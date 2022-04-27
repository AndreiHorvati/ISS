package network.objectprotocol;

import model.Employee;
import model.Employer;

public class AddEmployeeRequest implements Request {
    private Employee employee;

    public AddEmployeeRequest(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return this.employee;
    }
}
