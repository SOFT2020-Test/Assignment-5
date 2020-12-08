package servicelayer.employee;


import dto.Employee;

import java.util.Collection;
import java.util.Date;

public interface EmployeeService {
    int createEmployee(String firstName, String lastName, String dayEnd, String dayStart) throws EmployeeServiceException;
    Employee getEmployeeById(int id);
    Collection<Employee> getEmployeesByFirstname(String firstName) throws EmployeeServiceException;
    Collection<Employee> getAllEmployees() throws EmployeeServiceException;
}
