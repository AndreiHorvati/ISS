package controller;

import model.Employee;
import model.Employer;

public interface IController {
    void loginEmployer(Employer employer, IObserver client) throws Exception;

    Employer getEmployerByUsername(String username);

    void addEmployee(Employee employee) throws Exception;
}
