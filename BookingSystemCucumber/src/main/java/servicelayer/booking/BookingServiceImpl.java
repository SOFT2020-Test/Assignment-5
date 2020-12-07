package servicelayer.booking;

import datalayer.booking.BookingStorage;
import dto.Booking;
import dto.BookingCreation;
import dto.SmsMessage;
import servicelayer.customer.CustomerService;
import servicelayer.customer.CustomerServiceException;
import servicelayer.customer.CustomerServiceImpl;
import servicelayer.notifications.SmsService;
import servicelayer.notifications.SmsServiceException;
import servicelayer.notifications.SmsServiceImpl;

import java.awt.print.Book;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;


public class BookingServiceImpl implements BookingService{
    private BookingStorage bookingStorage;
    private SmsServiceImpl smsService = new SmsServiceImpl();

    public BookingServiceImpl(BookingStorage bookingStorage) {
        this.bookingStorage = bookingStorage;
    }


    @Override
    public int createBooking(int customerId, int employeeId, Date date, String start, String end, SmsMessage message) throws BookingServiceException {
        try {
            smsService.sendSms(message);
            BookingCreation bc = new BookingCreation(customerId, employeeId, date, start, end);
            if(bc.validateWithinWorkingHours())
            {
                return bookingStorage.createBooking(bc);
            }
        } catch(SQLException throwables) {
            throw new BookingServiceException(throwables.getMessage());
        }
        return -1;
    }

    @Override
    public Collection<Booking> getBookingsForCustomer(int customerId) throws BookingServiceException {
        try {
            return bookingStorage.getBookingsForCustomer(customerId);
        } catch(SQLException throwables) {
            throw new BookingServiceException(throwables.getMessage());
        }
    }

    @Override
    public Collection<Booking> getBookingsForEmployee(int employeeId) throws BookingServiceException {
        try {
            return bookingStorage.getBookingsForEmployee(employeeId);
        } catch(SQLException throwables) {
            throw new BookingServiceException(throwables.getMessage());
        }
    }

    @Override
    public Booking getBookingById(int id) throws BookingServiceException {
        try {
            return bookingStorage.getBookingById(id);
        } catch(SQLException throwables) {
            throw new BookingServiceException(throwables.getMessage());
        }
    }
}
