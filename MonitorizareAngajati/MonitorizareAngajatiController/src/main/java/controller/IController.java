package controller;

import model.Employee;
import model.Employer;

public interface IController {
    void loginEmployer(Employer employer, IObserver client) throws Exception;

    void loginEmployee(Employee employee, IObserver client) throws Exception;

    Employer getEmployerByUsername(String username);

    Employee getEmployeeByUsername(String username);

    void addEmployee(Employee employee) throws Exception;

    Iterable<Employee> getEmployeesOfAnEmployer(Employer employer);

    void deleteEmployee(Employee employee) throws Exception;
}
