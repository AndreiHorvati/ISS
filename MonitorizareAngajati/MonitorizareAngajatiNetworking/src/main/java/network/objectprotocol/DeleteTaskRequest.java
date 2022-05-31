package network.objectprotocol;

import model.Task;

public class DeleteTaskRequest implements Request {
    private Task task;

    public DeleteTaskRequest(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }
}
