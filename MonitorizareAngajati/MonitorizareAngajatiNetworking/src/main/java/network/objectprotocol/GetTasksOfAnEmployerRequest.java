package network.objectprotocol;

import model.Employee;
import model.Employer;

public class GetTasksOfAnEmployerRequest implements Request {
    private Employer employer;

    public GetTasksOfAnEmployerRequest(Employer employer) {
        this.employer = employer;
    }

    public Employer getEmployer() {
        return employer;
    }
}
