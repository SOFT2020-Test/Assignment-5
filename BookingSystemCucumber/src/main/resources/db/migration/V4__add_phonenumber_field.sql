ALTER TABLE Customers
ADD COLUMN `phonenumber` VARCHAR(45) NULL AFTER `birthdate`;

ALTER TABLE Employees ADD COLUMN `clockIn` VARCHAR(45) NULL AFTER `birthdate`;
ALTER TABLE Employees ADD COLUMN `clockOut` VARCHAR(45) NULL AFTER `birthdate`;