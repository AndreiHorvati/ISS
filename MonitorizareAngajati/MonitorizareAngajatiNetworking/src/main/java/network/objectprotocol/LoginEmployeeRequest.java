package network.objectprotocol;

import model.Employee;

public class LoginEmployeeRequest implements Request {
    private Employee employee;

    public LoginEmployeeRequest(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return this.employee;
    }
}
