package repository;

import model.Employer;
import model.Task;

public interface IEmployerRepository extends IRepository<Long, Employer> {
    Employer getEmployerByUsername(String username);

    Iterable<Task> getTasksOfAnEmployer(Employer employer);
}
