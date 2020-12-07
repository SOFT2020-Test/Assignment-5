package integration.datalayer.employee;

import com.github.javafaker.Faker;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.EmployeeCreation;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class CreateEmployeeTest {
    private EmployeeStorage employeeStorage;

    @BeforeAll
    public void Setup() throws SQLException {
        var url = "jdbc:mysql://localhost:3307/";
        var db = "BookingSystemTest";

        Flyway flyway = new Flyway(new FluentConfiguration()
                .defaultSchema(db)
                .createSchemas(true)
                .schemas(db)
                .target("4")
                .dataSource(url, "root", "password"));

        flyway.migrate();

        employeeStorage = new EmployeeStorageImpl(url+db, "root", "password");

        var numEmployees = employeeStorage.getEmployees().size();
        if (numEmployees < 25) {
            addFakeEmployees(25 - numEmployees);
        }
    }

    @Test
    void migrate() {
        // migration starts automatically,
        // since Spring Boot runs the Flyway scripts on startup
    }

    private void addFakeEmployees(int numCustomers) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numCustomers; i++) {
            EmployeeCreation e = new EmployeeCreation(faker.name().firstName(), faker.name().lastName(), "08:00", "16:00");
            employeeStorage.createEmployee(e);
        }

    }

    @Test
    public void mustSaveCustomerInDatabaseWhenCallingCreateCustomer() throws SQLException {
        // Arrange
        // Act
        employeeStorage.createEmployee(new EmployeeCreation("John","Carlssonn", "08:00", "16:00"));

        // Assert
        var customers = employeeStorage.getEmployees();
        assertTrue(
                customers.stream().anyMatch(x ->
                        x.getFirstname().equals("John") &&
                        x.getLastname().equals("Carlssonn")));
    }

    @Test
    public void mustReturnLatestId() throws SQLException {
        // Arrange
        // Act
        var id1 = employeeStorage.createEmployee(new EmployeeCreation("a", "b", "08:00", "16:00"));
        var id2 = employeeStorage.createEmployee(new EmployeeCreation("c", "d", "08:00", "16:00"));

        // Assert
        assertEquals(1, id2 - id1);
    }
}
