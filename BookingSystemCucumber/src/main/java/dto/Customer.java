package dto;

import java.util.Date;

public class Customer {
    private final int id;
    private final String firstname, lastname, phonenumber;
    private final Date birthdate;

    public Customer(int id, String firstname, String lastname, String phonenumber, Date birthday) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.birthdate = birthday;
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

    public String getPhonenumber() {
        return phonenumber;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", birthday=" + birthdate +
                '}';
    }
}
