package model;

public class Employee extends Employed {
    private int hour;
    private Employer employer;

    public Employer getEmployer() {
        return this.employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Employee(String firstName, String lastName, String username, Employer employer) {
        super(firstName, lastName, username);

        this.employer = employer;
        this.hour = -1;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employer=" + employer +
                '}';
    }

    public Employee() {
        super();
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
