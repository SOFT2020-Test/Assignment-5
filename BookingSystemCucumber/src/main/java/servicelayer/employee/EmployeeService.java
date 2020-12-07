package servicelayer.employee;


import dto.Employee;

import java.util.Collection;
import java.util.Date;

public interface EmployeeService {
    int createEmployee(String firstName, String lastName, Date birthdate, String clockIn, String clockOut) throws EmployeeServiceException;
    Employee getEmployeeById(int id);
    Collection<Employee> getEmployeesByFirstname(String firstName) throws EmployeeServiceException;
    Collection<Employee> getAllEmployees() throws EmployeeServiceException;
}
