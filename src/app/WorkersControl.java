package app;

import data.AllWorkers;
import rmi.RmiServer;
import server.Server;
import utilities.DataReader;
import view.View;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

import SoapServer.SoapServer;

public class WorkersControl {

    // zmienne do kontrolowania programu

    private static final int exit = 0;
    private static final int listWorkers = 1;
    private static final int addWorker = 2;
    private static final int deleteWorker = 3;
    private static final int backup = 4;
    private static final int dataNetwork = 5;

    private DataReader dr;

    public WorkersControl()
    {
        this.dr = new DataReader();
    }

    public void controlLoop()
    {
        new Server().startServer();
        new SoapServer().startServer();
        new RmiServer().startServer();
        int option;


        View.printMenu();

        while((option = dr.getInt()) != exit)
        {

            switch (option){
                case listWorkers:
                     View.printWorkers();
                    break;

                case addWorker:
                    View.addWorker();
                    break;

                case deleteWorker:
                    View.deleteWorker();
                    break;

                case backup:
                    View.copySerialize();
                    break;

                case dataNetwork:
                    View.getDataFromNetwork();
                    break;

                default:
                    System.out.println("Nie ma takiej opcji");
                    break;
            }

            View.printMenu();
        }

        dr.close();

    }



}
