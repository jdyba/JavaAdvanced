package server;

import data.AllWorkers;
import data.Director;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public void startServer() {
        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(8415);
                   // System.out.println("Waiting for clients to connect...");
                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        clientProcessingPool.submit(new ClientTask(clientSocket));
                    }
                } catch (IOException e) {
                    System.err.println("Unable to process client request");
                    e.printStackTrace();
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();

    }

    private class ClientTask implements Runnable {
        private final Socket clientSocket;

        private ClientTask(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
          //  System.out.println("Got a client");

            // Do whatever required to process the client's request

            try {

                OutputStream outputStream = this.clientSocket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                List<Object> workerServer = new ArrayList<>(AllWorkers.myWorkers);
                workerServer.add(new Director("90090515836", "Jacek", "Michalski", "Dyrektor", 12, 12, 13, "12", 12));

                objectOutputStream.writeObject(workerServer);
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}