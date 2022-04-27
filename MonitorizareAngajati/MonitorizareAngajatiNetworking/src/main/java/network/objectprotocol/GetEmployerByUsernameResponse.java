package network.objectprotocol;

import model.Employer;

public class GetEmployerByUsernameResponse implements Response {
    private Employer employer;

    public GetEmployerByUsernameResponse(Employer employer) {
        this.employer = employer;
    }

    public Employer getEmployer() {
        return this.employer;
    }
}
