package model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Employer employer)) {
            return false;
        }

        return getId().equals(employer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getUsername(), getPassword());
    }
}
