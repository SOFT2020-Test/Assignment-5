package unit.servicelayer.employee;


import com.github.javafaker.Faker;
import datalayer.employee.EmployeeStorage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import servicelayer.employee.EmployeeService;
import servicelayer.employee.EmployeeServiceException;
import servicelayer.employee.EmployeeServiceImpl;

import java.sql.SQLException;
import java.util.Date;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
public class CreateEmployeeTest {
    // SUT (System Under Test)
    private EmployeeService employeeService;

    // DOC (Depended-on Component)
    private EmployeeStorage storageMock;

    private Faker faker;


    @BeforeAll
    public void beforeAll(){
        storageMock = mock(EmployeeStorage.class);
        employeeService = new EmployeeServiceImpl(storageMock);
        faker = new Faker();
    }

    @Test
    public void mustCallStorageWhenCreatingCustomer() throws SQLException, EmployeeServiceException {
        // Arrange
        // Act
        var firstName = "a";
        var lastName = "b";
        var birthdate = faker.date().birthday();
        employeeService.createEmployee(firstName, lastName, "08:00", "16:00");

        // Assert
        // Can be read like: verify that storageMock was called 1 time on the method
        //   'createEmployee' with an argument whose 'firstname' == firstName and
        //   whose 'lastname' == lastName

        verify(storageMock, times(1))
                .createEmployee(
                        argThat(x -> x.firstname.equals(firstName) &&
                                x.lastname.equals(lastName)));
    }
}
