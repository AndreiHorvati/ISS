package network.objectprotocol;

import model.Employee;

public class GetEmployeeByUsernameResponse implements Response {
    private Employee employee;

    public GetEmployeeByUsernameResponse(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }
}
