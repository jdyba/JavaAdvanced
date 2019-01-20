package data;


import view.View;

import java.io.*;
import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.*;


public class AllWorkers {



    public static List<Object> myWorkers = getWorkersFromDataBase();


    public static List<pojo.workerspackage.Director> getDirectorsPojo () {
        List<pojo.workerspackage.Director> temp = new ArrayList<pojo.workerspackage.Director>();
        for(int i = 0; i < myWorkers.size(); i ++)
        {
            Object person = myWorkers.get(i);
            if(person.getClass().getSimpleName().equals("Director"))
            temp.add(new pojo.workerspackage.Director((Director) person));
        }
        return temp;
    }

    public static List<pojo.workerspackage.Tradesman> getTradesmanPojo () {
        List<pojo.workerspackage.Tradesman> temp = new ArrayList<pojo.workerspackage.Tradesman>();
        for(int i = 0; i < myWorkers.size(); i ++)
        {
            Object person = myWorkers.get(i);
            if(person.getClass().getSimpleName().equals("Tradesman"))
                temp.add(new pojo.workerspackage.Tradesman((Tradesman) person));
        }
        return temp;
    }

    public static List<Object> getWorkersObjectFromPojo(List<pojo.workerspackage.Director> dirPojo, List<pojo.workerspackage.Tradesman> tradePojo) {

        List<Object> tempWork = new ArrayList<Object>();
        for(int i = 0; i < dirPojo.size(); i++) {
            tempWork.add( new Director(dirPojo.get(i)));
        }
        for(int i = 0; i < tradePojo.size(); i++) {
            tempWork.add( new Tradesman(tradePojo.get(i)));
        }

        return tempWork;
    }




    public static String foundWorker (String _pesel)
    {
        String profesja = "";
        for(int i = 0; i < myWorkers.size(); i++)
        {
            Worker person = (Worker)myWorkers.get(i);
            if(person.getPesel().equals(_pesel))
            {
                profesja = person.getPosition();
                break;
            }
        }
        return profesja;
    }


    public static void deleteWorker (String _pesel)
    {
        String profesja = foundWorker(_pesel);

        if(profesja.equals("Dyrektor"))
        {
            String temp = "DELETE FROM directors WHERE pesel='" + _pesel + "'";
            addToDataBase(temp);
            myWorkers = getWorkersFromDataBase();
        }
        else if(profesja.equals("Handlowiec"))
        {
            String temp = "DELETE FROM tradesmen WHERE pesel='" + _pesel + "'";
            addToDataBase(temp);
            myWorkers = getWorkersFromDataBase();
        }
        else
        {
            System.out.println("Nie ma takiej osoby");
        }
    }


    public static void addDirector(Director dir)
    {
        String profesja = foundWorker(dir.getPesel());

        if(profesja.equals(""))
        {
            addToDataBase(dir.toDatabase());
        }
        else
        {
            System.out.println("Istnieje taka osoba w bazie danych");
        }
    }

    public static void addTradesman(Tradesman trader)
    {
        String profesja = foundWorker(trader.getPesel());

        if(profesja.equals(""))
        {
            addToDataBase(trader.toDatabase());
        }
        else
        {
            System.out.println("Istnieje taka osoba w bazie danych");
        }
    }

    private static void addToDataBase(String inp)
    {
        try
        {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees", "root", "miska123");
            PreparedStatement p = myConn.prepareStatement(inp);
            p.executeUpdate();

            myWorkers = getWorkersFromDataBase();
        }
        catch (Exception exc)
        {
            View.printStrip();
            exc.printStackTrace();
        }
    }

    public static List<Object> getWorkersFromDataBase ()
    {
        List<Object> workers = new ArrayList<Object>();
        try
        {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees", "root", "miska123");
            Statement myStat = myConn.createStatement();
            ResultSet myRes = myStat.executeQuery("select * from directors");
            while(myRes.next())
            {
                String pesel = myRes.getString("pesel");
                String name = myRes.getString("name");
                String lastName = myRes.getString("lastName");
                String position = myRes.getString("position");
                int income = myRes.getInt("income");
                int phone = myRes.getInt("phone");
                int addition = myRes.getInt("addition");
                String card = myRes.getString("card");
                int limit = myRes.getInt("limitMonth");

                Director temp = new Director(pesel, name, lastName, position, income, phone, addition, card, limit);
                workers.add(temp);
            }

            myRes = myStat.executeQuery("select * from tradesmen");
            while(myRes.next())
            {
                String pesel = myRes.getString("pesel");
                String name = myRes.getString("name");
                String lastName = myRes.getString("lastName");
                String position = myRes.getString("position");
                int income = myRes.getInt("income");
                int phone = myRes.getInt("phone");
                int provision = myRes.getInt("provision");
                int limit = myRes.getInt("limitMonth");

                Tradesman temp = new Tradesman(pesel, name, lastName, position, income, phone, provision, limit);
                workers.add(temp);
            }
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
        return workers;
    }


    public static Boolean Compress(String fileName, char compresion)
    {
        String filePath = "./src/compress/" + fileName;
        Boolean output=false;
        ObjectOutputStream oos =null;
        ZipOutputStream zos=null;
        try
        {
            if (compresion=='G')
            {
                filePath+= ".gzip";
                oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(new File(filePath))));
            }
            else
            {
                filePath+= ".zip";
                zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
                zos.putNextEntry(new ZipEntry("Object"));
                oos= new ObjectOutputStream(zos);
            }
            oos.writeObject(myWorkers);
            oos.flush();
            oos.close();
            output=true;
        }
        catch (Exception ex) {}
        finally
        {
            try {
                if (oos!=null)oos.close();
                return output;
            }
            catch (IOException e) {return output;}
        }
    }


    public static Boolean Decompress(String fileName)
    {
        String filePath = "./src/compress/" + fileName;
        Boolean output=false;
        ObjectInputStream ois=null;
        ZipInputStream zis=null;
        try{
            String[] temp=filePath.split("\\.");
            char compresion=temp[temp.length-1].charAt(0);
            if (compresion=='g'||compresion=='G')
            {
                ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream(new File(filePath))));
            }
            else if (compresion=='z'||compresion=='Z')
            {
                zis = new ZipInputStream(new FileInputStream(filePath));
                zis.getNextEntry();
                ois= new ObjectInputStream(zis);
            }
            else
                throw new ArrayIndexOutOfBoundsException();
            myWorkers= (List<Object>) ois.readObject();
            System.out.println(myWorkers);
            output=true;
        }
        catch (ArrayIndexOutOfBoundsException e) {System.out.println("Brak rozszerzenia");}
        catch (Exception ex) {}
        finally
        {
            try {
                if (ois!=null)ois.close();
                if (zis!=null)zis.close();
                return output;
            }
            catch (IOException e) {return output;}
        }
    }



}



