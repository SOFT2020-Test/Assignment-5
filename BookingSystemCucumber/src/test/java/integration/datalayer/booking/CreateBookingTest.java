package integration.datalayer.booking;

import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import dto.BookingCreation;
import main.SQLConverter;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class CreateBookingTest {
    private BookingStorage bookingStorage;

   @BeforeAll
    public void Setup() throws SQLException {
        var url = "jdbc:mysql://localhost:3307/";
        var db = "BookingSystemTest";

        Flyway flyway = new Flyway(new FluentConfiguration()
                .defaultSchema(db)
                .createSchemas(true)
                .schemas(db)
                .target("4")
                .dataSource(url, "root", "password"));

        flyway.migrate();

        bookingStorage = new BookingStorageImpl(url+db, "root", "password");
    }


    @Test
    public void mustSaveCustomerInDatabaseWhenCallingCreateCustomer() throws SQLException {
        // Arrange
        // Act
        bookingStorage.createBooking(new BookingCreation(1, 1, SQLConverter.convertToSQLDate(new Date()), "15:00", "21:00"));

        // Assert
        var customers = bookingStorage.getBookingsForCustomer(1);
        assertTrue(
                customers.stream().anyMatch(x ->
                        x.getCustomerId() == 1 &&
                                x.getEmployeeId() == 1));
    }
/*
    @Test
    public void mustReturnLatestId() throws SQLException {
        // Arrange
        // Act
        var id1 =  bookingStorage.createBooking(new BookingCreation(1, 1, new Date(), "15:00", "21:00"));
        var id2 =  bookingStorage.createBooking(new BookingCreation(1, 1, new Date(), "15:00", "21:00"));

        // Assert
        assertEquals(1, id2 - id1);
    }*/
}
