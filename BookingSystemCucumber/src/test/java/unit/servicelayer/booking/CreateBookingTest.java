package unit.servicelayer.booking;

import com.github.javafaker.Faker;
import datalayer.booking.BookingStorage;
import datalayer.customer.CustomerStorage;
import dto.BookingCreation;
import dto.SmsMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import servicelayer.booking.BookingService;
import servicelayer.booking.BookingServiceException;
import servicelayer.booking.BookingServiceImpl;
import servicelayer.customer.CustomerService;
import servicelayer.customer.CustomerServiceException;
import servicelayer.customer.CustomerServiceImpl;
import servicelayer.notifications.SmsServiceException;

import java.sql.SQLException;
import java.util.Date;

import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
public class CreateBookingTest {

    // SUT (System Under Test)
    private BookingService bookingService;
    private CustomerService customerService;

    // DOC (Depended-on Component)
    private BookingStorage storageMock;

    private Faker faker;


    @BeforeAll
    public void beforeAll(){
        storageMock = mock(BookingStorage.class);
        bookingService = new BookingServiceImpl(storageMock);
        faker = new Faker();
    }

    @Test
    public void mustCallStorageWhenCreatingBooking() throws BookingServiceException, SQLException {
        // Arrange
        // Act

        int customerId = 5;
        int employeeId = 1;
        Date date = faker.date().birthday();
        String start = "09:15";
        String end = "15:15";

        String recipient = faker.phoneNumber().phoneNumber();
        String message = "Thank you for your order!";
        bookingService.createBooking(customerId, employeeId, date, start, end, new SmsMessage(recipient, message));

        // Assert
        // Can be read like: verify that storageMock was called 1 time on the method
        //   'createBooking' with an argument whose 'customerId' == customerId and
        //   whose 'employeeId' == employeeId
        verify(storageMock, times(1))
                .createBooking(
                        argThat(x -> x.getCustomerId() == customerId &&
                                x.getEmployeeId() == employeeId));
    }
}
