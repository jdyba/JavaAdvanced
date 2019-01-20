package rmi;

import auth.AuthServant;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiServer {

    public void startServer() {
        Runnable serverTask = new Runnable() {
            @Override
            public void run() {

                try {
                    Registry registry = LocateRegistry.createRegistry(5099);
                 //   registry.rebind("valid", new ValidatorServant());  // dla uzytkownikow lokalnie trzymanych
                    registry.rebind("auth", new AuthServant());   // dla widsap
                 //   System.out.println("Server Open");

                } catch (RemoteException e) {

                }

            }
        };

        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }

}
