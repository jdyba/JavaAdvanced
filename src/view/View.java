package view;

import auth.Auth;
import data.AllWorkers;
import data.Director;
import data.Tradesman;
import rmi.Validator;
import utilities.DataReader;

import java.io.Console;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;

import SoapServer.SoapClient.SoapDemoImplService;
import SoapServer.SoapClient.SoapDemo;
import pojo.workerspackage.Workers;
import java.io.StringReader;
import java.util.concurrent.TimeUnit;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class View {

    public static void printStrip()
    {
        System.out.println("-----------------------------");
    }

    public static void printMenu()
    {
        System.out.println("MENU");
    //    System.out.println("\t0. Wyjscie z programu");
        System.out.println("\t1. Lista pracownikow");
        System.out.println("\t2. Dodaj pracownika");
        System.out.println("\t3. Usun pracownika");
        System.out.println("\t4. Kopia zapasowa");
        System.out.println("\t5. Pobierz dane z sieci");
        System.out.print("\nWybor>");
    }

    public static void printWorkers()
    {
        DataReader dr = new DataReader();
        List<Object> workers = AllWorkers.myWorkers;
        System.out.println("1.Lista pracownikow:");

        for (int counter = 0; counter < workers.size(); counter++ )
        {
            Object person = workers.get(counter);
            System.out.println(person);
            System.out.println("\n\t\t\t\t\t\t\t[Pozycja:"+ (counter + 1)  +"/"+workers.size()+"]");
            System.out.println("[Enter]-nastepny");
            System.out.println("[q]-powrot");

            String[] options ={"q",""};
            if (dr.getChoice(options).compareToIgnoreCase("q") == 0)
                break;
        }
    }

    public static void addWorker()
    {
        DataReader dr = new DataReader();
        System.out.println("2.Dodaj pracownika:\n");
        System.out.print("[D]yrektor/[H]andlowiec\t:\t");
        String[] options={"D","H"};
        String choice = dr.getChoice(options);
        printStrip();

        if(choice.compareToIgnoreCase("D") == 0 )
        {
            Director dir = dr.readAndCreateDirector();
            if(dir != null)
            {
                printStrip();
                System.out.println("[Enter]-zapisz");
                System.out.println("[q]-odrzuc");
                String[] options2={"","q"};
                if(dr.getChoice(options2).compareToIgnoreCase("") == 0)
                {
                    AllWorkers.addDirector(dir);
                    printStrip();
                }
            }
        }
        if(choice.compareToIgnoreCase("H") == 0 )
        {
            Tradesman trader = dr.readAndCreateTradesman();
            if(trader != null)
            {
                printStrip();
                System.out.println("[Enter]-zapisz");
                System.out.println("[q]-odrzuc");
                String[] options2={"","q"};
                if(dr.getChoice(options2).compareToIgnoreCase("") == 0)
                {
                    AllWorkers.addTradesman(trader);
                    printStrip();
                }
            }
        }
    }

    public static void deleteWorker()
    {
        DataReader dr = new DataReader();
        System.out.println("3.Usun pracownika:\n");
        System.out.print("Podaj pesel\t: ");
        String pesel = dr.getString();
        printStrip();
        AllWorkers.deleteWorker(pesel);
    }


    public static void copySerialize()
    {
        DataReader dr = new DataReader();
        System.out.println("4.Kopia zapasowa:\n");
        System.out.print("[Z]achowaj/[O]dtworz\t:\t");
        String[] options={"O","Z"};
        String choice = dr.getChoice(options);
        printStrip();
        if (choice.compareToIgnoreCase("Z")==0)
        {
            System.out.print("Kompresja [G]gzip/[Z]zip\t:\t");
            String[] options2={"G","Z"};
            String compresion=dr.getChoice(options2);
            System.out.print("Nazwa pliku\t:\t");
            String filePath = dr.getString();
            printStrip();
            System.out.println("[Enter]-zapisz");
            System.out.println("[q]-odrzuc");
            String[] options3={"","q"};
            if (dr.getChoice(options3).compareToIgnoreCase("")==0)
            {
                if (!AllWorkers.Compress(filePath, compresion.charAt(0)))
                    System.out.println("Blad pliku");
            }
        }
        else
        {
            System.out.print("Nazwa pliku\t:\t");
            String filePath = dr.getString();
            printStrip();
            System.out.println("[Enter]-odzyskaj");
            System.out.println("[q]-odrzuc");
            String[] options3={"","q"};
            if (dr.getChoice(options3).compareToIgnoreCase("")==0)
            {
                if (!AllWorkers.Decompress(filePath))
                    System.out.println("Blad pliku");
            }
        }
    }


    public static void getDataFromNetwork()
    {
        System.out.println("5.Pobierz dane z sieci:\n");
        DataReader dr = new DataReader();
        System.out.print("Podaj uzytkownika \t\t\t:\t\t");
        String user = dr.getString();
        String userName = "wipsad\\" + user;         /// domena wipsad dodana, nie trzeba wpisywac w konsole
        System.out.print("Podaj haslo \t\t\t\t:\t\t");
        String password;
        Console console = System.console();
        if(console == null) {
            password = dr.getString();
        } else {
            char passwordArray[] = console.readPassword();
            String temp = new String(passwordArray);
            password = temp;
        }
        printStrip();
        System.out.print("Protokol [T]CP/IP czy [S]OAP?\t:\t");
        String[] options={"T","S"};
        String choice = dr.getChoice(options);
        List<Object> temp = new ArrayList<Object>();
        if (choice.compareToIgnoreCase("T")==0)
        {
            temp = mySocket(userName, password);

        } else {
            temp = mySoap(userName, password);
        }

        if (!temp.isEmpty()) {

            System.out.println("Czy zapisać pobrane dane? [T]/[N]");
            String[] options2={"T","N"};
            if(dr.getChoice(options2).compareToIgnoreCase("T") == 0)
            {
                printStrip();
                System.out.print("Zapisywanie... ");
                AllWorkers.myWorkers = temp;
                System.out.println("Sukces");
            }
        }

        System.out.println("[Enter]- powrót do ekranu głównego");
        String[] options3={""};

        while(true)
        {
            if (dr.getChoice(options3).compareToIgnoreCase("")==0)
            {
                break;
            }
        }

    }


    public static List<Object> mySoap(String _userName, String _password)
    {
        String ip = "localhost";
        String port = "9093/ws/demo";
        DataReader dr = new DataReader();
        printPort(ip, port);

        List<Object> temp = new ArrayList<Object>();
        String key = loginUser(_userName, _password);

        if(!key.equals("false")) {
            System.out.println("Sukces");

            try {
                System.out.print("Ustanawianie polaczenia... ");
                SoapDemoImplService soapDemoImplService = new SoapDemoImplService();
                SoapDemo soapDemo = soapDemoImplService.getSoapDemoImplPort();

                System.out.println("Sukces");
                System.out.print("Pobieranie... ");
                String result = soapDemo.getDocument();
                StringReader reader = new StringReader(result);

                JAXBContext jaxbContext = JAXBContext.newInstance(Workers.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                Workers tests = (Workers) jaxbUnmarshaller.unmarshal(reader);
                temp = AllWorkers.getWorkersObjectFromPojo(tests.getDirector(), tests.getTradesman());
                System.out.println("Sukces");
                printStrip();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Blad autentykacji");
        }
        return temp;
    }


    public static List<Object> mySocket(String _userName, String _password)
    {
        String ip = "localhost";
        int port = 8415;
        DataReader dr = new DataReader();
        printPort(ip, Integer.toString(port));

        List<Object> temp = new ArrayList<Object>();

        String key = loginUser(_userName, _password);
        if(!key.equals("false")) {
            System.out.println("Sukces");
            try {
                System.out.print("Ustanawianie polaczenia... ");
                Socket s = new Socket(ip, port);
                if(s.isConnected())
                {
                    System.out.println("Sukces");
                    System.out.print("Pobieranie... ");
                    InputStream inputStream = s.getInputStream();
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                    temp = (List<Object>) objectInputStream.readObject();
                    System.out.println("Sukces");
                    printStrip();

                    s.close();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        } else {
            System.out.println("Blad autentykacji");
            printStrip();
        }
        return temp;
    }


    public static void printPort(String _ip, String _port) {
        System.out.println("Adres\t\t\t\t:\t"+_ip);
        System.out.println("Port\t\t\t\t:\t"+_port);
        printStrip();
    }

    public static String loginUser(String _userName, String _password) {
        System.out.print("Autentykacja uzytkownika... ");
        String temp = "";
        try {
       //     Validator _Validator = (Validator) Naming.lookup("rmi://localhost:5099/valid");  // dla uzytkownikow lokalnie
            Auth _Auth = (Auth) Naming.lookup("rmi://localhost:5099/auth");
            temp = _Auth.Authenticate(_userName, _password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }
}




