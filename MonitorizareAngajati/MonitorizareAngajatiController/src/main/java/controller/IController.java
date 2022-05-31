package controller;

import model.Employee;
import model.Employer;
import model.Task;

public interface IController {
    void loginEmployer(Employer employer, IObserver client) throws Exception;

    void loginEmployee(Employee employee, IObserver client) throws Exception;

    Employer getEmployerByUsername(String username);

    Employee getEmployeeByUsername(String username);

    void addEmployee(Employee employee) throws Exception;

    void deleteEmployee(Employee employee) throws Exception;

    Iterable<Employee> getEmployeesOfAnEmployer(Employer employer);

    Iterable<Task> getTasksOfAnEmployee(Employee employee);

    Iterable<Task> getTasksOfAnEmployer(Employer employer);

    void deleteTask(Task task) throws Exception;

    void addTask(Task task) throws Exception;

    void updateTask(Task task) throws Exception;


    void logoutEmployer(Employer employer, IObserver observer) throws Exception;

    void logoutEmployee(Employee employee, IObserver observer) throws Exception;

    void updateEmployee(Employee employee) throws Exception;
}
