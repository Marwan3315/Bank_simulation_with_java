package bank;
import bank.exceptions.TransactionAttributeException;
public class IncomingTransfer extends Transfer{

    public IncomingTransfer(String d, double a, String desc) throws TransactionAttributeException{
        super(d, a, desc);
    }
    public IncomingTransfer(String d , double a, String desc , String sender, String recipient ) throws TransactionAttributeException
    {
        super(d, a, desc, sender, recipient);
    }
    public  IncomingTransfer(IncomingTransfer IT)
    {
        super(IT);
    }



}
