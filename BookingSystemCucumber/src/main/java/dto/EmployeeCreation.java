package dto;

public class EmployeeCreation {
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public final String firstname, lastname, clockIn, clockOut;

    public EmployeeCreation(String firstname, String lastname, String clockIn, String clockOut) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
    }
}
