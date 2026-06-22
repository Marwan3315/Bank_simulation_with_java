package bank;
import bank.exceptions.*;
/**
 *  abstract Class Transaction der als Oberklasse für die Klassen Payment und Transfer
 *  Die Klasse enthält 3 Attribute die als Sichtbarkeit protected haben.
 *  Die Attribute lauten: date als string, amount als double und description als string
 *  Die Klasse enthält ein Konstruktor für die initialisierung der 3 attribute
 *
 */
public abstract class Transaction implements CalculateBill{
protected String date;
protected double amount;
protected String description;

    /**
     * Konstruktor der alle Attribute der Klasse initialisiert
     * @param date der Wert wird durch diesen Parameter übergeben um den attribut date mit diesem Wert zu initialisiern
     * @param amount der Wert wird durch diesen Parameter übergeben um den attribut amount mit diesem Wert zu initialisiern
     * @param description der Wert wird durch diesen Parameter übergeben um den attribut description mit diesem Wert zu initialisiern
     */
    Transaction (String date , double amount, String description )
{
    this.date = date;
    this.amount = amount;
    this.description = description;


}

    /**
     * Hier sind die getter und setter für die Attribute damit man die von außerhalb des Packets bank zugreifen kann
     */

    /**
     *  Keine Parameter
     * @return : das attribut date der den datum der Operation zeigt
     */
   public String getdate(){return this.date;}


    /**
     *
     * @param date : wird der gewünschte Wert übergeben um im attribut date neu zu speichern und ändern
     */
    public void setdate(String date)
    {
      this.date = date;
    }

    /**
     * Keine Parameter
     * @return : den gespeicherten Wert von amount
     */
    public double getamount(){return this.amount;}

    /**
     *
     * @param amount : wird der gewünschte Wert als double übergeben und ersetzt den alten Wert vom attribut amount
     */
    public void setamount(double amount) throws TransactionAttributeException
    {
        this.amount = amount;

    }

    /**
     *Keine Parameter
     * @return den gespeicherten Wert von description in String
     */
    public String getdescription(){return this.description;}

    /**
     *
     * @param description : Der gewünschte Wert wird mit dem parameter als String übergeben und ändert damit den alten Wert vom attribut description
     */
    public void setdescription(String description)
    {
        this.description = description;
    }

    /**
     * toString Methode aus der Object Oberklasse von Java
     * Hier wird die Methode überschrieben mit @Override um die funktionalität an der Klsse Transaction anzupassen
     * @return gibt alle Attribute der Klasse in String Form an
     */
    @Override
    public String toString()
    {
        return ("Date: " + date + " Amount: "+ calculate() + " Description: "+ description);


    }

    /**
     * Hier wird nochmal die Methode mit Override überschriben damit die funktionalität an Transaction anzupassen
     * @param obj   the der übergebene Referenz um damit die attribute der anderen Objekt zu vergleichen
     * @return gibt true wenn beide Objekte die gleichen Attributwerte haben else false
     */
    @Override
    public boolean equals (Object obj )
    {
        if (this==obj)
        {
            return true;
        }
        if (obj == null|| this.getClass() != obj.getClass())
        {
           return false;
        }

       Transaction T = (Transaction)obj; // typecast damit der übergebene objekt zu ein typ von Transaction wird
        return(Double.compare(this.amount, T.amount) == 0 && // Korrekt für double
                this.date.equals(T.date) &&
                this.description.equals(T.description));

    }



    public double calculate(){return this.amount;}
}
