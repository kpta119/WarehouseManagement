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
    DECLARE v_UnitPrice DECIMAL(6,2);

    -- Wstawienie nowej transakcji
    INSERT INTO `Transaction` 
      (TransactionType, Date, Description, EmployeeID, FromWarehouseID, ToWarehouseID, ClientID, SupplierID)
    VALUES 
      (p_TransactionType, p_Date, p_Description, p_EmployeeID, p_FromWarehouseID, p_ToWarehouseID, p_ClientID, p_SupplierID);

    SET v_TransactionID = LAST_INSERT_ID();

    -- Liczba produktów w JSON
    SET v_ProductCount = JSON_LENGTH(p_ProductsJSON);

    -- Pętla po produktach
    WHILE v_i < v_ProductCount DO
        -- Pobieranie danych z JSON
        SET v_ProductID = JSON_UNQUOTE(JSON_EXTRACT(p_ProductsJSON, CONCAT('$[', v_i, '].ProductID')));
        SET v_Quantity = JSON_UNQUOTE(JSON_EXTRACT(p_ProductsJSON, CONCAT('$[', v_i, '].Quantity')));

        -- Cena jednostkowa z tabeli Product
        SELECT UnitPrice INTO v_UnitPrice
        FROM Product
        WHERE ProductID = v_ProductID;

        -- Wstawienie do TransactionProduct (z TransactionPrice)
        INSERT INTO TransactionProduct(TransactionID, ProductID, Quantity, TransactionPrice)
        VALUES (v_TransactionID, v_ProductID, v_Quantity, v_Quantity * v_UnitPrice);

        -- Logika aktualizacji ProductInventory
        IF p_TransactionType = 'SUPPLIER_TO_WAREHOUSE' THEN
            -- Dodanie lub aktualizacja stanu w docelowym magazynie
            INSERT INTO ProductInventory (ProductID, WarehouseID, Quantity, Price)
            VALUES (v_ProductID, p_ToWarehouseID, v_Quantity, v_UnitPrice)
            ON DUPLICATE KEY UPDATE Quantity = Quantity + v_Quantity;

        ELSEIF p_TransactionType = 'WAREHOUSE_TO_CUSTOMER' THEN
            -- Zmniejszenie stanu w magazynie źródłowym
            UPDATE ProductInventory
            SET Quantity = Quantity - v_Quantity
            WHERE ProductID = v_ProductID AND WarehouseID = p_FromWarehouseID;

        ELSEIF p_TransactionType = 'WAREHOUSE_TO_WAREHOUSE' THEN
            -- Zmniejszenie stanu w magazynie źródłowym
            UPDATE ProductInventory
            SET Quantity = Quantity - v_Quantity
            WHERE ProductID = v_ProductID AND WarehouseID = p_FromWarehouseID;

            -- Dodanie do magazynu docelowego
            INSERT INTO ProductInventory (ProductID, WarehouseID, Quantity, Price)
            VALUES (v_ProductID, p_ToWarehouseID, v_Quantity, v_UnitPrice)
            ON DUPLICATE KEY UPDATE Quantity = Quantity + v_Quantity;
        END IF;

        SET v_i = v_i + 1;
    END WHILE;
END$$

DELIMITER ;