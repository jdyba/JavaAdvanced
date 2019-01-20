package data;


import java.io.Serializable;

public abstract class Worker implements Serializable {

    protected String pesel;
    protected String name;
    protected String lastName;
    protected String position;
    protected int income;
    protected int phone;

    Worker(String _pesel, String _name, String _lastName, String _position, int _income, int _phone)
    {
        this.pesel = _pesel;
        this.name = _name;
        this.lastName =_lastName;
        this.position =_position;
        this.income = _income;
        this.phone = _phone;
    }

    public String getPesel() {return this.pesel;}
    public String getName() {return this.name;}
    public String getLastName() {return this.lastName;}
    public String getPosition() { return  this.position; }
    public int getIncome() { return this.income; }
    public int getPhone() { return this.phone; }


    public String toString() {
        String output="";
        output+="Identyfikator Pesel\t\t\t:\t"+this.pesel+"\n";
        output+="Imie\t\t\t\t\t\t:\t"+this.name+"\n";
        output+="Nazwisko\t\t\t\t\t:\t"+this.lastName+"\n";
        output+="Stanowisko\t\t\t\t\t:\t"+this.position+"\n";
        output+="Wynagrodzenie (zl)\t\t\t:\t"+this.income+"\n";
        output+="Telefon sluzbowy numer\t\t:\t";
        if (phone!=-1)
            output+= this.phone+"\n";
        else
            output+="-brak-\n";
        return output;
    }

    public String toDatabase ()
    {
        String output ="'" + this.pesel + "', '" + this.name +  "', '" + this.lastName + "', '" + this.position +
                "', " + this.income + ", " + this.phone;
        return output;
    }


}
