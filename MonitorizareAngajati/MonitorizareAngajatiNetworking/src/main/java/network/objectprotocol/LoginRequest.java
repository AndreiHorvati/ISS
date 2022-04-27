package network.objectprotocol;

import model.Employer;

public class LoginRequest implements Request{
    private Employer employer;

    public LoginRequest(Employer employer) {
        this.employer = employer;
    }

    public Employer getEmployer() {
        return this.employer;
    }
}
