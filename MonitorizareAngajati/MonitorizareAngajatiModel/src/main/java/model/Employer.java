package model;

public class Employer extends Employed {
    private String password;

    public Employer(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username);
        this.password = password;
    }

    public Employer() {
        super();
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
