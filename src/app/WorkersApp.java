package app;

import rmi.RmiServer;
import rmi.Validator;

import java.rmi.Naming;
import java.util.concurrent.TimeUnit;

public class WorkersApp {

    public static final String appName = "Pracownicy v.01";

    public static void main  (String[] args) throws Exception
    {
        WorkersControl myApp = new WorkersControl();
        myApp.controlLoop();
    }
}
