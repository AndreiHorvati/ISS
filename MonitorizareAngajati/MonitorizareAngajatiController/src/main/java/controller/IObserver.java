package controller;

public interface IObserver {
    void employeeAdded() throws Exception;

    void employeeDeleted() throws Exception;

    void taskAdded() throws Exception;

    void taskDeleted() throws Exception;

    void taskUpdated() throws Exception;

    void employeeUpdated() throws Exception;
}
