package datalayer.booking;

import dto.Booking;
import dto.BookingCreation;
import main.SQLConverter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

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
}
