package integration.datalayer.customer;

import com.github.javafaker.Faker;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import dto.CustomerCreation;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class CreateCustomerTest {
    private CustomerStorage customerStorage;
    private Faker faker;

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

        customerStorage = new CustomerStorageImpl(url+db, "root", "password");
        faker = new Faker();

        var numCustomers = customerStorage.getCustomers().size();
        if (numCustomers < 5) {
            addFakeCustomers(5 - numCustomers);
        }
    }

    private void addFakeCustomers(int numCustomers) throws SQLException {
        for (int i = 0; i < numCustomers; i++) {
            CustomerCreation c = new CustomerCreation(faker.name().firstName(), faker.name().lastName(), faker.phoneNumber().phoneNumber(), faker.date().birthday());
            customerStorage.createCustomer(c);
        }

    }

    @Test
    public void mustSaveCustomerInDatabaseWhenCallingCreateCustomer() throws SQLException {
        // Arrange
        // Act
        customerStorage.createCustomer(new CustomerCreation("John","Carlssonn", faker.phoneNumber().phoneNumber(), faker.date().birthday()));

        // Assert
        var customers = customerStorage.getCustomers();
        assertTrue(
                customers.stream().anyMatch(x ->
                        x.getFirstname().equals("John") &&
                        x.getLastname().equals("Carlssonn")));
    }

    @Test
    public void mustReturnLatestId() throws SQLException {
        // Arrange
        // Act
        var id1 = customerStorage.createCustomer(new CustomerCreation("a", "b", faker.phoneNumber().phoneNumber(), faker.date().birthday()));
        var id2 = customerStorage.createCustomer(new CustomerCreation("c", "d", faker.phoneNumber().phoneNumber(), faker.date().birthday()));

        // Assert
        assertEquals(1, id2 - id1);
    }
}
