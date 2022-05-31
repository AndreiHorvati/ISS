package network.objectprotocol;

import model.Employee;

public class UpdateEmployeeRequest implements Request {
    private Employee employee;
    private int hour;

    public UpdateEmployeeRequest(Employee employee, int hour) {
        this.employee = employee;
        this.hour = hour;
    }

    public Employee getEmployee() {
        return employee;
    }

    public int getHour() {
        return hour;
    }
}
