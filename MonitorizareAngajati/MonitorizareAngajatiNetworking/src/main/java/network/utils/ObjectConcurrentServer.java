package network.utils;


import controller.IController;
import network.objectprotocol.ClientObjectWorker;

import java.net.Socket;

public class ObjectConcurrentServer extends AbsConcurrentServer {
    private IController server;

    public ObjectConcurrentServer(int port, IController server) {
        super(port);
        this.server = server;
        System.out.println("Object - ObjectConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientObjectWorker worker = new ClientObjectWorker(server, client);
        Thread tw = new Thread(worker);
        return tw;
    }


}
