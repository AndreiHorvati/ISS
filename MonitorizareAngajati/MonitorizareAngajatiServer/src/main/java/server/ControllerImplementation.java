package server;

import controller.IController;
import controller.IObserver;
import model.Employee;
import model.Employer;
import services.EmployeeService;
import services.EmployerService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ControllerImplementation implements IController {
    private EmployeeService employeeService;
    private EmployerService employerService;

    private Map<Long, IObserver> loggedEmployers;
    private Map<Long, IObserver> loggedEmployees;

    public ControllerImplementation(EmployerService employerService, EmployeeService employeeService) {
        this.employerService = employerService;
        this.employeeService = employeeService;

        this.loggedEmployers = new ConcurrentHashMap<>();
        this.loggedEmployees = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void loginEmployee(Employee employee, IObserver client) throws Exception {
        Employee searchedEmployee = this.employeeService.getEmployeeByUsername(employee.getUsername());

        if (searchedEmployee != null) {
            if (loggedEmployees.get(searchedEmployee.getId()) != null) {
                throw new Exception("Employee already logged in.");
            }

            loggedEmployees.put(searchedEmployee.getId(), client);
            //notifyOfficePersonsLoggedIn(officePerson);
        } else {
            throw new Exception("Authentication failed.");
        }
    }

    @Override
    public synchronized void loginEmployer(Employer employer, IObserver client) throws Exception {
        Employer searchedEmployer = this.employerService.getEmployerByUsername(employer.getUsername());

        if (searchedEmployer != null) {
            if (loggedEmployers.get(searchedEmployer.getId()) != null) {
                throw new Exception("Employer already logged in.");
            }

            loggedEmployers.put(searchedEmployer.getId(), client);
            //notifyOfficePersonsLoggedIn(officePerson);

            System.out.println(loggedEmployers.size());
        } else {
            throw new Exception("Authentication failed.");
        }
    }

    @Override
    public Employer getEmployerByUsername(String username) {
        return this.employerService.getEmployerByUsername(username);
    }

    @Override
    public Employee getEmployeeByUsername(String username) {
        return this.employeeService.getEmployeeByUsername(username);
    }

    @Override
    public Iterable<Employee> getEmployeesOfAnEmployer(Employer employer) {
        return this.employeeService.getEmployeesOfAnEmployer(employer);
    }

    @Override
    public void deleteEmployee(Employee employee) throws Exception {
        this.employeeService.deleteEmployee(employee);

        for (Map.Entry<Long, IObserver> entry : loggedEmployers.entrySet()) {
            if (entry.getKey().equals(employee.getEmployer().getId())) {
                entry.getValue().employeeDeleted();
            }
        }
    }

    @Override
    public void addEmployee(Employee employee) throws Exception {
        this.employeeService.save(employee);

        for (Map.Entry<Long, IObserver> entry : loggedEmployers.entrySet()) {
            if (entry.getKey().equals(employee.getEmployer().getId())) {
                entry.getValue().employeeAdded();
            }
        }
    }
}
