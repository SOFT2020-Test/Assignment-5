package integration.servicelayer.customer;

import com.github.javafaker.Faker;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import servicelayer.customer.CustomerService;
import servicelayer.customer.CustomerServiceException;
import servicelayer.customer.CustomerServiceImpl;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
class SvcCreateCustomerTest {

    private CustomerService svc;
    private CustomerStorage storage;
    private Faker faker;

    private static final int PORT = 3306;
    private static final String PASSWORD = "password";

    @Container
    public static MySQLContainer mysql = (MySQLContainer) new MySQLContainer(DockerImageName.parse("mysql"))
            .withPassword(PASSWORD)
            .withExposedPorts(PORT);

    // A generic container could be used as well:
//    public static GenericContainer mysql = new GenericContainer(DockerImageName.parse("mysql"))
//            .withExposedPorts(PORT)
//            .withEnv("MYSQL_ROOT_PASSWORD", PASSWORD);

   @BeforeAll
    public void setup() {
        System.err.println("mysql created: " + mysql.isCreated());
        System.err.println("mysql running: " + mysql.isRunning());
        System.err.println("mysql host: " + mysql.getHost());
        String url = "jdbc:mysql://"+mysql.getHost()+":"+mysql.getFirstMappedPort()+"/";
        String db = "BookingSystemTest";
        Flyway flyway = new Flyway(
                new FluentConfiguration()
                        .schemas(db)
                        .defaultSchema(db)
                        .createSchemas(true)
                        .target("4")
                        .dataSource(url, "root", PASSWORD)
        );
        flyway.migrate();

        storage = new CustomerStorageImpl(url + db,"root", PASSWORD);
        svc = new CustomerServiceImpl(storage);
        faker = new Faker();
    }

    @Test
    void migrate() {
        // migration starts automatically,
        // since Spring Boot runs the Flyway scripts on startup
    }

    @Test
    public void mustSaveCustomerToDatabaseWhenCallingCreateCustomer() throws CustomerServiceException, SQLException {
        // Arrange
        var firstName = "John";
        var lastName = "Johnson";
        var phonenumber = faker.phoneNumber().phoneNumber();
        var bday = faker.date().birthday();
        int id = svc.createCustomer(firstName, lastName, bday, phonenumber);

        // Act
        var createdCustomer = storage.getCustomerWithId(id);

        // Assert
        assertEquals(firstName, createdCustomer.getFirstname());
        assertEquals(lastName, createdCustomer.getLastname());
    }
}
