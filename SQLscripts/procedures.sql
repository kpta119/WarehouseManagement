DROP PROCEDURE IF EXISTS add_transaction;

DELIMITER $$

CREATE PROCEDURE add_transaction(
    IN p_TransactionType ENUM('WAREHOUSE_TO_WAREHOUSE', 'SUPPLIER_TO_WAREHOUSE', 'WAREHOUSE_TO_CUSTOMER'),
    IN p_Date DATE,
    IN p_Description VARCHAR(200),
    IN p_EmployeeID INT,
    IN p_FromWarehouseID INT,
    IN p_ToWarehouseID INT,
    IN p_ClientID INT,
    IN p_SupplierID INT,
    IN p_ProductsJSON TEXT
)
BEGIN
    DECLARE v_TransactionID INT;
    DECLARE v_i INT DEFAULT 0;
    DECLARE v_ProductCount INT;
    DECLARE v_ProductID INT;
    DECLARE v_Quantity INT;
    DECLARE v_UnitPrice DECIMAL(10,2);
    DECLARE v_UnitSize DECIMAL(4,2);
    DECLARE v_ChangeInCapacity DECIMAL(10,2);
    DECLARE v_SourceCapacityAfter DECIMAL(10,2) DEFAULT NULL;
    DECLARE v_TargetCapacityAfter DECIMAL(10,2) DEFAULT NULL;
    DECLARE v_FromWarehouseOccupied DECIMAL(10,2);
    DECLARE v_ToWarehouseOccupied DECIMAL(10,2);
    DECLARE v_InventoryID INT;

    INSERT INTO `Transaction`
      (TransactionType, Date, Description, EmployeeID, FromWarehouseID, ToWarehouseID, ClientID, SupplierID,
       SourceWarehouseCapacityAfterTransaction, TargetWarehouseCapacityAfterTransaction)
    VALUES
      (p_TransactionType, p_Date, p_Description, p_EmployeeID, p_FromWarehouseID, p_ToWarehouseID, p_ClientID, p_SupplierID,
       NULL, NULL);

    SET v_TransactionID = LAST_INSERT_ID();
    SET v_ProductCount = JSON_LENGTH(p_ProductsJSON);

    WHILE v_i < v_ProductCount DO
        SET v_ProductID = JSON_UNQUOTE(JSON_EXTRACT(p_ProductsJSON, CONCAT('$[', v_i, '].ProductID')));
        SET v_Quantity = JSON_UNQUOTE(JSON_EXTRACT(p_ProductsJSON, CONCAT('$[', v_i, '].Quantity')));

        SELECT UnitPrice, UnitSize INTO v_UnitPrice, v_UnitSize
        FROM Product
        WHERE ProductID = v_ProductID;

        SET v_ChangeInCapacity = v_UnitSize * v_Quantity;

        INSERT INTO TransactionProduct(TransactionID, ProductID, Quantity, TransactionPrice)
        VALUES (v_TransactionID, v_ProductID, v_Quantity, v_UnitPrice);

        IF p_TransactionType = 'SUPPLIER_TO_WAREHOUSE' THEN

            SELECT ProductInventoryID INTO v_InventoryID
            FROM ProductInventory
            WHERE ProductID = v_ProductID AND WarehouseID = p_ToWarehouseID
            LIMIT 1;

            IF v_InventoryID IS NOT NULL THEN
                UPDATE ProductInventory
                SET Quantity = Quantity + v_Quantity
                WHERE ProductInventoryID = v_InventoryID;
            ELSE
                INSERT INTO ProductInventory (ProductID, WarehouseID, Quantity, Price)
                VALUES (v_ProductID, p_ToWarehouseID, v_Quantity, v_UnitPrice);
            END IF;

        ELSEIF p_TransactionType = 'WAREHOUSE_TO_CUSTOMER' THEN

            UPDATE ProductInventory
            SET Quantity = Quantity - v_Quantity
            WHERE ProductID = v_ProductID AND WarehouseID = p_FromWarehouseID;

        ELSEIF p_TransactionType = 'WAREHOUSE_TO_WAREHOUSE' THEN

            UPDATE ProductInventory
            SET Quantity = Quantity - v_Quantity
            WHERE ProductID = v_ProductID AND WarehouseID = p_FromWarehouseID;

            SELECT ProductInventoryID INTO v_InventoryID
            FROM ProductInventory
            WHERE ProductID = v_ProductID AND WarehouseID = p_ToWarehouseID
            LIMIT 1;

            IF v_InventoryID IS NOT NULL THEN
                UPDATE ProductInventory
                SET Quantity = Quantity + v_Quantity
                WHERE ProductInventoryID = v_InventoryID;
            ELSE
                INSERT INTO ProductInventory (ProductID, WarehouseID, Quantity, Price)
                VALUES (v_ProductID, p_ToWarehouseID, v_Quantity, v_UnitPrice);
            END IF;
        END IF;

        SET v_i = v_i + 1;
    END WHILE;

    IF p_FromWarehouseID IS NOT NULL THEN
        SELECT OccupiedCapacity INTO v_FromWarehouseOccupied FROM Warehouse WHERE WarehouseID = p_FromWarehouseID;
        SET v_SourceCapacityAfter = v_FromWarehouseOccupied;
    END IF;

    IF p_ToWarehouseID IS NOT NULL THEN
        SELECT OccupiedCapacity INTO v_ToWarehouseOccupied FROM Warehouse WHERE WarehouseID = p_ToWarehouseID;
        SET v_TargetCapacityAfter = v_ToWarehouseOccupied;
    END IF;

    UPDATE `Transaction`
    SET SourceWarehouseCapacityAfterTransaction = v_SourceCapacityAfter,
        TargetWarehouseCapacityAfterTransaction = v_TargetCapacityAfter
    WHERE TransactionID = v_TransactionID;
END$$

DELIMITER ;

-----------------------------------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS receive_delivery;

DELIMITER $$

CREATE PROCEDURE receive_delivery(
    IN p_Date DATE,
    IN p_Description VARCHAR(200),
    IN p_EmployeeID INT,
    IN p_SupplierID INT,
    IN p_ToWarehouseID INT,
    IN p_ProductsJSON TEXT
)
BEGIN
    DECLARE v_i INT DEFAULT 0;
    DECLARE v_ProductCount INT;
    DECLARE v_ProductID INT;
    DECLARE v_Quantity INT;
    DECLARE v_UnitSize DECIMAL(4,2);
    DECLARE v_TotalRequiredCapacity DECIMAL(10,2) DEFAULT 0;
    DECLARE v_WarehouseCapacity DECIMAL(10,2);
    DECLARE v_WarehouseOccupied DECIMAL(10,2);

    SET v_ProductCount = JSON_LENGTH(p_ProductsJSON);

    WHILE v_i < v_ProductCount DO
        SET v_ProductID = JSON_UNQUOTE(JSON_EXTRACT(p_ProductsJSON, CONCAT('$[', v_i, '].ProductID')));
        SET v_Quantity = JSON_UNQUOTE(JSON_EXTRACT(p_ProductsJSON, CONCAT('$[', v_i, '].Quantity')));

        SELECT UnitSize INTO v_UnitSize
        FROM Product
        WHERE ProductID = v_ProductID;

        SET v_TotalRequiredCapacity = v_TotalRequiredCapacity + (v_UnitSize * v_Quantity);
        SET v_i = v_i + 1;
    END WHILE;

    SELECT Capacity, OccupiedCapacity
    INTO v_WarehouseCapacity, v_WarehouseOccupied
    FROM Warehouse
    WHERE WarehouseID = p_ToWarehouseID;

    IF v_WarehouseOccupied + v_TotalRequiredCapacity > v_WarehouseCapacity THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Insufficient warehouse capacity for this delivery.';
    ELSE
        CALL add_transaction(
            'SUPPLIER_TO_WAREHOUSE',
            p_Date,
            p_Description,
            p_EmployeeID,
            NULL,
            p_ToWarehouseID,
            NULL,
            p_SupplierID,
            p_ProductsJSON
        );
    END IF;

END$$

DELIMITER ;

-----------------------------------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS sell_to_client;

DELIMITER $$

CREATE PROCEDURE sell_to_client(
    IN p_Date DATE,
    IN p_Description VARCHAR(200),
    IN p_EmployeeID INT,
    IN p_FromWarehouseID INT,
    IN p_ClientID INT,
    IN p_ProductsJSON TEXT
)
BEGIN
    DECLARE v_i INT DEFAULT 0;
    DECLARE v_ProductCount INT;
    DECLARE v_ProductID INT;
    DECLARE v_Quantity INT;
    DECLARE v_InventoryQuantity INT;
    DECLARE v_ErrorMessage VARCHAR(255);

    SET v_ProductCount = JSON_LENGTH(p_ProductsJSON);

    WHILE v_i < v_ProductCount DO
        SET v_ProductID = JSON_UNQUOTE(JSON_EXTRACT(p_ProductsJSON, CONCAT('$[', v_i, '].ProductID')));
        SET v_Quantity = JSON_UNQUOTE(JSON_EXTRACT(p_ProductsJSON, CONCAT('$[', v_i, '].Quantity')));

        SELECT Quantity INTO v_InventoryQuantity
        FROM ProductInventory
        WHERE ProductID = v_ProductID AND WarehouseID = p_FromWarehouseID;

        IF v_InventoryQuantity IS NULL OR v_InventoryQuantity < v_Quantity THEN
            SET v_ErrorMessage = CONCAT('Insufficient quantity of product ID ', v_ProductID, ' in source warehouse.');
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = v_ErrorMessage;
        END IF;

        SET v_i = v_i + 1;
    END WHILE;

    CALL add_transaction(
        'WAREHOUSE_TO_CUSTOMER',
        p_Date,
        p_Description,
        p_EmployeeID,
        p_FromWarehouseID,
        NULL,
        p_ClientID,
        NULL,
        p_ProductsJSON
    );

END$$

DELIMITER ;

-----------------------------------------------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS exchange_between_warehouses;

DELIMITER $$

CREATE PROCEDURE exchange_between_warehouses(
    IN p_Date DATE,
    IN p_Description VARCHAR(200),
    IN p_EmployeeID INT,
    IN p_FromWarehouseID INT,
    IN p_ToWarehouseID INT,
    IN p_ProductsJSON TEXT
)
BEGIN
    DECLARE v_i INT DEFAULT 0;
    DECLARE v_ProductCount INT;
    DECLARE v_ProductID INT;
    DECLARE v_Quantity INT;
    DECLARE v_InventoryQuantity INT;
    DECLARE v_UnitSize DECIMAL(4,2);
    DECLARE v_TotalVolumeChange DECIMAL(10,2) DEFAULT 0;
    DECLARE v_ToWarehouseCapacity DECIMAL(10,2);
    DECLARE v_ToWarehouseOccupied DECIMAL(10,2);
    DECLARE v_ErrorMessage VARCHAR(255);

    SET v_ProductCount = JSON_LENGTH(p_ProductsJSON);

    WHILE v_i < v_ProductCount DO
        SET v_ProductID = JSON_UNQUOTE(JSON_EXTRACT(p_ProductsJSON, CONCAT('$[', v_i, '].ProductID')));
        SET v_Quantity = JSON_UNQUOTE(JSON_EXTRACT(p_ProductsJSON, CONCAT('$[', v_i, '].Quantity')));

        SELECT Quantity INTO v_InventoryQuantity
        FROM ProductInventory
        WHERE ProductID = v_ProductID AND WarehouseID = p_FromWarehouseID
        LIMIT 1;

        IF v_InventoryQuantity IS NULL OR v_InventoryQuantity < v_Quantity THEN
            SET v_ErrorMessage = CONCAT('Insufficient quantity of product ID ', v_ProductID, ' in source warehouse.');
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = v_ErrorMessage;
        END IF;

        SELECT UnitSize INTO v_UnitSize FROM Product WHERE ProductID = v_ProductID LIMIT 1;

        SET v_TotalVolumeChange = v_TotalVolumeChange + (v_UnitSize * v_Quantity);

        SET v_i = v_i + 1;
    END WHILE;

    SELECT Capacity, OccupiedCapacity INTO v_ToWarehouseCapacity, v_ToWarehouseOccupied
    FROM Warehouse
    WHERE WarehouseID = p_ToWarehouseID LIMIT 1;

    IF v_ToWarehouseOccupied + v_TotalVolumeChange > v_ToWarehouseCapacity THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Not enough capacity in target warehouse.';
    END IF;

    CALL add_transaction(
        'WAREHOUSE_TO_WAREHOUSE',
        p_Date,
        p_Description,
        p_EmployeeID,
        p_FromWarehouseID,
        p_ToWarehouseID,
        NULL,
        NULL,
        p_ProductsJSON
    );

END$$

DELIMITER ;