package network.objectprotocol;

import controller.IController;
import controller.IObserver;
import model.Employee;
import model.Employer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientObjectWorker implements Runnable, IObserver {
    private IController server;
    private Socket connection;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public ClientObjectWorker(IController controller, Socket connection) {
        this.server = controller;
        this.connection = connection;

        try {
            this.output = new ObjectOutputStream(connection.getOutputStream());
            this.output.flush();
            this.input = new ObjectInputStream(connection.getInputStream());
            this.connected = true;
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    public void run() {
        while (this.connected) {
            try {
                Object request = this.input.readObject();
                Object response = this.handleRequest((Request) request);
                if (response != null) {
                    this.sendResponse((Response) response);
                }
            } catch (IOException var4) {
                var4.printStackTrace();
            } catch (ClassNotFoundException var5) {
                var5.printStackTrace();
            }

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException var3) {
                var3.printStackTrace();
            }
        }

        try {
            this.input.close();
            this.output.close();
            this.connection.close();
        } catch (IOException var6) {
            System.out.println("Error " + var6);
        }
    }

    /*
    @Override
    public void officePersonLoggedIn(OfficePerson officePerson) throws Exception {
        try {
            this.sendResponse(new OfficePersonLoggedInResponse(officePerson));
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    @Override
    public void childEntered() throws Exception {
        try {
            sendResponse(new ChildEnteredResponse());
        } catch (IOException e) {
            throw new Exception("Sending error: " + e);
        }
    }
    */

    private Response handleRequest(Request request) {
        Response response = null;
        Employer employer;

        if (request instanceof LoginRequest) {
            System.out.println("Login request ...");
            LoginRequest loginRequest = (LoginRequest) request;
            employer = loginRequest.getEmployer();

            try {
                this.server.loginEmployer(employer, this);

                return new OkResponse();
            } catch (Exception var8) {
                this.connected = false;

                return new ErrorResponse(var8.getMessage());
            }
        } else if (request instanceof GetEmployerByUsernameRequest) {
            System.out.println("get employer ...");

            GetEmployerByUsernameRequest getReq = (GetEmployerByUsernameRequest) request;
            try {
                Employer em = server.getEmployerByUsername(getReq.getUsername());

                return new GetEmployerByUsernameResponse(em);
            } catch (Exception e) {
                return new ErrorResponse(e.getMessage());
            }
        } else if (request instanceof AddEmployeeRequest) {
            System.out.println("Sending add employee request ...");

            AddEmployeeRequest req = (AddEmployeeRequest) request;
            Employee employee = req.getEmployee();

            try {
                server.addEmployee(employee);

                return new OkResponse();
            } catch (Exception e) {
                return new ErrorResponse(e.getMessage());
            }
        }
            /*
        } else if (request instanceof GetAllGamesRequest) {
            System.out.println("Get all games Request ...");
            GetAllGamesRequest getReq = (GetAllGamesRequest) request;
            try {
                Iterable<Game> games = server.findAllGames();

                return new GetAllGamesResponse(games);
            } catch (Exception e) {
                return new ErrorResponse(e.getMessage());
            }
        } else if (request instanceof GetParticipantsRequest) {
            System.out.println("Get all participants Request ...");
            GetParticipantsRequest getReq = (GetParticipantsRequest) request;
            try {
                Iterable<Child> participants = server.getParticipants(getReq.getGame());

                return new GetParticipantsResponse(participants);
            } catch (Exception e) {
                return new ErrorResponse(e.getMessage());
            }
        } else if (request instanceof NumberOfGamesRequest) {
            System.out.println("Get number of games ...");
            NumberOfGamesRequest getReq = (NumberOfGamesRequest) request;
            try {
                int number = server.numberOfGamesPerChild(getReq.getChild());

                return new NumberOfGamesResponse(number);
            } catch (Exception e) {
                return new ErrorResponse(e.getMessage());
            }
        } else if (request instanceof FindGameRequest) {
            System.out.println("Finding game ...");
            FindGameRequest getReq = (FindGameRequest) request;
            try {
                Game game = server.findGameByAttributes(getReq.getGame());

                return new FindGameResponse(game);
            } catch (Exception e) {
                return new ErrorResponse(e.getMessage());
            }
        } else if (request instanceof SendEntryRequest) {
            System.out.println("Sending entry request ...");
            SendEntryRequest senReq = (SendEntryRequest) request;
            Child child = senReq.getChild();
            List<Integer> meters = senReq.getMeters();
            try {
                server.entry(child, meters);
                return new OkResponse();
            } catch (Exception e) {
                return new ErrorResponse(e.getMessage());
            }
        } else if (request instanceof LogoutRequest) {
            System.out.println("Logout request");
            LogoutRequest logReq = (LogoutRequest) request;
            OfficePerson officePerson1 = logReq.getOfficePerson();
            try {
                server.logout(officePerson1, this);
                connected = false;
                return new OkResponse();

            } catch (Exception e) {
                return new ErrorResponse(e.getMessage());
            }
        } else if (request instanceof GetOfficePersonByUsernameRequest) {
            System.out.println("get office person ...");
            GetOfficePersonByUsernameRequest getReq = (GetOfficePersonByUsernameRequest) request;
            try {
                OfficePerson of = server.getOfficePersonByUsername(getReq.getUsername());

                return new GetOfficePersonByUsernameResponse(of);
            } catch (Exception e) {
                return new ErrorResponse(e.getMessage());
            }
        } else {
            return (Response) response;
        }
        */
        return null;
    }

    private void sendResponse(Response response) throws IOException {
        System.out.println("sending response " + response);
        this.output.writeObject(response);
        this.output.flush();
    }
}
