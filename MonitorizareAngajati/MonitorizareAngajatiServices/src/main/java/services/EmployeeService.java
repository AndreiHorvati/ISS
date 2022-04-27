package services;

import model.Employee;
import repository.IEmployeeRepository;

public class EmployeeService {
    private IEmployeeRepository repository;

    public EmployeeService(IEmployeeRepository repository) {
        this.repository = repository;
    }

    public void save(Employee employee) {
        this.repository.save(employee);
    }
}
