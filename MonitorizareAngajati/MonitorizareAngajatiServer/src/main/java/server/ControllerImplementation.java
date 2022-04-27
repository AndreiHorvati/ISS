package server;

import controller.IController;
import controller.IObserver;
import model.Employer;
import services.EmployeeService;
import services.EmployerService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ControllerImplementation implements IController {
    private EmployeeService employeeService;
    private EmployerService employerService;

    private Map<Long, IObserver> loggedEmployers;

    public ControllerImplementation(EmployerService employerService, EmployeeService employeeService) {
        this.employerService = employerService;
        this.employeeService = employeeService;

        this.loggedEmployers = new ConcurrentHashMap<>();
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
        return null;
    }
}
