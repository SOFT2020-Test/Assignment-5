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
    public int createEmployee(String firstName, String lastName, String dayEnd, String dayStart) throws EmployeeServiceException {
        try {
            EmployeeCreation em = new EmployeeCreation(firstName, lastName, dayEnd, dayStart);
            return employeeStorage.createEmployee(em);
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
