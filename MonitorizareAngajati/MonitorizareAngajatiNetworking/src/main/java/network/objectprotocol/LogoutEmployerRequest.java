package network.objectprotocol;

import model.Employer;

public class LogoutEmployerRequest implements Request {
    private Employer employer;

    public LogoutEmployerRequest(Employer employer) {
        this.employer = employer;
    }

    public Employer getEmployer() {
        return employer;
    }
}
