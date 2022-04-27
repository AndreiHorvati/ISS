package model;

import java.time.LocalDateTime;

public class Task extends Entity<Long> {
    public enum Status { FINISHED, UNFINISHED };

    private String name;
    private String description;
    private LocalDateTime deadline;
    private Status status;

    public Task(String name, String description, LocalDateTime deadline) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.status = Status.UNFINISHED;
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
