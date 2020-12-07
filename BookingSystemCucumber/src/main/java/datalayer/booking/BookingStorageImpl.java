package datalayer.booking;

import dto.Booking;
import dto.BookingCreation;
import dto.Employee;
import main.SQLConverter;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class BookingStorageImpl implements BookingStorage {

    private String connectionString;
    private String username, password;

    public BookingStorageImpl(String conStr, String user, String pass) {
        connectionString = conStr;
        username = user;
        password = pass;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }


    @Override
    public int createBooking(BookingCreation bookingToCreate) throws SQLException {

        Collection<Employee> empList = getEmployeeWithId(bookingToCreate.getEmployeeId());
        for(Employee emp : empList)
        {
            if(validateWithinWorkingHours(bookingToCreate, emp))
            {
                if(seeIfTimeIsAvalible(bookingToCreate))
                {
                    var sql = "insert into Bookings(customerId,employeeId,date,start,end) values (?,?,?,?,?)";
                    try (var con = getConnection();
                         var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                        stmt.setInt(1, bookingToCreate.getCustomerId());
                        stmt.setInt(2, bookingToCreate.getEmployeeId());
                        stmt.setDate(3, SQLConverter.convertToSQLDate(bookingToCreate.getDate()));
                        stmt.setString(4, bookingToCreate.getStart());
                        stmt.setString(5, bookingToCreate.getEnd());
                        stmt.executeUpdate();
                        // get the newly created id
                        try (var resultSet = stmt.getGeneratedKeys()) {
                            resultSet.next();
                            int newId = resultSet.getInt(1);
                            return newId;
                        }
                    }
                }
            }
        }

        return -1;
    }



    @Override
    public Collection<Booking> getBookingsForCustomer(int customerId) throws SQLException {
        var sql = "select ID, customerId, employeeId,date,start,end from Bookings where customerId= ?";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            var results = new ArrayList<Booking>();
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int employeeId = resultSet.getInt("employeeId");
                String date = resultSet.getString("date");
                String start = resultSet.getString("start");
                String end = resultSet.getString("end");
                Booking c = new Booking(id, customerId, employeeId, date, start, end);
                results.add(c);
            }
            return results;
        }

    }

    @Override
    public Collection<Booking> getBookingsForEmployee(int employeeId) throws SQLException {
        var sql = "select ID, customerId, employeeId,date,start,end from Bookings where customerId= ?";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            var results = new ArrayList<Booking>();
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int customerId = resultSet.getInt("customerId");
                String date = resultSet.getString("date");
                String start = resultSet.getString("start");
                String end = resultSet.getString("end");
                Booking c = new Booking(id, customerId, employeeId, date, start, end);
                results.add(c);
            }
            return results;
        }

    }

    @Override
    public Booking getBookingById(int bookingId) throws SQLException{
        var sql = "select * from Bookings where id = ?";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);

            try (var resultSet = stmt.executeQuery()) {
                if (resultSet.next()){
                    var id = resultSet.getInt("ID");
                    var customerId = resultSet.getInt("customerId");
                    var employeeId = resultSet.getInt("employeeId");
                    var date = resultSet.getString("date");
                    var start = resultSet.getString("start");
                    var end = resultSet.getString("end");
                    return new Booking(id, customerId, employeeId, date, start, end);
                }
                return null;
            }
        }
    }

    @Override
    public Collection<Booking> getBookingsForEmployeeOnDate(int employeeId, String dateInUTC) throws SQLException
    {
        var sql = "select * from Bookings where employeeId= ? and date = ? ";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            stmt.setString(2, dateInUTC);
            var results = new ArrayList<Booking>();
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int customerId = resultSet.getInt("customerId");
                String date = resultSet.getString("date");
                String start = resultSet.getString("start");
                String end = resultSet.getString("end");
                Booking c = new Booking(id, customerId, employeeId, date, start, end);
                results.add(c);
            }
            return results;
        }
    }

    private Collection<Employee> getEmployeeWithId(int employeeId) throws SQLException {
        var sql = "select ID, firstname, lastname from Employees where id = ?";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            var results = new ArrayList<Employee>();

            try (var resultSet = stmt.executeQuery()) {
                if (resultSet.next()){
                    var id = resultSet.getInt("id");
                    var firstname = resultSet.getString("firstname");
                    var lastname = resultSet.getString("lastname");
                    var clockIn = resultSet.getString("clockIn");
                    var clockOut = resultSet.getString("clockOut");
                    Employee e= new Employee(id, firstname, lastname, clockIn, clockOut);
                    results.add(e);


                }
                return results;
            }
        }
    }

    private boolean validateWithinWorkingHours(BookingCreation bc, Employee e)
    {

        if(bc.getDate().getDay() != 0 )
        {
            if(bc.getDate() != null && bc.getStart() != null && bc.getEnd() != null)
            {
                try {
                    java.util.Date time1 = new SimpleDateFormat("HH:mm").parse(bc.getStart());
                    Calendar bookingStartTime = Calendar.getInstance();
                    bookingStartTime.setTime(time1);
                    bookingStartTime.add(Calendar.DATE, 1);

                    java.util.Date time2 = new SimpleDateFormat("HH:mm").parse(bc.getEnd());
                    Calendar bookingEndTime = Calendar.getInstance();
                    bookingEndTime.setTime(time2);
                    bookingEndTime.add(Calendar.DATE, 1);

                    java.util.Date refStart = new SimpleDateFormat("HH:mm").parse(e.getClockIn());
                    Calendar workStartTime = Calendar.getInstance();
                    workStartTime.setTime(refStart);
                    workStartTime.add(Calendar.DATE, 1);

                    Date refEnd = new SimpleDateFormat("HH:mm").parse(e.getClockOut());
                    Calendar workEndTime = Calendar.getInstance();
                    workEndTime.setTime(refEnd);
                    workEndTime.add(Calendar.DATE, 1);
                    if(bookingStartTime.getTime().after(workStartTime.getTime()) || bookingStartTime.getTime().equals(workStartTime.getTime()))
                    {
                        if(bookingEndTime.getTime().before(workEndTime.getTime()) || bookingEndTime.getTime().equals(workEndTime.getTime()))
                        {
                            return true;
                        }
                    }
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return false;
    }

    private boolean seeIfTimeIsAvalible(BookingCreation bookingToCreate) throws SQLException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String strDate = dateFormat.format(bookingToCreate.getDate());
        Collection<Booking> list = getBookingsForEmployeeOnDate(bookingToCreate.getEmployeeId(), strDate);

        boolean canSave = false;


        for(Booking b : list)
        {

            try {
                java.util.Date time1 = new SimpleDateFormat("HH:mm").parse(bookingToCreate.getStart());
                Calendar bookingStartTime = Calendar.getInstance();
                bookingStartTime.setTime(time1);
                bookingStartTime.add(Calendar.DATE, 1);

                java.util.Date time2 = new SimpleDateFormat("HH:mm").parse(bookingToCreate.getEnd());
                Calendar bookingEndTime = Calendar.getInstance();
                bookingEndTime.setTime(time2);
                bookingEndTime.add(Calendar.DATE, 1);

                java.util.Date refStart = new SimpleDateFormat("HH:mm").parse(b.getStart());
                Calendar currentStartTime = Calendar.getInstance();
                currentStartTime.setTime(refStart);
                currentStartTime.add(Calendar.DATE, 1);

                Date refEnd = new SimpleDateFormat("HH:mm").parse(b.getEnd());
                Calendar currentEndTime = Calendar.getInstance();
                currentEndTime.setTime(refEnd);
                currentEndTime.add(Calendar.DATE, 1);

                boolean areBothTimesBefore = false;
                boolean areBothTimesAfter = false;

                //"09:00 is before 12:00"
                if(bookingStartTime.getTime().before(currentStartTime.getTime()))
                {
                    //"09:00 is before 16:00"
                    if(bookingStartTime.getTime().before(currentEndTime.getTime()))
                    {
                       //11:00 is before 12:00
                        if(bookingEndTime.getTime().before(currentStartTime.getTime()))
                        {
                            //11:00 is before 16:00
                            if(bookingEndTime.getTime().before(currentEndTime.getTime()))
                            {
                                areBothTimesBefore = true;
                            }
                        }
                    }
                }

                //17:00 is after 12:00
                if(bookingStartTime.getTime().after(currentStartTime.getTime()))
                {
                    //17:00 is after 16:00
                    if(bookingStartTime.getTime().after(currentEndTime.getTime()))
                    {
                        //19:00 is after 12:00
                        if(bookingEndTime.getTime().after(currentStartTime.getTime()))
                        {
                            //19:00 is after 16:00
                            if(bookingEndTime.getTime().after(currentEndTime.getTime()))
                            {
                                areBothTimesAfter = true;
                            }
                        }
                    }
                }

                if(!areBothTimesAfter || !areBothTimesBefore)
                {
                    return false;
                }


            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }
}
