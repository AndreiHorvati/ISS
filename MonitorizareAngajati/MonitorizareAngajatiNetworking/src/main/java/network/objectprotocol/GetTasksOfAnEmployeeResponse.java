package network.objectprotocol;

import model.Task;

public class GetTasksOfAnEmployeeResponse implements Response {
    private Iterable<Task> tasks;

    public GetTasksOfAnEmployeeResponse(Iterable<Task> tasks) {
        this.tasks = tasks;
    }

    public Iterable<Task> getTasks() {
        return tasks;
    }
}
