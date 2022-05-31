package network.objectprotocol;

import model.Employee;

public class GetTasksOfAnEmployeeRequest implements Request {
    private Employee employee;

    public GetTasksOfAnEmployeeRequest(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }
}
