package network.objectprotocol;

import model.Task;

public class AddTaskRequest implements Request{
    private Task task;

    public AddTaskRequest(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return this.task;
    }
}
