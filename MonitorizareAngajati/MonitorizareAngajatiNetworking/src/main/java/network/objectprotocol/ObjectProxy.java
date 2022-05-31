package network.objectprotocol;

import controller.IController;
import controller.IObserver;
import model.Employee;
import model.Employer;
import model.Task;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ObjectProxy implements IController {
    private String host;
    private int port;
    private IObserver client;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;
    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public ObjectProxy(String host, int port) {
        this.host = host;
        this.port = port;
        this.qresponses = new LinkedBlockingQueue();
    }

    @Override
    public void loginEmployer(Employer employer, IObserver client) throws Exception {
        this.initializeConnection();

        this.sendRequest(new LoginRequest(employer));
        Response response = this.readResponse();

        if (response instanceof OkResponse) {
            this.client = client;
        } else if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            this.closeConnection();

            throw new Exception(err.getMessage());
        }
    }

    @Override
    public void loginEmployee(Employee employee, IObserver client) throws Exception {
        this.initializeConnection();

        this.sendRequest(new LoginEmployeeRequest(employee));
        Response response = this.readResponse();

        if (response instanceof OkResponse) {
            this.client = client;
        } else if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            this.closeConnection();

            throw new Exception(err.getMessage());
        }
    }

    private void closeConnection() {
        this.finished = true;

        try {
            this.input.close();
            this.output.close();
            this.connection.close();
            this.client = null;
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    private void sendRequest(Request request) throws Exception {
        try {
            this.output.writeObject(request);
            this.output.flush();
        } catch (IOException var3) {
            throw new Exception("Error sending object " + var3);
        }
    }

    private Response readResponse() throws Exception {
        Response response = null;

        try {
            response = (Response) this.qresponses.take();
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        }

        return response;
    }

    private void initializeConnection() throws Exception {
        try {
            this.connection = new Socket(this.host, this.port);
            this.output = new ObjectOutputStream(this.connection.getOutputStream());
            this.output.flush();
            this.input = new ObjectInputStream(this.connection.getInputStream());
            this.finished = false;
            this.startReader();
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    /*
    public void logout(OfficePerson officePerson, IObserver client) throws Exception {
        sendRequest(new LogoutRequest(officePerson));
        Response response = readResponse();
        closeConnection();
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new Exception(err.getMessage());
        }
    }
     */

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(UpdateResponse update) {
        if (update instanceof EmployeeAddedResponse) {
            try {
                client.employeeAdded();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (update instanceof EmployeeDeletedResponse) {
            try {
                client.employeeDeleted();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (update instanceof TaskAddedResponse) {
            try {
                client.taskAdded();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (update instanceof TaskDeletedResponse)
            try {
                client.taskDeleted();
            } catch (Exception e) {
                e.printStackTrace();
            }

        if (update instanceof TaskUpdatedResponse)
            try {
                client.taskUpdated();
            } catch (Exception e) {
                e.printStackTrace();
            }

        if (update instanceof EmployeeUpdated)
            try {
                client.employeeUpdated();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public Employer getEmployerByUsername(String username) {
        try {
            sendRequest(new GetEmployerByUsernameRequest(username));
            Response response = readResponse();

            if (response instanceof ErrorResponse) {
                ErrorResponse err = (ErrorResponse) response;
                throw new Exception(err.getMessage());
            }

            GetEmployerByUsernameResponse resp = (GetEmployerByUsernameResponse) response;
            Employer employer = resp.getEmployer();

            return employer;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Employee getEmployeeByUsername(String username) {
        try {
            sendRequest(new GetEmployeeByUsernameRequest(username));
            Response response = readResponse();

            if (response instanceof ErrorResponse) {
                ErrorResponse err = (ErrorResponse) response;
                throw new Exception(err.getMessage());
            }

            GetEmployeeByUsernameResponse resp = (GetEmployeeByUsernameResponse) response;
            Employee employee = resp.getEmployee();

            return employee;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void addEmployee(Employee employee) throws Exception {
        sendRequest(new AddEmployeeRequest(employee));
        Response response = readResponse();

        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;

            throw new Exception(err.getMessage());
        }
    }

    @Override
    public Iterable<Employee> getEmployeesOfAnEmployer(Employer employer) {
        try {
            sendRequest(new GetEmployeesOfAnEmployerRequest(employer));
            Response response = readResponse();

            if (response instanceof ErrorResponse) {
                ErrorResponse err = (ErrorResponse) response;
                throw new Exception(err.getMessage());
            }

            GetEmployeesOfAnEmployerResponse resp = (GetEmployeesOfAnEmployerResponse) response;
            Iterable<Employee> employees = resp.getEmployees();

            return employees;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<Task> getTasksOfAnEmployee(Employee employee) {
        try {
            sendRequest(new GetTasksOfAnEmployeeRequest(employee));
            Response response = readResponse();

            if (response instanceof ErrorResponse) {
                ErrorResponse err = (ErrorResponse) response;
                throw new Exception(err.getMessage());
            }

            GetTasksOfAnEmployeeResponse resp = (GetTasksOfAnEmployeeResponse) response;
            Iterable<Task> tasks = resp.getTasks();

            return tasks;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<Task> getTasksOfAnEmployer(Employer employer) {
        try {
            sendRequest(new GetTasksOfAnEmployerRequest(employer));
            Response response = readResponse();

            if (response instanceof ErrorResponse) {
                ErrorResponse err = (ErrorResponse) response;
                throw new Exception(err.getMessage());
            }

            GetTasksOfAnEmployerResponse resp = (GetTasksOfAnEmployerResponse) response;
            Iterable<Task> tasks = resp.getTasks();

            return tasks;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void deleteEmployee(Employee employee) throws Exception {
        sendRequest(new DeleteEmployeeRequest(employee));
        Response response = readResponse();

        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;

            throw new Exception(err.getMessage());
        }
    }

    @Override
    public void deleteTask(Task task) throws Exception {
        sendRequest(new DeleteTaskRequest(task));
        Response response = readResponse();

        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;

            throw new Exception(err.getMessage());
        }
    }

    @Override
    public void addTask(Task task) throws Exception {
        sendRequest(new AddTaskRequest(task));
        Response response = readResponse();

        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;

            throw new Exception(err.getMessage());
        }
    }

    @Override
    public void updateTask(Task task) throws Exception {
        sendRequest(new UpdateTaskRequest(task));
        Response response = readResponse();

        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;

            throw new Exception(err.getMessage());
        }
    }

    @Override
    public void logoutEmployer(Employer employer, IObserver observer) throws Exception {
        sendRequest(new LogoutEmployerRequest(employer));
        Response response = readResponse();
        closeConnection();
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new Exception(err.getMessage());
        }
    }

    @Override
    public void logoutEmployee(Employee employee, IObserver observer) throws Exception {
        sendRequest(new LogoutEmployeeRequest(employee));
        Response response = readResponse();
        closeConnection();
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new Exception(err.getMessage());
        }
    }

    @Override
    public void updateEmployee(Employee employee) throws Exception {
        sendRequest(new UpdateEmployeeRequest(employee, employee.getHour()));
        Response response = readResponse();

        System.out.println("Proxy: " + employee.getHour());

        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;

            throw new Exception(err.getMessage());
        }
    }

    private class ReaderThread implements Runnable {
        private ReaderThread() {
        }

        public void run() {
            while (!ObjectProxy.this.finished) {
                try {
                    Object response = ObjectProxy.this.input.readObject();
                    System.out.println("response received " + response);
                    if (response instanceof UpdateResponse) {
                        ObjectProxy.this.handleUpdate((UpdateResponse) response);
                    } else {
                        try {
                            ObjectProxy.this.qresponses.put((Response) response);
                        } catch (InterruptedException var3) {
                            var3.printStackTrace();
                        }
                    }
                } catch (IOException var4) {
                    System.out.println("Reading error " + var4);
                } catch (ClassNotFoundException var5) {
                    System.out.println("Reading error " + var5);
                }
            }

        }
    }
}

