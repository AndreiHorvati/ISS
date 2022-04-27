package network.objectprotocol;

import model.Employer;

public class LogoutRequest implements Request {
    private Employer employer;

    public LogoutRequest(Employer employer) {
        this.employer = employer;
    }

    public Employer getEmployer() {
        return this.employer;
    }
}
