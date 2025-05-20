SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS ProductInventory;
DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS Category;
DROP TABLE IF EXISTS Employee;
DROP TABLE IF EXISTS Warehouse;
DROP TABLE IF EXISTS Supplier;
DROP TABLE IF EXISTS Client;
DROP TABLE IF EXISTS Address;
DROP TABLE IF EXISTS City;
DROP TABLE IF EXISTS Country;
DROP TABLE IF EXISTS Region;
DROP TABLE IF EXISTS TransactionProduct;
DROP TABLE IF EXISTS Transaction;
DROP TABLE IF EXISTS Report;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE Region
(
    RegionID INT AUTO_INCREMENT PRIMARY KEY,
    Name     VARCHAR(50) NOT NULL
);

CREATE TABLE Country
(
    CountryID   INT AUTO_INCREMENT PRIMARY KEY,
    Name        VARCHAR(50) NOT NULL,
    CountryCode VARCHAR(5),
    RegionID    INT,
    FOREIGN KEY (RegionID) REFERENCES Region (RegionID)
);

CREATE TABLE City
(
    CityID     INT AUTO_INCREMENT PRIMARY KEY,
    Name       VARCHAR(50) NOT NULL,
    PostalCode VARCHAR(20) NOT NULL,
    CountryID  INT,
    FOREIGN KEY (CountryID) REFERENCES Country (CountryID)
);

CREATE TABLE Address
(
    AddressID    INT AUTO_INCREMENT PRIMARY KEY,
    Street       VARCHAR(50) NOT NULL,
    StreetNumber INT         NOT NULL,
    CityID       INT,
    FOREIGN KEY (CityID) REFERENCES City (CityID)
);

CREATE TABLE Client
(
    ClientID    INT AUTO_INCREMENT PRIMARY KEY,
    Name        VARCHAR(50) NOT NULL,
    Email       VARCHAR(50),
    PhoneNumber VARCHAR(20),
    AddressID   INT,
    FOREIGN KEY (AddressID) REFERENCES Address (AddressID)
);

CREATE TABLE Supplier
(
    SupplierID  INT AUTO_INCREMENT PRIMARY KEY,
    Name        VARCHAR(50) NOT NULL,
    Email       VARCHAR(50),
    PhoneNumber VARCHAR(20),
    AddressID   INT,
    FOREIGN KEY (AddressID) REFERENCES Address (AddressID)
);

CREATE TABLE Warehouse
(
    WarehouseID      INT AUTO_INCREMENT PRIMARY KEY,
    Name             VARCHAR(100) NOT NULL,
    Capacity         DECIMAL(4, 2)          NOT NULL,
    OccupiedCapacity DECIMAL(4, 2)          NOT NULL,
    AddressID        INT,
    FOREIGN KEY (AddressID) REFERENCES Address (AddressID)
);

CREATE TABLE Employee
(
    EmployeeID  INT AUTO_INCREMENT PRIMARY KEY,
    Name        VARCHAR(100) NOT NULL,
    Surname     VARCHAR(100) NOT NULL,
    Position    VARCHAR(100),
    Email       VARCHAR(100),
    PhoneNumber VARCHAR(20),
    AddressID   INT,
    WarehouseID INT,
    FOREIGN KEY (AddressID) REFERENCES Address (AddressID),
    FOREIGN KEY (WarehouseID) REFERENCES Warehouse (WarehouseID)
);

CREATE TABLE Category
(
    CategoryID  INT AUTO_INCREMENT PRIMARY KEY,
    Name        VARCHAR(100) NOT NULL,
    Description VARCHAR(200)
);

CREATE TABLE Product
(
    ProductID   INT AUTO_INCREMENT PRIMARY KEY,
    Name        VARCHAR(50)   NOT NULL,
    Description VARCHAR(200),
    UnitPrice   DECIMAL(6, 2) NOT NULL,
    UnitSize    DECIMAL(4, 2) NOT NULL,
    CategoryID  INT,
    FOREIGN KEY (CategoryID) REFERENCES Category (CategoryID)
);

CREATE TABLE ProductInventory
(
    ProductInventoryID INT AUTO_INCREMENT PRIMARY KEY,
    ProductID          INT,
    WarehouseID        INT,
    Quantity           INT            NOT NULL,
    Price              DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (ProductID) REFERENCES Product (ProductID),
    FOREIGN KEY (WarehouseID) REFERENCES Warehouse (WarehouseID)
);

CREATE TABLE Report
(
    ReportID    INT AUTO_INCREMENT PRIMARY KEY,
    Type        VARCHAR(50),
    Date        DATE         NOT NULL,
    Content     VARCHAR(200) NOT NULL,
    WarehouseID INT,
    FOREIGN KEY (WarehouseID) REFERENCES Warehouse (WarehouseID)
);

CREATE TABLE Transaction
(
    TransactionID                           INT PRIMARY KEY AUTO_INCREMENT,
    TransactionType                         ENUM (
        'WAREHOUSE_TO_WAREHOUSE',
        'SUPPLIER_TO_WAREHOUSE',
        'WAREHOUSE_TO_CUSTOMER'
        )                                        NOT NULL,
    Date                                    DATE NOT NULL,
    Description                             VARCHAR(200),
    EmployeeID                              INT,
    FromWarehouseID                         INT,
    ToWarehouseID                           INT,
    ClientID                                INT,
    SupplierID                              INT,
    SourceWarehouseCapacityAfterTransaction INT,
    TargetWarehouseCapacityAfterTransaction INT,
    FOREIGN KEY (EmployeeID) REFERENCES Employee (EmployeeID),
    FOREIGN KEY (FromWarehouseID) REFERENCES Warehouse (WarehouseID),
    FOREIGN KEY (ToWarehouseID) REFERENCES Warehouse (WarehouseID),
    FOREIGN KEY (ClientID) REFERENCES Client (ClientID),
    FOREIGN KEY (SupplierID) REFERENCES Supplier (SupplierID)
);

ALTER TABLE Transaction
    ADD CONSTRAINT transaction_type_check
        CHECK ((TransactionType = 'WAREHOUSE_TO_WAREHOUSE' AND FromWarehouseID IS NOT NULL AND
                ToWarehouseID IS NOT NULL)
            OR
               (TransactionType = 'SUPPLIER_TO_WAREHOUSE' AND SupplierID IS NOT NULL AND ToWarehouseID IS NOT NULL)
            OR
               (TransactionType = 'WAREHOUSE_TO_CUSTOMER' AND FromWarehouseID IS NOT NULL AND ClientID IS NOT NULL)
            );

CREATE TABLE TransactionProduct
(
    TransactionProductID INT AUTO_INCREMENT PRIMARY KEY,
    TransactionID        INT,
    ProductID            INT,
    Quantity             INT            NOT NULL,
    TransactionPrice     DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (TransactionID) REFERENCES Transaction (TransactionID),
    FOREIGN KEY (ProductID) REFERENCES Product (ProductID)
);