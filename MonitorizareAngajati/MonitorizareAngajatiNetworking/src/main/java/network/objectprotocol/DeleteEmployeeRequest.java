package network.objectprotocol;

import model.Employee;

public class DeleteEmployeeRequest implements Request {
    private Employee employee;

    public DeleteEmployeeRequest(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }
}
