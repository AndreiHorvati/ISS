package services;

import model.Employee;
import model.Employer;
import model.Task;
import repository.IEmployerRepository;
import repository.ITaskRepository;

public class TaskService {
    private ITaskRepository repository;

    public TaskService(ITaskRepository repository) {
        this.repository = repository;
    }

    public void save(Task task) {
        this.repository.save(task);
    }

    public Iterable<Task> getTasksOfAnEmployee(Employee employee) {
        return this.repository.getTasksOfAnEmployee(employee);
    }

    public void deleteTask(Task task) {
        this.repository.delete(task.getId());
    }

    public void updateTask(Task task) {
        this.repository.update(task);
    }
}
