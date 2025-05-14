SET FOREIGN_KEY_CHECKS = 0;

-- Region, kraj, miasto, adres
INSERT INTO Region (RegionID, Name) VALUES (1, 'Europa');
INSERT INTO Country (CountryID, Name, CountryCode, RegionID) VALUES (1, 'Polska', 'PL', 1);
INSERT INTO City (CityID, Name, PostalCode, CountryID) VALUES (1, 'Warszawa', '00-001', 1);
INSERT INTO Address (AddressID, Street, StreetNumber, CityID) VALUES (1, 'Puławska', 1, 1);

-- Magazyn z pojemnością 1000 i zajętością 0
INSERT INTO Warehouse (WarehouseID, Name, Capacity, OccupiedCapacity, AddressID)
VALUES (1, 'Magazyn Testowy', 1000, 0, 1);

-- Kategoria
INSERT INTO Category (CategoryID, Name, Description) VALUES (1, 'Elektronika', 'Sprzęt elektroniczny');

-- Produkty z różnym rozmiarem jednostkowym
INSERT INTO Product (ProductID, Name, Description, UnitPrice, UnitSize, CategoryID)
VALUES
(1, 'Laptop', 'Laptop 15 cali', 3500.00, 5.00, 1),
(2, 'Monitor', 'Monitor 27 cali', 1200.00, 10.00, 1);

-- INSERT test
-- 10 * 5 = 50
INSERT INTO ProductInventory (ProductInventoryID, ProductID, WarehouseID, Quantity)
VALUES (1, 1, 1, 10);

-- Check OccupiedCapacity
SELECT OccupiedCapacity FROM Warehouse WHERE WarehouseID = 1;
-- expected: 50

-- UPDATE test
-- 30 * 5 = 150
UPDATE ProductInventory
SET Quantity = 30
WHERE ProductID = 1 AND WarehouseID = 1;

-- Check OccupiedCapacity
SELECT OccupiedCapacity FROM Warehouse WHERE WarehouseID = 1;
-- expected: 150

-- INSERT another product
-- 5 * 10 = 50
INSERT INTO ProductInventory (ProductInventoryID, ProductID, WarehouseID, Quantity)
VALUES (2, 2, 1, 5);

-- Check OccupiedCapacity
SELECT OccupiedCapacity FROM Warehouse WHERE WarehouseID = 1;
-- expected: 200 (150 + 50)

-- DELETE test
DELETE FROM ProductInventory
WHERE ProductID = 2 AND WarehouseID = 1;

-- Check OccupiedCapacity
SELECT OccupiedCapacity FROM Warehouse WHERE WarehouseID = 1;
-- expected: 150 (200 - 50)

SET FOREIGN_KEY_CHECKS = 1;
