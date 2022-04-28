package network.objectprotocol;

import model.Employee;

public class GetEmployeesOfAnEmployerResponse implements Response {
    private Iterable<Employee> employees;

    public GetEmployeesOfAnEmployerResponse(Iterable<Employee> employees) {
        this.employees = employees;
    }

    public Iterable<Employee> getEmployees() {
        return this.employees;
    }
}
