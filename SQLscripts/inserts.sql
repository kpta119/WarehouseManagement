SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM TransactionProduct;
DELETE FROM `Transaction`;
DELETE FROM Report;
DELETE FROM ProductInventory;
DELETE FROM Product;
DELETE FROM Category;
DELETE FROM Employee;
DELETE FROM Supplier;
DELETE FROM Client;
DELETE FROM Warehouse;
DELETE FROM Address;
DELETE FROM City;
DELETE FROM Country;
DELETE FROM Region;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO Region (RegionID, Name) VALUES
(1, 'Africa'),
(2, 'Antarctica'),
(3, 'Asia'),
(4, 'Europe'),
(5, 'North America'),
(6, 'Oceania'),
(7, 'South America');

INSERT INTO Country (CountryID, Name, CountryCode, RegionID) VALUES
(1, 'Poland', 'PL', 1),
(2, 'Germany', 'DE', 2),
(3, 'France', 'FR', 2),
(4, 'Spain', 'ES', 3),
(5, 'Italy', 'IT', 3),
(6, 'United Kingdom', 'UK', 2),
(7, 'Norway', 'NO', 4),
(8, 'Sweden', 'SE', 4),
(9, 'Japan', 'JP', 5),
(10, 'Australia', 'AU', 6);

INSERT INTO City (CityID, Name, PostalCode, CountryID) VALUES
(1, 'Warsaw', '00-001', 1),
(2, 'Berlin', '10115', 2),
(3, 'Paris', '75001', 3),
(4, 'Madrid', '28001', 4),
(5, 'Rome', '00100', 5),
(6, 'London', 'EC1A 1BB', 6),
(7, 'Oslo', '0010', 7),
(8, 'Stockholm', '111 20', 8),
(9, 'Tokyo', '100-0001', 9),
(10, 'Sydney', '2000', 10);

INSERT INTO Address (AddressID, Street, StreetNumber, CityID) VALUES
(1, 'Krakowskie Przedmieście', 15, 1),
(2, 'Unter den Linden', 77, 2),
(3, 'Rue de Rivoli', 10, 3),
(4, 'Calle de Alcalá', 45, 4),
(5, 'Via del Corso', 20, 5),
(6, 'Baker Street', 221, 6),
(7, 'Karl Johans gate', 10, 7),
(8, 'Drottninggatan', 50, 8),
(9, 'Chiyoda', 1, 9),
(10, 'George Street', 100, 10);

INSERT INTO Supplier (SupplierID, Name, Email, PhoneNumber, AddressID) VALUES
(1, 'Tech Solutions', 'contact@techsolutions.com', '+48 123 456 789', 1),
(2, 'Green Supplies', 'info@greensupplies.de', '+49 234 567 890', 2),
(3, 'Paris Traders', 'sales@paristraders.fr', '+33 345 678 901', 3),
(4, 'Madrid Distributors', 'contact@madriddist.es', '+34 456 789 012', 4),
(5, 'Rome Wholesale', 'info@romewholesale.it', '+39 567 890 123', 5),
(6, 'London Imports', 'support@londonimports.co.uk', '+44 678 901 234', 6),
(7, 'Oslo Supplies', 'contact@oslosupplies.no', '+47 789 012 345', 7),
(8, 'Stockholm Goods', 'sales@stockholmgoods.se', '+46 890 123 456', 8),
(9, 'Tokyo Electronics', 'info@tokyoelectronics.jp', '+81 901 234 567', 9),
(10, 'Sydney Wholesale', 'contact@sydneywholesale.au', '+61 012 345 678', 10);

INSERT INTO Client (ClientID, Name, Email, PhoneNumber, AddressID) VALUES
(1, 'Jan Kowalski', 'jan.kowalski@example.com', '+48 111 222 333', 1),
(2, 'Anna Nowak', 'anna.nowak@example.de', '+49 222 333 444', 2),
(3, 'Pierre Dupont', 'pierre.dupont@example.fr', '+33 333 444 555', 3),
(4, 'Maria Garcia', 'maria.garcia@example.es', '+34 444 555 666', 4),
(5, 'Luca Bianchi', 'luca.bianchi@example.it', '+39 555 666 777', 5),
(6, 'John Smith', 'john.smith@example.co.uk', '+44 666 777 888', 6),
(7, 'Kari Nordmann', 'kari.nordmann@example.no', '+47 777 888 999', 7),
(8, 'Eva Svensson', 'eva.svensson@example.se', '+46 888 999 000', 8),
(9, 'Hiro Tanaka', 'hiro.tanaka@example.jp', '+81 999 000 111', 9),
(10, 'Emma Johnson', 'emma.johnson@example.au', '+61 000 111 222', 10);

INSERT INTO Warehouse (WarehouseID, Name, Capacity, OccupiedCapacity, AddressID) VALUES
(1, 'Central Warehouse', 10000, 0, 1),
(2, 'North Warehouse', 8000, 0, 2),
(3, 'South Warehouse', 12000, 0, 3),
(4, 'East Warehouse', 7000, 0, 4),
(5, 'West Warehouse', 9000, 0, 5),
(6, 'Warehouse A', 5000, 0, 6),
(7, 'Warehouse B', 11000, 0, 7),
(8, 'Warehouse C', 6000, 0, 8),
(9, 'Warehouse D', 13000, 0, 9),
(10, 'Warehouse E', 4000, 0, 10);

INSERT INTO Employee (EmployeeID, Name, Surname, Position, Email, PhoneNumber, AddressID, WarehouseID) VALUES
(1, 'Jan', 'Kowalski', 'Manager', 'jan.kowalski@example.com', '+48 111 222 333', 1, 1),
(2, 'Anna', 'Nowak', 'Sales Representative', 'anna.nowak@example.de', '+49 222 333 444', 2, 2),
(3, 'Pierre', 'Dupont', 'Warehouse Supervisor', 'pierre.dupont@example.fr', '+33 333 444 555', 3, 3),
(4, 'Maria', 'Garcia', 'Accountant', 'maria.garcia@example.es', '+34 444 555 666', 4, 1),
(5, 'Luca', 'Bianchi', 'HR Specialist', 'luca.bianchi@example.it', '+39 555 666 777', 5, 2),
(6, 'John', 'Smith', 'Logistics Coordinator', 'john.smith@example.co.uk', '+44 666 777 888', 6, 3),
(7, 'Kari', 'Nordmann', 'Customer Service', 'kari.nordmann@example.no', '+47 777 888 999', 7, 1),
(8, 'Eva', 'Svensson', 'Marketing Manager', 'eva.svensson@example.se', '+46 888 999 000', 8, 2),
(9, 'Hiro', 'Tanaka', 'IT Support', 'hiro.tanaka@example.jp', '+81 999 000 111', 9, 3),
(10, 'Emma', 'Johnson', 'Operations Manager', 'emma.johnson@example.au', '+61 000 111 222', 10, 1);

INSERT INTO Category (CategoryID, Name, Description) VALUES
(1, 'Electronics', 'Devices and gadgets including computers, phones, and accessories'),
(2, 'Furniture', 'Home and office furniture like chairs, tables, and desks'),
(3, 'Clothing', 'Apparel for men, women, and children'),
(4, 'Books', 'Printed and digital books across various genres'),
(5, 'Toys', 'Childrens toys and games'),
(6, 'Food & Beverages', 'Groceries, snacks, and drinks'),
(7, 'Sports Equipment', 'Gear and equipment for various sports'),
(8, 'Health & Beauty', 'Personal care and beauty products'),
(9, 'Automotive', 'Car parts, accessories, and maintenance products'),
(10, 'Garden & Outdoors', 'Tools and equipment for gardening and outdoor activities');

INSERT INTO Product (ProductID, Name, Description, UnitPrice, UnitSize, CategoryID) VALUES
(1, 'Smartphone', 'Latest model smartphone with 128GB storage', 699.99, 1, 1),
(2, 'Office Chair', 'Ergonomic office chair with adjustable height', 149.99, 15, 2),
(3, 'Jeans', 'Comfortable blue denim jeans', 39.99, 5, 3),
(4, 'Novel Book', 'Bestselling fiction novel', 14.99, 3, 4),
(5, 'Building Blocks', 'Plastic building blocks for children', 24.99, 1, 5),
(6, 'Olive Oil', 'Extra virgin olive oil 1L bottle', 9.99, 4, 6),
(7, 'Soccer Ball', 'Standard size 5 soccer ball', 29.99, 2, 7),
(8, 'Shampoo', 'Herbal shampoo for all hair types, 500ml', 6.99, 1, 8),
(9, 'Car Battery', '12V car battery with 60Ah capacity', 89.99, 15, 9),
(10, 'Garden Hose', '25 meters flexible garden hose', 34.99, 3, 10);
