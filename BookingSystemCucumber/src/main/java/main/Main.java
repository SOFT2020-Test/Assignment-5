package main;

import com.github.javafaker.Faker;
import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorageImpl;
import dto.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Logger;

public class Main {

    private static final String connectionString = "jdbc:mysql://localhost:3307/BookingSystem";
    private static final String username = "root";
    private static final String password = "password";
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws SQLException, ParseException {
        CustomerStorageImpl storage = new CustomerStorageImpl(connectionString, username, password);
        EmployeeStorageImpl eStorage = new EmployeeStorageImpl(connectionString, username, password);
        BookingStorageImpl bStorage = new BookingStorageImpl(connectionString, username, password);

        var custSize = storage.getCustomers().size();
        var empSize = eStorage.getEmployees().size();

        if(custSize < 10 || empSize < 10) {
            createFakeEmployees(10);
            createFakeCostumers(10);

            BookingCreation t = new BookingCreation(3,3, SQLConverter.convertToSQLDate(new Date()),"20:15","21:15");
            bStorage.createBooking(t);
            //System.out.println("DATABASE SETUP COMPLETE");
            LOGGER.info("[LOG] DATABASE SETUP COMPLETE");
        }

    }

    public static void createFakeEmployees(int amount) throws SQLException {
        EmployeeStorageImpl eStorage = new EmployeeStorageImpl(connectionString, username, password);
        Faker faker = new Faker();
        for (int i = 0; i < amount; i++) {
            EmployeeCreation c = new EmployeeCreation(faker.name().firstName(), faker.name().lastName(), "08:00", "16:00");
            eStorage.createEmployee(c);
        }
    }

    public static void createFakeCostumers(int amount) throws SQLException {
        CustomerStorageImpl cStorage = new CustomerStorageImpl(connectionString, username, password);
        Faker faker = new Faker();
        for (int i = 0; i < amount; i++) {
             CustomerCreation c = new CustomerCreation(faker.name().firstName(), faker.name().lastName(), faker.phoneNumber().toString(), faker.date().birthday());
            cStorage.createCustomer(c);
        }
    }
}
