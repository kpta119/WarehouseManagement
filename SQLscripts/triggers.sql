DELIMITER //

CREATE TRIGGER check_warehouse_capacity_before_update
BEFORE UPDATE ON ProductInventory
FOR EACH ROW
BEGIN
    DECLARE v_capacity INT;
    DECLARE v_occupied INT;
    DECLARE v_product_size DECIMAL(4,2);
    DECLARE v_total DECIMAL(10,2);
    DECLARE v_current_quantity INT;

    SELECT Capacity, OccupiedCapacity
    INTO v_capacity, v_occupied
    FROM Warehouse
    WHERE WarehouseID = NEW.WarehouseID;

    SELECT UnitSize
    INTO v_product_size
    FROM Product
    WHERE ProductID = NEW.ProductID;

    SET v_current_quantity = OLD.Quantity;

    SET v_total = v_occupied + ((NEW.Quantity - v_current_quantity) * v_product_size);

    IF v_total > v_capacity THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Update would exceed warehouse capacity';
    END IF;
END;
//

DELIMITER ;

-- update OccupiedCapacity after changing warehouse states

DELIMITER //

CREATE TRIGGER update_occupied_capacity_after_insert
AFTER INSERT ON ProductInventory
FOR EACH ROW
BEGIN
    DECLARE v_product_size DECIMAL(4,2);

    SELECT UnitSize INTO v_product_size
    FROM Product
    WHERE ProductID = NEW.ProductID;

    UPDATE Warehouse
    SET OccupiedCapacity = OccupiedCapacity + (NEW.Quantity * v_product_size)
    WHERE WarehouseID = NEW.WarehouseID;
END;
//

DELIMITER ;

DELIMITER //

CREATE TRIGGER update_occupied_capacity_after_update
AFTER UPDATE ON ProductInventory
FOR EACH ROW
BEGIN
    DECLARE v_product_size DECIMAL(4,2);
    DECLARE v_quantity_diff INT;

    SET v_quantity_diff = NEW.Quantity - OLD.Quantity;

    SELECT UnitSize INTO v_product_size
    FROM Product
    WHERE ProductID = NEW.ProductID;

    UPDATE Warehouse
    SET OccupiedCapacity = OccupiedCapacity + (v_quantity_diff * v_product_size)
    WHERE WarehouseID = NEW.WarehouseID;
END;
//

DELIMITER ;

DELIMITER //

CREATE TRIGGER update_occupied_capacity_after_delete
AFTER DELETE ON ProductInventory
FOR EACH ROW
BEGIN
    DECLARE v_product_size DECIMAL(4,2);

    SELECT UnitSize INTO v_product_size
    FROM Product
    WHERE ProductID = OLD.ProductID;

    UPDATE Warehouse
    SET OccupiedCapacity = OccupiedCapacity - (OLD.Quantity * v_product_size)
    WHERE WarehouseID = OLD.WarehouseID;
END;
//

DELIMITER ;

-- transaction type check

DELIMITER $$

CREATE TRIGGER check_transaction_type_before_write
BEFORE INSERT ON Transaction
FOR EACH ROW
BEGIN
    IF NOT (
        (NEW.TransactionType = 'WAREHOUSE_TO_WAREHOUSE' AND NEW.FromWarehouseID IS NOT NULL AND NEW.ToWarehouseID IS NOT NULL)
        OR
        (NEW.TransactionType = 'SUPPLIER_TO_WAREHOUSE' AND NEW.SupplierID IS NOT NULL AND NEW.FromWarehouseID IS NOT NULL)
        OR
        (NEW.TransactionType = 'WAREHOUSE_TO_CUSTOMER' AND NEW.FromWarehouseID IS NOT NULL AND NEW.ClientID IS NOT NULL)
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Invalid transaction structure for the specified TransactionType';
    END IF;
END$$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER check_transaction_type_before_update
BEFORE UPDATE ON Transaction
FOR EACH ROW
BEGIN
    IF NOT (
        (NEW.TransactionType = 'WAREHOUSE_TO_WAREHOUSE' AND NEW.FromWarehouseID IS NOT NULL AND NEW.ToWarehouseID IS NOT NULL)
        OR
        (NEW.TransactionType = 'SUPPLIER_TO_WAREHOUSE' AND NEW.SupplierID IS NOT NULL AND NEW.FromWarehouseID IS NOT NULL)
        OR
        (NEW.TransactionType = 'WAREHOUSE_TO_CUSTOMER' AND NEW.FromWarehouseID IS NOT NULL AND NEW.ClientID IS NOT NULL)
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Invalid transaction structure for the specified TransactionType';
    END IF;
END$$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER add_product_inventory_after_post_on_product
AFTER INSERT ON Product
FOR EACH ROW
BEGIN
    DECLARE v_warehouse_id INT;
    DECLARE done INT DEFAULT FALSE;

    DECLARE warehouse_cursor CURSOR FOR
        SELECT WarehouseID FROM Warehouse;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN warehouse_cursor;

    read_loop: LOOP
        FETCH warehouse_cursor INTO v_warehouse_id;
        IF done THEN
            LEAVE read_loop;
        END IF;

        INSERT INTO ProductInventory (ProductID, WarehouseID, Quantity, Price)
        VALUES (NEW.ProductID, v_warehouse_id, 0, NEW.UnitPrice);
    END LOOP;

    CLOSE warehouse_cursor;
END$$

DELIMITER ;

ALTER TABLE Transaction
ADD INDEX idx_employee_id_transaction (EmployeeID);