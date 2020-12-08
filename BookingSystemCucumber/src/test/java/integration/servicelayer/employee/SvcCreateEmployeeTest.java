package integration.servicelayer.employee;


import com.github.javafaker.Faker;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.Employee;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;;
import servicelayer.employee.EmployeeService;
import servicelayer.employee.EmployeeServiceException;
import servicelayer.employee.EmployeeServiceImpl;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
class SvcCreateEmployeeTest {

    private EmployeeService svc;
    private EmployeeStorage storage;
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

        storage = new EmployeeStorageImpl(url + db,"root", PASSWORD);
        svc = new EmployeeServiceImpl(storage);
        faker = new Faker();
    }

    @Test
    public void mustSaveEmployeeToDatabaseWhenCreateEmployee() throws EmployeeServiceException, SQLException {
        // Arrange
        var firstName = "John";
        var lastName = "Johnson";
        int id = svc.createEmployee(firstName, lastName,"16:00", "08:00");

        // Act
        var createdEmployee = storage.getEmployeeWithId(id);

        // Assert
        for (Employee x: createdEmployee) {
            assertEquals(firstName, x.getFirstname());
            assertEquals(lastName, x.getLastname());
        }
    }
}
