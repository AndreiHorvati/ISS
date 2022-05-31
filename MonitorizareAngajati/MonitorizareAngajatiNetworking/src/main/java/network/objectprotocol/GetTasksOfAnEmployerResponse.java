package network.objectprotocol;

import model.Task;

public class GetTasksOfAnEmployerResponse implements Response {
    private Iterable<Task> tasks;

    public GetTasksOfAnEmployerResponse(Iterable<Task> tasks) {
        this.tasks = tasks;
    }

    public Iterable<Task> getTasks() {
        return tasks;
    }
}
