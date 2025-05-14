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
