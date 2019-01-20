package data;

public class Tradesman extends Worker {

    private int provision;
    private int limit;

    public Tradesman(String _pesel, String _name, String _lastName, String _position, int _income, int _phone, int _provision, int _limit)
    {
        super(_pesel, _name, _lastName, _position, _income, _phone);
        this.provision = _provision;
        this.limit = _limit;
    }

    public Tradesman(pojo.workerspackage.Tradesman _trader)
    {
        super(_trader.getPesel(), _trader.getName(), _trader.getLastName(), _trader.getPosition(), _trader.getIncome(), _trader.getPhone());
        this.provision = _trader.getProvision();
        this.limit = _trader.getLimit();
    }


    public int getProvision() { return this.provision; }
    public int getLimit() { return this.limit; }

    public String toString()
    {
        String output=super.toString();
        output+="Prowizja (%)\t\t\t\t:\t"+this.provision+"\n";
        output+="Limit prowizji/miesiac (zl)\t:\t"+this.limit+"\n";
        return output;
    }


    public String toDatabase()
    {
        String data = "INSERT INTO tradesmen VALUES(" + super.toDatabase();
        data += ", " + this.provision + ", " + this.limit;
        data += ")";
        return data;
    }

}
