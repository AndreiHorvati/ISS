package network.objectprotocol;

public class GetEmployeeByUsernameRequest implements Request {
    private String username;

    public GetEmployeeByUsernameRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}
