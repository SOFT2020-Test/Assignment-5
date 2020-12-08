ALTER TABLE Employees
    ADD COLUMN dayStart VARCHAR(45) NULL AFTER birthdate;
ALTER TABLE Employees
    ADD COLUMN dayEnd VARCHAR(45) NULL AFTER birthdate;