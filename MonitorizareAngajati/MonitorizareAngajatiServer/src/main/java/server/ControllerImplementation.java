package server;

import controller.IController;
import controller.IObserver;
import model.Employee;
import model.Employer;
import model.Task;
import services.EmployeeService;
import services.EmployerService;
import services.TaskService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ControllerImplementation implements IController {
    private EmployeeService employeeService;
    private EmployerService employerService;
    private TaskService taskService;

    private Map<Long, IObserver> loggedEmployers;
    private Map<Long, IObserver> loggedEmployees;

    public ControllerImplementation(EmployerService employerService, EmployeeService employeeService, TaskService taskService) {
        this.employerService = employerService;
        this.employeeService = employeeService;
        this.taskService = taskService;

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
    public synchronized Employer getEmployerByUsername(String username) {
        return this.employerService.getEmployerByUsername(username);
    }

    @Override
    public synchronized Employee getEmployeeByUsername(String username) {
        return this.employeeService.getEmployeeByUsername(username);
    }

    @Override
    public synchronized Iterable<Employee> getEmployeesOfAnEmployer(Employer employer) {
        return this.employeeService.getEmployeesOfAnEmployer(employer);
    }

    @Override
    public synchronized Iterable<Task> getTasksOfAnEmployee(Employee employee) {
        return this.taskService.getTasksOfAnEmployee(employee);
    }

    @Override
    public synchronized Iterable<Task> getTasksOfAnEmployer(Employer employer) {
        return this.employerService.getTasksOfAnEmployeer(employer);
    }

    @Override
    public synchronized void deleteEmployee(Employee employee) throws Exception {
        this.employeeService.deleteEmployee(employee);

        for (Map.Entry<Long, IObserver> entry : loggedEmployers.entrySet()) {
            if (entry.getKey().equals(employee.getEmployer().getId())) {
                entry.getValue().employeeDeleted();
            }
        }
    }

    @Override
    public synchronized void deleteTask(Task task) throws Exception {
        this.taskService.deleteTask(task);

        for (Map.Entry<Long, IObserver> entry : loggedEmployers.entrySet()) {
            if (entry.getKey().equals(task.getEmployee().getEmployer().getId())) {
                entry.getValue().taskDeleted();
            }
        }

        for (Map.Entry<Long, IObserver> entry : loggedEmployees.entrySet()) {
            if (entry.getKey().equals(task.getEmployee().getId())) {
                entry.getValue().taskDeleted();
            }
        }
    }

    @Override
    public synchronized void addEmployee(Employee employee) throws Exception {
        this.employeeService.save(employee);

        for (Map.Entry<Long, IObserver> entry : loggedEmployers.entrySet()) {
            if (entry.getKey().equals(employee.getEmployer().getId())) {
                entry.getValue().employeeAdded();
            }
        }
    }

    @Override
    public synchronized void addTask(Task task) throws Exception {
        this.taskService.save(task);

        for (Map.Entry<Long, IObserver> entry : loggedEmployees.entrySet()) {
            if (entry.getKey().equals(task.getEmployee().getId())) {
                entry.getValue().taskAdded();
            }
        }
    }

    @Override
    public synchronized void updateTask(Task task) throws Exception {
        this.taskService.updateTask(task);

        try {
            for (Map.Entry<Long, IObserver> entry : loggedEmployers.entrySet()) {
                if (entry.getKey().equals(task.getEmployee().getEmployer().getId())) {
                    entry.getValue().taskUpdated();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        for (Map.Entry<Long, IObserver> entry : loggedEmployees.entrySet()) {
            if (entry.getKey().equals(task.getEmployee().getId())) {
                entry.getValue().taskUpdated();
            }
        }
    }

    @Override
    public synchronized void logoutEmployer(Employer employer, IObserver observer) throws Exception {
        IObserver localClient = (IObserver) this.loggedEmployers.remove(employer.getId());
    }

    @Override
    public synchronized void logoutEmployee(Employee employee, IObserver observer) throws Exception {
        IObserver localClient = (IObserver) this.loggedEmployees.remove(employee.getId());
    }

    @Override
    public void updateEmployee(Employee employee) throws Exception {
        System.out.println("Am ajuns in controller!");
        System.out.println(employee.getHour());
        System.out.println(employee.getId());
        this.employeeService.updateEmployee(employee);

        for (Map.Entry<Long, IObserver> entry : loggedEmployers.entrySet()) {
            if (entry.getKey().equals(employee.getEmployer().getId())) {
                entry.getValue().employeeUpdated();
            }
        }
    }
}
