package services;

import model.Employee;
import model.Employer;
import repository.IEmployeeRepository;

public class EmployeeService {
    private IEmployeeRepository repository;

    public EmployeeService(IEmployeeRepository repository) {
        this.repository = repository;
    }

    public void save(Employee employee) {
        this.repository.save(employee);
    }

    public Iterable<Employee> getEmployeesOfAnEmployer(Employer employer) {
        return this.repository.getEmployeesOfAnEmployer(employer);
    }

    public Employee getEmployeeByUsername(String username) {
        return this.repository.getEmployeeByUsername(username);
    }

    public void deleteEmployee(Employee employee) {
        this.repository.delete(employee.getId());
    }

    public void updateEmployee(Employee employee) {
        this.repository.update(employee);
    }
}
