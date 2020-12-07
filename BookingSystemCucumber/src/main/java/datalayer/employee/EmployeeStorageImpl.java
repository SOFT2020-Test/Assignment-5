package datalayer.employee;

import dto.Employee;
import dto.EmployeeCreation;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmployeeStorageImpl implements EmployeeStorage {
    private String connectionString;
    private String username, password;

    public EmployeeStorageImpl(String conStr, String user, String pass) {
        connectionString = conStr;
        username = user;
        password = pass;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }

    @Override
    public Collection<Employee> getEmployeeWithId(int employeeId) throws SQLException {
        var sql = "select ID, firstname, lastname from Employees where id = ?";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            var results = new ArrayList<Employee>();

            try (var resultSet = stmt.executeQuery()) {
                if (resultSet.next()){
                    var id = resultSet.getInt("id");
                    var firstname = resultSet.getString("firstname");
                    var lastname = resultSet.getString("lastname");
                    Employee e= new Employee(id, firstname, lastname);
                    results.add(e);


                }
                return results;
            }
        }
    }



    public int createEmployee(EmployeeCreation employeeToCreate) throws SQLException {
        var sql = "insert into Employees(firstname, lastname) values (?, ?)";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, employeeToCreate.getFirstname());
            stmt.setString(2, employeeToCreate.getLastname());

            stmt.executeUpdate();

            // get the newly created id
            try (var resultSet = stmt.getGeneratedKeys()) {
                resultSet.next();
                return resultSet.getInt(1);
            }
        }
    }

    @Override
    public List<Employee> getEmployees() throws SQLException {
        try (var con = getConnection();
             var stmt = con.createStatement()) {
            var results = new ArrayList<Employee>();
            ResultSet resultSet = stmt.executeQuery("select ID, firstname, lastname from Customers");
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                Employee e = new Employee(id, firstname, lastname);
                results.add(e);
            }
            return results;
        }
    }
}

