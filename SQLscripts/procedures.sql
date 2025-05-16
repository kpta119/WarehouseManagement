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
    DECLARE v_TransactionPrice DECIMAL(12,2);

    INSERT INTO `Transaction` 
      (TransactionType, Date, Description, EmployeeID, FromWarehouseID, ToWarehouseID, ClientID, SupplierID)
    VALUES 
      (p_TransactionType, p_Date, p_Description, p_EmployeeID, p_FromWarehouseID, p_ToWarehouseID, p_ClientID, p_SupplierID);

    SET v_TransactionID = LAST_INSERT_ID();

    SET v_ProductCount = JSON_LENGTH(p_ProductsJSON);

    WHILE v_i < v_ProductCount DO
        SET v_ProductID = JSON_EXTRACT(p_ProductsJSON, CONCAT('$[', v_i, '].ProductID'));
        SET v_Quantity = JSON_EXTRACT(p_ProductsJSON, CONCAT('$[', v_i, '].Quantity'));

        SELECT UnitPrice INTO v_UnitPrice FROM Product WHERE ProductID = v_ProductID;

        SET v_TransactionPrice = v_UnitPrice * v_Quantity;

        INSERT INTO TransactionProduct(TransactionID, ProductID, Quantity, TransactionPrice)
        VALUES (v_TransactionID, v_ProductID, v_Quantity, v_TransactionPrice);

        IF p_TransactionType = 'SUPPLIER_TO_WAREHOUSE' THEN
            INSERT INTO ProductInventory (ProductID, WarehouseID, Quantity)
            VALUES (v_ProductID, p_ToWarehouseID, v_Quantity)
            ON DUPLICATE KEY UPDATE Quantity = Quantity + v_Quantity;

        ELSEIF p_TransactionType = 'WAREHOUSE_TO_CUSTOMER' THEN
            UPDATE ProductInventory
            SET Quantity = Quantity - v_Quantity
            WHERE ProductID = v_ProductID AND WarehouseID = p_FromWarehouseID;

        ELSEIF p_TransactionType = 'WAREHOUSE_TO_WAREHOUSE' THEN
            UPDATE ProductInventory
            SET Quantity = Quantity - v_Quantity
            WHERE ProductID = v_ProductID AND WarehouseID = p_FromWarehouseID;

            INSERT INTO ProductInventory (ProductID, WarehouseID, Quantity)
            VALUES (v_ProductID, p_ToWarehouseID, v_Quantity)
            ON DUPLICATE KEY UPDATE Quantity = Quantity + v_Quantity;
        END IF;

        SET v_i = v_i + 1;
    END WHILE;
END$$

DELIMITER ;