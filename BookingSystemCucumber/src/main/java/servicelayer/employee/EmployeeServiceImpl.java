package servicelayer.employee;

import datalayer.employee.EmployeeStorage;
import dto.Employee;
import dto.EmployeeCreation;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeStorage employeeStorage;

    public EmployeeServiceImpl(EmployeeStorage empstorage) {
        this.employeeStorage = empstorage;
    }


    @Override
    public int createEmployee(String firstName, String lastName, Date birthdate, String clockIn, String clockOut) throws EmployeeServiceException {
        try {
            return employeeStorage.createEmployee(new EmployeeCreation(firstName, lastName, clockIn, clockOut));
        } catch (SQLException throwables) {
            throw new EmployeeServiceException(throwables.getMessage());
        }
    }

    @Override
    public Employee getEmployeeById(int id) {
        return null;
    }

    @Override
    public Collection<Employee> getEmployeesByFirstname(String firstName) throws EmployeeServiceException {
        return null;
    }

    @Override
    public Collection<Employee> getAllEmployees() throws EmployeeServiceException {
       try {
           return employeeStorage.getEmployees();
       } catch(SQLException throwables) {
           throw new EmployeeServiceException(throwables.getMessage());
       }
    }
}
