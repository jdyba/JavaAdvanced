package data;

public class Director extends Worker {

    private int addition;
    private String card;
    private int limit;

    public Director (String _pesel, String _name, String _lastName, String _position, int _income, int _phone, int _addition, String _card, int _limit)
    {
        super(_pesel, _name, _lastName, _position, _income, _phone);
        this.addition = _addition;
        this.card = _card;
        this.limit = _limit;
    }

    public Director (pojo.workerspackage.Director _dirPojo)
    {
        super(_dirPojo.getPesel(), _dirPojo.getName(), _dirPojo.getLastName(), _dirPojo.getPosition(), _dirPojo.getIncome(), _dirPojo.getPhone());
        this.addition = _dirPojo.getAddition();
        this.card = _dirPojo.getCard();
        this.limit = _dirPojo.getLimit();
    }


    public int getAddition() { return this.addition; }
    public String getCard() { return this.card; }
    public int getLimit() { return this.limit; }

    public String toString()
    {
        String output = super.toString();
        output+="Dodatek sluzbowy (zl)\t\t:\t"+this.addition+"\n";
        output+="Karta sluzbowa numer\t\t:\t"+this.card+"\n";
        output+="Limit kosztow/miesiac (zl)\t:\t"+this.limit+"\n";
        return output;
    }


    public String toDatabase()
    {
        String data = "INSERT INTO directors VALUES(" + super.toDatabase();
        data += ", " + this.addition + ", '" + this.card + "', " + this.limit;
        data += ")";
        return data;
    }

}
