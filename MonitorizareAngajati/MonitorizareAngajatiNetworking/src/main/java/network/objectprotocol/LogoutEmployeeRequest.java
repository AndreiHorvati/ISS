package network.objectprotocol;

import model.Employee;

public class LogoutEmployeeRequest implements Request {
    private Employee employee;

    public LogoutEmployeeRequest(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }
}
