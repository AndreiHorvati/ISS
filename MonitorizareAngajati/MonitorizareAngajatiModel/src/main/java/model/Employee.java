package model;

public class Employee extends Employed {
    private int hour;
    private Employer employer;

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Employee(String firstName, String lastName, String username, Employer employer) {
        super(firstName, lastName, username);

        this.employer = employer;
        this.hour = -1;
    }

    public Employee() {
        super();
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
