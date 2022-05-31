package model;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

public class Task extends Entity<Long> {
    private String name;
    private String description;
    private LocalDateTime deadline;

    private Status status;

    private Employee employee;

    public Task() {
        super();
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Task(String name, String description, LocalDateTime deadline, Employee employee) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.status = Status.UNFINISHED;
        this.employee = employee;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
