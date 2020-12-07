package cucumbertest;

import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import dto.BookingCreation;
import dto.SmsMessage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import main.SQLConverter;
import servicelayer.booking.BookingService;
import servicelayer.booking.BookingServiceException;
import servicelayer.booking.BookingServiceImpl;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class CreateBookingTest {

    private Date date;
    private String startTime, endTime;
    private boolean result;
    private BookingStorage bookingStorage;
    private int employeeId;

    @Given("the booking is within {string}, {string}, {string}, and {string}")
    public void today_is(String today, String startTime, String endTime, String employeeId) throws ParseException {
        this.date =new SimpleDateFormat("dd/MM/yyyy").parse(today);
        this.employeeId = Integer.parseInt(employeeId);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @When("i want to create booking")
    public void i_ask_whether_it_s_Friday_yet() throws SQLException, BookingServiceException {
        BookingCreation bc = new BookingCreation(1, 1, date, startTime, endTime);
        bookingStorage = new BookingStorageImpl("jdbc:mysql://localhost:3307/BookingSystemTest", "root", "password");
        BookingService bs = new BookingServiceImpl(bookingStorage);
        int resultInt = bs.createBooking(1, 1, date, startTime, endTime, new SmsMessage("bla", "bla"));
        if(resultInt == -1)
        {
            result = false;
        }
        else
        {
            result = true;
        }
    }

    @Then("i should be told {string}")
    public void i_should_be_told(String expectedAnswer) {
        if(expectedAnswer.equals("true"))
        {
            assertEquals (true, result);
        }
        else
        {
            assertEquals (false, result);
        }

    }


}
