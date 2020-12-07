package dto;

import java.sql.Time;
import java.util.Date;

public class Booking {
    private final int id;
    private final String date;
    private final String start;
    private final String end;
    private final int customerId, employeeId;


    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", customerId=" + customerId +
                ", employeeId=" + employeeId +
                '}';
    }

    public Booking(int id, int customerId, int employeeId, String date, String start, String end ) {
        this.id = id;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.date = date;
        this.start = start;
        this.end = end;

    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getEmployeeId() {
        return employeeId;
    }
}

