package repository;

import model.Employee;
import model.Employer;

public interface IEmployeeRepository extends IRepository<Long, Employee> {
    Iterable<Employee> getEmployeesOfAnEmployer(Employer employer);

    Employee getEmployeeByUsername(String username);
}
