package network.objectprotocol;

import model.Employer;

public class GetEmployeesOfAnEmployerRequest implements Request {
    private Employer employer;

    public GetEmployeesOfAnEmployerRequest(Employer employer) {
        this.employer = employer;
    }

    public Employer getEmployer() {
        return employer;
    }
}
