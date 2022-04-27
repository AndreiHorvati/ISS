package network.objectprotocol;

public class GetEmployerByUsernameRequest implements Request {
    private String username;

    public GetEmployerByUsernameRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}
