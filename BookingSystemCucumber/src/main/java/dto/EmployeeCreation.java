package dto;

public class EmployeeCreation {
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getDayStart() {
        return dayStart;
    }

    public String getDayEnd() {
        return dayEnd;
    }

    public final String firstname, lastname, dayStart, dayEnd;

    public EmployeeCreation(String firstname, String lastname, String dayStart, String dayEnd) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
    }
}
