package bank;
import bank.exceptions.TransactionAttributeException;
public class OutgoingTransfer extends Transfer{

public OutgoingTransfer(String d, double a, String desc) throws TransactionAttributeException
{
    super(d, a, desc);
}
public OutgoingTransfer(String d, double a , String desc, String sender, String recipient) throws TransactionAttributeException
{
    super(d, a, desc, sender, recipient);
}

public OutgoingTransfer(OutgoingTransfer OT)
{
    super(OT);
}

@Override
    public double calculate(){
    return this.amount * -1;
}

}
