package utilities;

import data.Director;
import data.Tradesman;
import validation.PeselValidation;
import view.View;

import  java.util.Scanner;

public class DataReader {

    private Scanner sc;

    public DataReader()
    {
        sc = new Scanner(System.in);
    }

    public void close()
    {
        sc.close();
    }

    public int getInt()
    {
        int num = sc.nextInt();
        sc.nextLine();
        return num;
    }

    public String getString()
    {
        String temp = sc.nextLine();
        return temp;
    }

    public  String getChoice(String[] options)
    {
        while (true)
        {
            String choise = this.getString();
            for (String option : options)
            {
                if(option.compareToIgnoreCase(choise) == 0)
                    return option;
            }
            System.out.println("Bledny wybor, sprobuj ponownie: ");
        }
    }


    public Director readAndCreateDirector()
    {
        try
        {
            String position ="Dyrektor";
            System.out.print("Identyfikator PESEL \t\t\t:\t");
            String pesel = this.getString();
            if(!PeselValidation.byString(pesel))
            {
                throw new NumberFormatException();
            }
            System.out.print("Imię\t\t\t:\t");
            String name = this.getString();
            System.out.print("Nazwisko\t\t\t:\t");
            String lastName = this.getString();
            System.out.print("Wynagrodzenie (zł) \t\t\t:\t");
            int income = this.getInt();
            System.out.print("Telefon służbowy numer\t\t\t:\t");
            int phone = this.getInt();
            System.out.print("Dodatek służbowy (zł)\t\t\t:\t");
            int addition = this.getInt();
            System.out.print("Karta służbowa numer\t\t\t:\t");
            String card = this.getString();
            System.out.print("Limit kosztów/miesiąc (zł)\t\t\t:\t");
            int limit = this.getInt();
            return new Director(pesel, name, lastName, position, income, phone, addition, card, limit );
        }
        catch (NumberFormatException e)
        {
            View.printStrip();
            System.out.println("Bledne dane");
            return null;
        }

    }

    public Tradesman readAndCreateTradesman()
    {
        try
        {
            String position ="Handlowiec";
            System.out.print("Identyfikator PESEL \t\t\t:\t");
            String pesel = this.getString();
            if(!PeselValidation.byString(pesel))
            {
                throw new NumberFormatException();
            }
            System.out.print("Imię\t\t\t:\t");
            String name = this.getString();
            System.out.print("Nazwisko\t\t\t:\t");
            String lastName = this.getString();
            System.out.print("Wynagrodzenie (zł) \t\t\t:\t");
            int income = this.getInt();
            System.out.print("Telefon służbowy numer\t\t\t:\t");
            int phone = this.getInt();
            System.out.print("Prowizja (%)\t\t\t:\t");
            int provision = this.getInt();
            System.out.print("Limit prowizji/miesiąc (zł)\t\t\t:\t");
            int limit = this.getInt();
            return new Tradesman(pesel, name, lastName, position, income, phone, provision, limit);
        }
        catch (NumberFormatException e)
        {
            View.printStrip();
            System.out.println("Bledne dane");
            return null;
        }
    }

}
