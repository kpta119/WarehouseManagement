INSERT INTO Region (Name) VALUES ('Europa');
SET @region_id = LAST_INSERT_ID();

INSERT INTO Country (Name, CountryCode, RegionID)
VALUES ('Polska', 'PL', @region_id);
SET @country_id = LAST_INSERT_ID();

INSERT INTO City (Name, PostalCode, CountryID)
VALUES ('Warszawa', '00-001', @country_id);
SET @city_id = LAST_INSERT_ID();

INSERT INTO Address (Street, StreetNumber, CityID)
VALUES ('Puławska', 1, @city_id);
SET @address_id = LAST_INSERT_ID();

INSERT INTO Warehouse (Name, Capacity, OccupiedCapacity, AddressID)
VALUES ('Magazyn A', 1000, 0, @address_id);
SET @wh1 = LAST_INSERT_ID();

INSERT INTO Warehouse (Name, Capacity, OccupiedCapacity, AddressID)
VALUES ('Magazyn B', 1000, 0, @address_id);
SET @wh2 = LAST_INSERT_ID();

INSERT INTO Supplier (Name, Email, PhoneNumber, AddressID)
VALUES ('Dostawca Sp. z o.o.', 'dostawca@example.com', '123456789', @address_id);
SET @supplier_id = LAST_INSERT_ID();

INSERT INTO Client (Name, Email, PhoneNumber, AddressID)
VALUES ('Klient SA', 'klient@example.com', '987654321', @address_id);
SET @client_id = LAST_INSERT_ID();

INSERT INTO Employee (Name, Surname, Position, Email, PhoneNumber, AddressID, WarehouseID)
VALUES ('Jan', 'Kowalski', 'Magazynier', 'jan@example.com', '111222333', @address_id, @wh1);
SET @employee_id = LAST_INSERT_ID();


-- Typ 1: WAREHOUSE_TO_WAREHOUSE
INSERT INTO Transaction (
    TransactionType, Date, Description, EmployeeID, FromWarehouseID, ToWarehouseID
) VALUES (
    'WAREHOUSE_TO_WAREHOUSE', CURDATE(), 'Przeniesienie towaru', @employee_id, @wh1, @wh2
);

-- Typ 2: SUPPLIER_TO_WAREHOUSE
INSERT INTO Transaction (
    TransactionType, Date, Description, EmployeeID, FromWarehouseID, SupplierID
) VALUES (
    'SUPPLIER_TO_WAREHOUSE', CURDATE(), 'Dostawa od dostawcy', @employee_id, @wh1, @supplier_id
);

-- Typ 3: WAREHOUSE_TO_CUSTOMER
INSERT INTO Transaction (
    TransactionType, Date, Description, EmployeeID, FromWarehouseID, ClientID
) VALUES (
    'WAREHOUSE_TO_CUSTOMER', CURDATE(), 'Wysyłka do klienta', @employee_id, @wh1, @client_id
);

-- error
INSERT INTO Transaction (
    TransactionType, Date, Description, EmployeeID, FromWarehouseID
) VALUES (
    'WAREHOUSE_TO_WAREHOUSE', CURDATE(), 'Niepełna transakcja', @employee_id, @wh1
);
