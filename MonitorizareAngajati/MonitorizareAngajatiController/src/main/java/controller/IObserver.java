package controller;

public interface IObserver {
    void employeeAdded() throws Exception;

    void employeeDeleted() throws Exception;
}
