package services;

import model.Employee;
import model.Employer;
import model.Task;
import repository.IEmployeeRepository;
import repository.IEmployerRepository;

public class EmployerService {
    private IEmployerRepository repository;

    public EmployerService(IEmployerRepository repository) {
        this.repository = repository;
    }

    public Employer getEmployerByUsername(String username) {
        return this.repository.getEmployerByUsername(username);
    }

    public Iterable<Task> getTasksOfAnEmployeer(Employer employer) {
        return this.repository.getTasksOfAnEmployer(employer);
    }
}