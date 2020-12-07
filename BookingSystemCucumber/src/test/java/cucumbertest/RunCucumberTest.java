package cucumbertest;

import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;

import java.sql.SQLException;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"})
public class RunCucumberTest {

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
    }
}