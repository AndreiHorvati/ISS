package network.objectprotocol;

import model.Employer;
import model.Task;

public class UpdateTaskRequest implements Request {
    private Task task;

    public UpdateTaskRequest(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }
}
