package dto;

public class Employee {
    private final int id;
    private final String firstname, lastname, dayStart, dayEnd;;

    public Employee(int id, String firstname, String lastname, String dayStart, String dayEnd) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
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

    public String getdayStart() {
        return dayStart;
    }

    public String getdayEnd() {
        return dayEnd;
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
