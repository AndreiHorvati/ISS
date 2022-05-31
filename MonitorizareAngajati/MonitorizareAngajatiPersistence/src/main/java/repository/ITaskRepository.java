package repository;

import model.Employee;
import model.Employer;
import model.Task;

public interface ITaskRepository extends IRepository<Long, Task> {
    Iterable<Task> getTasksOfAnEmployee(Employee employee);
}
