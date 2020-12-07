package servicelayer.booking;

import dto.*;
import servicelayer.customer.CustomerServiceException;
import servicelayer.notifications.SmsServiceException;

import java.util.Collection;
import java.util.Date;


public interface BookingService {

     int createBooking(int customerId, int employeeId, Date date, String start, String end, SmsMessage message) throws BookingServiceException;
     Collection<Booking> getBookingsForCustomer(int customerId)throws BookingServiceException;
     Collection<Booking> getBookingsForEmployee(int employeeId)throws BookingServiceException;
     Booking getBookingById(int id) throws BookingServiceException;
}
