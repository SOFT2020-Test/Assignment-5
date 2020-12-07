package dto;

public class Employee {
    private final int id;
    private final String firstname, lastname, clockIn, clockOut;;

    public Employee(int id, String firstname, String lastname, String clockIn, String clockOut) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getClockIn() {
        return clockIn;
    }

    public String getClockOut() {
        return clockOut;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
