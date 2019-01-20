package SoapServer;

import javax.xml.ws.Endpoint;

public class SoapServer {

    public void startServer() {
        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                try {
                    Endpoint.publish("http://localhost:9093/ws/demo", new SoapDemoImpl());
                 //   System.out.println("Done");
                } catch (Exception e) {
                    System.err.println("Unable to process client request");
                    e.printStackTrace();
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();

    }

}
