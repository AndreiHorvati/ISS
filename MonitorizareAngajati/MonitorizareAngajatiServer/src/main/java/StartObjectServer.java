import controller.IController;
import model.Employee;
import model.Employer;
import model.Task;
import network.utils.AbstractServer;
import network.utils.ObjectConcurrentServer;
import repository.*;
import server.ControllerImplementation;
import services.EmployeeService;
import services.EmployerService;
import services.TaskService;
import utils.ORMUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Properties;

public class StartObjectServer {
    private static int defaultPort = 55555;

    public static void main(String[] args) {
        Properties serverProps = new Properties();

        try {
            serverProps.load(StartObjectServer.class.getResourceAsStream("/server.properties"));

            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find server.properties " + e);
            return;
        }
        IEmployerRepository employerRepository = new EmployerORMRepository();
        IEmployeeRepository employeeRepository = new EmployeeORMRepository();
        ITaskRepository taskRepository = new TaskORMRepository();

        EmployerService employerService = new EmployerService(employerRepository);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        TaskService taskService = new TaskService(taskRepository);

        IController controller = new ControllerImplementation(employerService, employeeService, taskService);

        int serverPort = defaultPort;
        try {
            serverPort = Integer.parseInt(serverProps.getProperty("server.port"));
        } catch (NumberFormatException nef) {
            System.err.println("Wrong  Port Number" + nef.getMessage());
            System.err.println("Using default port " + defaultPort);
        }

        System.out.println("Starting server on port: " + serverPort);
        AbstractServer server = new ObjectConcurrentServer(serverPort, controller);

        try {
            server.start();
        } catch (Exception e) {
            System.err.println("Error starting the server" + e.getMessage());
        }

        ORMUtils.closeSessionFactory();
    }
}

