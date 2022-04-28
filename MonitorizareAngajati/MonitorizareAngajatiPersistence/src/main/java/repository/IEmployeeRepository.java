package repository;

import model.Employee;
import model.Employer;

public interface IEmployeeRepository extends IRepository<Long, Employee> {
    public Iterable<Employee> getEmployeesOfAnEmployer(Employer employer);
}
