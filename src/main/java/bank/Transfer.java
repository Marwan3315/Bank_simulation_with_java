package bank;
import bank.exceptions.*;

/**
 * Klasse Transfer mit der man Überweisungen machen kann
 *Die Klasse erbt die 3 attribute von der Oberabstrakten Klasse Transaction
 * Enthält zwei weitere private Attribute sender und recipient
 */
public class Transfer extends Transaction
{
    private String sender;
    private String recipient;

    /**
     *
     * @param d Parameter der den date erhält
     * @param a Parameter der den amount erhält
     * @param desc der die Description erhält.
     *             Die Parameter werden alle mit super an den Konstrukotr der Oberklasse Transaction übergeben
     */
    public Transfer(String d, double a , String desc) throws TransactionAttributeException
    {
        super(d, a, desc);
        setamount(a);
    }

    /**
     *
     * @param d Parameter der den date als String erhält
     * @param a Parameter der den amount als String erhält
     * @param desc Parameter der die Description als String erhält.
     * @param sender Parameter der die incominginterest als double Zahl erhält
     * @param recipient Parameter der die outgoinginterest als double Zahl erhält
     */
    public Transfer(String d , double a, String desc , String sender, String recipient )throws TransactionAttributeException
    {

        super(d, a, desc );
        setamount(a);
            this.sender = sender;
        this.recipient= recipient;

    }

    /**
     * copy Konstruktor : nimmt einen Parameter vom Typ Payment und erstellt ein neues Objekt der eine Kopie vom originalen ist (ist unabhängig vom Originalen)
     @param T Parameter der ein Objekt vom Typ Transfer übergeben nimmt damit er die Attribute davon kopiert und eine neue unabhängiges Objekt Kopie erstellt
     */
    public Transfer(Transfer T)
    {
       super(T.date, T.amount, T.description);
       this.sender = T.sender;
       this.recipient = T.recipient;


    }
    @Override
public void setamount(double amount) throws TransactionAttributeException
{


    if(amount < 0 )
    {

        throw new TransactionAttributeException("Error: amount must be a positive number ");

    }
    else{
        this.amount = amount;
    }
}

public double getamount(){return super.getamount();}
    /**
     *
     * @return den gespeicherten Wert von sender als String aus
     */
    public String getsender(){return this.sender;}

    /**
     *
     * @param sender Der gewünschte Wert wird mit dem parameter als String übergeben und ändert damit den alten Wert vom attribut sender
     */
    public void setsender(String sender)
    {
        this.sender = sender;
    }
    /**
     *
     * @return den gespeicherten Wert von recipient als String aus
     */
    public String getrecipient(){return this.recipient;}

    /**
     *
     * @param recipient Der gewünschte Wert wird mit dem parameter als String übergeben und ändert damit den alten Wert vom attribut recipient
     */
    public void setrecipient(String recipient)
    {
        this.recipient = recipient;
    }

    /**
     * toString Methode aus der Object Oberklasse von Java
     *  Hier wird die Methode überschrieben mit @Override um die funktionalität an der Klasse Transaction anzupassen
     * @return gibt alle Attribute der Klasse in String Form an
     */
    @Override
    public String toString()
    {
        return (super.toString() + " Sender: " + sender + " Recipient: " + recipient );


    }

    /**
     *
     * @return den richtigen amount Wert in double
     */
    public double calculate()
    {
        return amount;
    }

    /**
     *
     * @param obj   the der übergebene Referenz um damit die attribute der anderen Objekt zu vergleichen
     * @return true wenn beide Objekte vom typ Transaction stammen und wenn alle attribute der beiden Klassen gleich sind
     */
  @Override
    public boolean equals(Object obj)
    {
        boolean Transactionattributegleich = super.equals(obj);

        if (obj == null|| this.getClass() != obj.getClass())
        {
            return false;
        }

        if(!Transactionattributegleich)
        {
            return false;

        }

       Transfer T = (Transfer)obj;

        return(this.sender.equals(T.sender) && this.recipient.equals(T.recipient));



    }

}
