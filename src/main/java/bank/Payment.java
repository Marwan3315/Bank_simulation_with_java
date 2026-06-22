package bank;
import bank.exceptions.*;
/**
 * Klasse Payment mit der man Bzehalungen machen kann
 * erbt die Attribute von der Oberabstrakten Klasse Transaction und hat dazu zwei private attribute incominginterest,outgoinginterest
 * richtigeincominginterestangabe + richtigeoutgoinginterestangabe sind hilfattribute für prüfung der richtigkeit bei der Angabe
 */
public class Payment extends Transaction  {
    private double incominginterest;
    private double outgoinginterest;
    private double baseAmount; // Diese Variable speichert den Originalbetrag
    // private boolean richtigeincominginterestangabe;
    // private boolean richtigeoutgoinginterestangabe;

    /**
     *
     * @param d Parameter der den date erhält
     * @param a Parameter der den amount erhält
     * @param desc Parameter der die Description erhält.
     * Die Parameter werden alle mit super an den Konstrukotr der Oberklasse Transaction übergeben

     */
    public Payment(String d, double a , String desc)
    {
        super(d, a, desc);
        this.baseAmount = a;
    }

    /**
     *
     * @param d Parameter der den date als String erhält
     * @param a Parameter der den amount als String erhält
     * @param desc Parameter der die Description als String erhält.
     * @param incominginterest Parameter der die incominginterest als double Zahl erhält
     * @param outgoinginterest Parameter der die outgoinginterest als double Zahl erhält
     */
    public Payment(String d , double a, String desc , double incominginterest, double outgoinginterest) throws TransactionAttributeException
    {
        super(d, a, desc);
        this.baseAmount = a;
        this.setincominginterest(incominginterest);
        this.setoutgoinginterest(outgoinginterest);
        this.amount = calculate();
    }

    /**
     * copy Konstruktor : nimmt einen Parameter vom Typ Payment und erstellt ein neues Objekt der eine Kopie vom originalen ist (ist unabhängig vom Originalen)
     @param P Parameter der ein Objekt vom Typ Payment übergeben nimmt damit er die Attribute davon kopiert und eine neue unabhängiges Objekt Kopie erstellt
     */
    public Payment( Payment P)
    {
        super(P.date, P.amount, P.description);
        this.baseAmount = P.baseAmount;
        this.incominginterest = P.incominginterest;
        this.outgoinginterest = P.outgoinginterest;
    }

    /**
     * Keine Parameter
     * @return den gespiecherten Wert von incominginterest aus
     */
    public double getincominginterest(){return this.incominginterest;}

    /**
     *
     * @param incominginterest Parameter wird als double übergeben damit es den alten Wert von incominginterest ändert
     */
    public void setincominginterest(double incominginterest) throws TransactionAttributeException
    {

        if(incominginterest < 0 || incominginterest > 1)
        {
            throw new TransactionAttributeException ("Error: Incominginterest must be a positive number between 0-1 please try again");

        }
        else{

            this.incominginterest = incominginterest;

        }
        this.amount = calculate();
    }

    /**
     *
     * Keine Parameter
     * @return gespiecherten Wert von outgoinginterest aus
     */
    public double getoutgoinginterest(){return this.outgoinginterest;}

    /**
     *
     * @param outgoinginterest Parameter wird als double übergeben damit es den alten Wert von incominginterest ändert
     */
    public void setoutgoinginterest(double outgoinginterest) throws TransactionAttributeException
    {

        if (outgoinginterest < 0 || outgoinginterest > 1)
        {
            throw new TransactionAttributeException("Error: Outgoinginterest must be a positive number between 0-1 please try again");

        }
        else{
            this.outgoinginterest = outgoinginterest;

        }
        this.amount = calculate();
    }


    /**
     * toString Methode aus der Object Oberklasse von Java
     * Hier wird die Methode überschrieben mit @Override um die funktionalität an der Klasse Payment anzupassen
     * @return gibt alle Attribute der Klasse in String Form an
     */
    @Override
    public String toString()
    {
        return (super.toString() + " Incominginterest: " + incominginterest + " Outgoinginterest: " + outgoinginterest  );
    }

    /**
     *
     * @return Den richtigen berechneten amount nach eingeben von incoming- oder outgoinginterest
     */
    @Override // muss nicht eingesetzt werden nur für extra Sicherheit wegen compiler fehler
    public double calculate()
    {
       /* if(incominginterest < 0 || incominginterest > 1)
        {
            richtigeincominginterestangabe = false;
        }
        else{ richtigeincominginterestangabe = true; }

        if (outgoinginterest < 0 || outgoinginterest > 1)
        {
            richtigeoutgoinginterestangabe = false;
        }
        else{ richtigeoutgoinginterestangabe = true; }
        */
        double calculatedAmount = this.baseAmount;

        if(this.baseAmount > 0)
        {
            calculatedAmount = this.baseAmount - (this.baseAmount * incominginterest);
        }

        if(this.baseAmount < 0 )
        {
            calculatedAmount = this.baseAmount + (this.baseAmount * outgoinginterest);
        }

        return calculatedAmount;
    }

    /**
     * Hier wird nochmal die Methode mit Override überschriben damit die funktionalität an Transaction anzupassen
     * @param obj   the der übergebene Referenz um damit die attribute der anderen Objekt zu vergleichen
     * @return gibt true wenn beide Objekte die gleichen Attributwerte haben else false
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

        Payment P = (Payment)obj;

        return (Double.compare(this.incominginterest , P.incominginterest)== 0 && Double.compare(this.outgoinginterest , P.outgoinginterest)== 0);
    }

}
