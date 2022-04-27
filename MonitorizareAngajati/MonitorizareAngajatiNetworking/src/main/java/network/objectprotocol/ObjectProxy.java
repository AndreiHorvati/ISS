package network.objectprotocol;

import controller.IController;
import controller.IObserver;
import model.Employer;

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

    @Override
    public OfficePerson getOfficePersonByUsername(String username) {
        try {
            sendRequest(new GetOfficePersonByUsernameRequest(username));
            Response response = readResponse();
            if (response instanceof ErrorResponse) {
                ErrorResponse err = (ErrorResponse) response;
                throw new Exception(err.getMessage());
            }
            GetOfficePersonByUsernameResponse resp = (GetOfficePersonByUsernameResponse) response;
            OfficePerson of = resp.getOfficePerson();

            return of;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
     */

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(UpdateResponse update) {
        /*
        OfficePerson officePerson;
        if (update instanceof OfficePersonLoggedInResponse) {
            OfficePersonLoggedInResponse update1 = (OfficePersonLoggedInResponse) update;
            officePerson = update1.getOfficePerson();
            System.out.println("OfficePerson logged in " + officePerson);

            try {
                this.client.officePersonLoggedIn(officePerson);
            } catch (Exception var7) {
                var7.printStackTrace();
            }
        }

        if (update instanceof ChildEnteredResponse) {
            try {
                client.childEntered();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        */
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

    /*
    @Override
    public void entry(Child child, List<Integer> gamesMeters) throws Exception {
        sendRequest(new SendEntryRequest(child, gamesMeters));
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new Exception(err.getMessage());
        }
    }

    @Override
    public Iterable<Child> getParticipants(Game game) {
        try {
            sendRequest(new GetParticipantsRequest(game));
            Response response = readResponse();
            if (response instanceof ErrorResponse) {
                ErrorResponse err = (ErrorResponse) response;
                throw new Exception(err.getMessage());
            }
            GetParticipantsResponse resp = (GetParticipantsResponse) response;
            Iterable<Child> participants = resp.getParticipants();

            return participants;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<Game> findAllGames() throws Exception {
        sendRequest(new GetAllGamesRequest());
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new Exception(err.getMessage());
        }
        GetAllGamesResponse resp = (GetAllGamesResponse) response;
        Iterable<Game> games = resp.getGames();

        return games;
    }

    @Override
    public Game findGameByAttributes(Game game) throws Exception {
        sendRequest(new FindGameRequest(game));
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new Exception(err.getMessage());
        }
        FindGameResponse resp = (FindGameResponse) response;
        Game game1 = resp.getGame();

        return game1;
    }

    @Override
    public int numberOfGamesPerChild(Child child) {
        try {
            sendRequest(new NumberOfGamesRequest(child));
            Response response = readResponse();
            if (response instanceof ErrorResponse) {
                ErrorResponse err = (ErrorResponse) response;
                throw new Exception(err.getMessage());
            }
            NumberOfGamesResponse resp = (NumberOfGamesResponse) response;
            int number = resp.getNumber();

            return number;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
     */

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

