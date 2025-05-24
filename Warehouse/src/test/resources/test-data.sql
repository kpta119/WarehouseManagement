SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE TransactionProduct;
TRUNCATE TABLE `Transaction`;
TRUNCATE TABLE Report;
TRUNCATE TABLE ProductInventory;
TRUNCATE TABLE Product;
TRUNCATE TABLE Category;
TRUNCATE TABLE Employee;
TRUNCATE TABLE Warehouse;
TRUNCATE TABLE Supplier;
TRUNCATE TABLE Client;
TRUNCATE TABLE Address;
TRUNCATE TABLE City;
TRUNCATE TABLE Country;
TRUNCATE TABLE Region;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO Region (Name)
VALUES ('Europe'),
       ('Asia'),
       ('North America'),
       ('South America'),
       ('Africa');

INSERT INTO Country (Name, CountryCode, RegionID)
VALUES ('France', 'FR', 1),
       ('China', 'CN', 2),
       ('United States', 'US', 3),
       ('Brazil', 'BR', 4),
       ('Nigeria', 'NG', 5);

INSERT INTO City (Name, PostalCode, CountryID)
VALUES ('Paris', '75000', 1),
       ('Beijing', '100000', 2),
       ('New York', '10001', 3),
       ('São Paulo', '01000-000', 4),
       ('Lagos', '100001', 5);

INSERT INTO Address (Street, StreetNumber, CityID)
VALUES ('Champs-Élysées', 101, 1),
       ('Chang''an Avenue', 200, 2),
       ('5th Avenue', 350, 3),
       ('Avenida Paulista', 1000, 4),
       ('Broad Street', 1, 5);

INSERT INTO Client (Name, Email, PhoneNumber, AddressID)
VALUES ('Client 1', 'client1@example.com', '+123456789', 1),
       ('Client 2', 'client2@example.com', '+234567890', 2),
       ('Client 3', 'client3@example.com', '+345678901', 3),
       ('Client 4', 'client4@example.com', '+456789012', 4),
       ('Client 5', 'client5@example.com', '+567890123', 5);

INSERT INTO Supplier (Name, Email, PhoneNumber, AddressID)
VALUES ('Supplier 1', 'supplier1@example.com', '+111222333', 1),
       ('Supplier 2', 'supplier2@example.com', '+222333444', 2),
       ('Supplier 3', 'supplier3@example.com', '+333444555', 3),
       ('Supplier 4', 'supplier4@example.com', '+444555666', 4),
       ('Supplier 5', 'supplier5@example.com', '+555666777', 5);

INSERT INTO Warehouse (Name, Capacity, OccupiedCapacity, AddressID)
VALUES ('Warehouse Paris', 1000, 500, 1),
       ('Warehouse Beijing', 2000, 1000, 2),
       ('Warehouse NYC', 1500, 750, 3),
       ('Warehouse São Paulo', 1800, 900, 4),
       ('Warehouse Lagos', 1200, 600, 5);

INSERT INTO Employee (Name, Surname, Position, Email, PhoneNumber, AddressID, WarehouseID)
VALUES ('John', 'Doe', 'Manager', 'john@example.com', '+111222333', 1, 1),
       ('Jane', 'Smith', 'Clerk', 'jane@example.com', '+222333444', 2, 2),
       ('Bob', 'Johnson', 'Driver', 'bob@example.com', '+333444555', 3, 3),
       ('Alice', 'Brown', 'Supervisor', 'alice@example.com', '+444555666', 4, 4),
       ('Charlie', 'Wilson', 'Assistant', 'charlie@example.com', '+555666777', 5, 5),
       ('David', 'Garcia', 'Intern', 'lol@gmail.com', '+666777888', 1, 1);


INSERT INTO Category (Name, Description)
VALUES ('Electronics', 'Gadgets and devices'),
       ('Clothing', 'Apparel and fashion'),
       ('Grocery', 'Food items'),
       ('Furniture', 'Home and office furniture'),
       ('Books', 'Educational and recreational reading');
INSERT INTO Product (Name, Description, UnitPrice, UnitSize, CategoryID)
VALUES ('Laptop', '15-inch laptop', 999.99, 2.5, 1),
       ('T-Shirt', 'Cotton T-Shirt', 19.99, 0.5, 2),
       ('Apple', 'Red apple', 0.99, 0.1, 3),
       ('Chair', 'Wooden chair', 49.99, 5.0, 4),
       ('Novel', 'Bestseller novel', 14.99, 1.0, 5),
       ('Smartphone', 'Latest model smartphone', 799.99, 0.3, 1);


INSERT INTO ProductInventory (ProductID, WarehouseID, Quantity, Price)
VALUES (1, 1, 2, 999.99),
       (2, 2, 6, 19.99),
       (3, 3, 5, 0.99),
       (4, 4, 3, 49.99),
       (5, 5, 1, 14.99),
       (1, 2, 3, 900.00),
       (2, 3, 4, 18.99);

INSERT INTO Transaction (TransactionType, Date, Description, EmployeeID, FromWarehouseID, ToWarehouseID, ClientID,
                         SupplierID, SourceWarehouseCapacityAfterTransaction, TargetWarehouseCapacityAfterTransaction)
VALUES ('WAREHOUSE_TO_WAREHOUSE', '2024-06-01', 'Transfer between warehouses', 1, 1, 2, NULL, NULL, 450, 1100),
       ('SUPPLIER_TO_WAREHOUSE', '2024-05-31', 'Supplier restock', 2, 1, NULL, NULL, 1, 550, NULL),
       ('WAREHOUSE_TO_CUSTOMER', '2025-04-29', 'Customer order', 3, 3, NULL, 3, NULL, 700, NULL),
       ('WAREHOUSE_TO_WAREHOUSE', '2025-05-20', 'Inter-warehouse transfer', 4, 4, 5, NULL, NULL, 800, 650),
       ('SUPPLIER_TO_WAREHOUSE', '2024-03-05', 'Supplier delivery', 5, 5, NULL, NULL, 5, 700, NULL),
       ('WAREHOUSE_TO_CUSTOMER', '2024-07-15', 'Customer order', 1, 2, NULL, 2, NULL, 600, NULL);

INSERT INTO TransactionProduct (TransactionID, ProductID, Quantity, TransactionPrice)
VALUES (1, 1, 10, 999.99),
       (2, 1, 5, 999.99),
       (3, 3, 100, 0.99),
       (4, 4, 20, 49.99),
       (5, 5, 50, 14.99),
       (1, 2, 2, 900.00);