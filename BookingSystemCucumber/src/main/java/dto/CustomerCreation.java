package dto;

import java.util.Date;

public class CustomerCreation {

    public String getFirstname() {

        return firstname;
    }

    public String getLastname() {

        return lastname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public final String firstname, lastname, phoneNumber;
    public final Date birthday;

    public CustomerCreation(String firstname, String lastname, String phoneNumber, Date birthday) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }
}
