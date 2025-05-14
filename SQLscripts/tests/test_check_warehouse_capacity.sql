SET FOREIGN_KEY_CHECKS = 0;

-- Region
INSERT INTO Region (RegionID, Name) VALUES (1, 'Europa');

-- Kraj
INSERT INTO Country (CountryID, Name, CountryCode, RegionID)
VALUES (1, 'Polska', 'PL', 1);

-- Miasto
INSERT INTO City (CityID, Name, PostalCode, CountryID)
VALUES (1, 'Warszawa', '00-001', 1);

-- Adres
INSERT INTO Address (AddressID, Street, StreetNumber, CityID)
VALUES (1, 'Puławska', 1, 1);

-- Magazyn
INSERT INTO Warehouse (WarehouseID, Name, Capacity, OccupiedCapacity, AddressID)
VALUES (1, 'Magazyn Centralny', 1000, 200, 1);

-- Kategoria
INSERT INTO Category (CategoryID, Name, Description)
VALUES (1, 'Elektronika', 'Urządzenia elektroniczne');

-- Produkty
INSERT INTO Product (ProductID, Name, Description, UnitPrice, UnitSize, CategoryID)
VALUES
(1, 'Laptop', 'Laptop 15 cali', 3000.00, 5.00, 1),
(2, 'Monitor', 'Monitor 24 cale', 700.00, 10.00, 1);

-- Magazynowanie: 20 laptopów * 5 = 100
INSERT INTO ProductInventory (ProductInventoryID, ProductID, WarehouseID, Quantity)
VALUES (1, 1, 1, 20);

-- Brak błędu — 30 * 5 = 150 + inne stany = mieści się w 1000
UPDATE ProductInventory
SET Quantity = 30
WHERE ProductInventoryID = 1;

-- Błąd — 200 * 5 = 1000 → magazyn już ma 200 zajętości, więc przekroczy Capacity
UPDATE ProductInventory
SET Quantity = 200
WHERE ProductInventoryID = 1;

SET FOREIGN_KEY_CHECKS = 1;
