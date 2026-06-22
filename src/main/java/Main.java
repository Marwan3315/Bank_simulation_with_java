import bank.Bank;
import bank.Payment;
import bank.Transfer;
import bank.PrivateBank;
import bank.exceptions.*;

public class Main  {
    public static void main(String []args )
    {

    }
  /*   Payment Pay = new Payment("25.10.2025", 200, "Payment for the flight ");


       //System.out.println(Pay.getdate()+ " " + Pay.getamount());
      Payment P1 = new Payment("20.10", 200, "Zahlung für den Laptop");

        Payment P2 = new Payment(P1);
        try {
            Payment P4 = new Payment("31.10.2025", -1000, "Bezahung fürs Kostüm ", 0.0, 0.1);
            System.out.println(P4.toString());
            Payment P3 = new Payment("2.11.2025", 1000, "Payment fürs Kopfhörer", 1.5, 0.0);

        }

        catch(TransactionAttributeException e ){
           System.out.println(e.getMessage());

    /*   }


        try {
            Transfer T1 = new Transfer("15.10", -480, "überweisung für die Wohnmiete", "Miro", "gibfeloosGmbH");
            Transfer T2 = new Transfer("30.10.2025", 1000, "asfnadb", "alo", "Miro");
        }
        catch(TransactionAttributeException e){
            System.out.println(e.getMessage());


        }

        System.out.println("PribvateBank test: \n");
        PrivateBank PB = new PrivateBank("MM",2, 0.0, "accounts");
        PrivateBank PB1 = new PrivateBank("Sparkasse", 0.0,0.3, "accounts");
        PrivateBank PB2 = new PrivateBank(PB1);
        System.out.println("PB2 und PB1 sind identisch: " + PB2.equals(PB1));
        /*
        try {
           List<Transaction> L1 = Arrays.asList(
                   new Transfer("20.11.2025", 300 , "Überweisung von der Wohnungsmiete", "Miro", "aloGmbH"),
                   new Payment ("31.11.2025", 400, "Zahlung vom Laptop" , 0.1 ,0.0),
                   new IncomingTransfer("15.11.2025", 50, "Überweisung fürs Hotel", "lala","Miro")


            );

           List<Transaction> L2 = Arrays.asList(
                   new Payment("21.12.2025", 200, "Amsterdam accommodation", 0.3,0.4),
                   new OutgoingTransfer("22.12.2025",100,"Transfer for the accommodation","Lala","Miro"),
                   new Payment("25.11.2025", 50, "Payment for the train travel",0.0,0.4 )


           );
            PB.createAccount("Miro",L1);
            PB2.createAccount("Lala",L2);


        }
        catch( AccountAlreadyExistsException e){
         System.out.println(e.getMessage());
    }
        catch(TransactionAlreadyExistException e1){
            System.out.println(e1.getMessage());
    }
        catch(TransactionAttributeException e2){
            System.out.println(e2.getMessage());
        }

   System.out.println(PB.toString());


System.out.println("Test von der Methode addTransaction : ");
try{
    PB2.addTransaction("Lala",new IncomingTransfer("25.12.2025", 100,"ausgeliehne Geld zurück" , "Miro", "lala"));
    }

catch(TransactionAlreadyExistException e){
    System.out.println(e.getMessage());
}
catch(AccountDoesNotExistException e2)
{
    System.out.println(e2.getMessage());
}

catch(TransactionAttributeException e3)
{
    System.out.println(e3.getMessage());
}
        System.out.println("addTransaction: mit nicht existierendem Konto");
        try
        {
            PB.addTransaction("Hans",
                    new Transfer("2.1.2025",10,"Sehr Tolle Beschreibung","Konto irgendwas","Konto irgendetwas"));
        } catch (AccountDoesNotExistException e1) {
            System.out.println(e1.getMessage());
        } catch (TransactionAlreadyExistException e2) {
            System.out.println(e2.getMessage());
        } catch (TransactionAttributeException e3) {
            System.out.println(e3.getMessage());
        }
        System.out.println("removeTransaction: mit nicht existierendem Konto");
        try
        {
            PB.removeTransaction("Hans",null);
        } catch (AccountDoesNotExistException e1) {
            System.out.println(e1.getMessage());
        } catch (TransactionDoesNotExistException e2) {
            System.out.println(e2.getMessage());
        }

        System.out.println("removeTransaction: mit nicht existierender Transaktion");
        try
        {
            PB.removeTransaction("Miro",null);
        } catch (AccountDoesNotExistException e1) {
            System.out.println(e1.getMessage());
        } catch (TransactionDoesNotExistException e2) {
            System.out.println(e2.getMessage());
        }

        System.out.println("testen von removeTransaction: eine transaction wird gelöscht aus dem Konto Miro");
        try{
            Transfer T1 = new Transfer("20.11.2025", 300 , "Überweisung von der Wohnungsmiete", "Miro", "aloGmbH");
            PB.removeTransaction("Miro", T1);
        }
        catch(TransactionAttributeException e1){
            System.out.println(e1.getMessage());
        }
        catch (AccountDoesNotExistException e2) {
            System.out.println(e2.getMessage());
        } catch (TransactionDoesNotExistException e3) {
            System.out.println(e3.getMessage());
        }
        System.out.println(PB.getTransactions("Miro"));
System.out.println("testen von der methode getTransactionsSorted : ");

        System.out.println(PB2.getTransactionsSorted("Lala", true));

        System.out.println("testen von der methode getAccountBalance : Balance von Account Miro: ");
           System.out.println(PB.getAccountBalance("Miro"));
           System.out.println("testen von getTransactionsByType: negative Transactions von Account Miro: ");
           System.out.println(PB.getTransactionsByType("Miro", false));


        PrivateBankAlt pa1 = new PrivateBankAlt("Bank1",0.05,0.1);
try {
    List<Transaction> L3 = Arrays.asList
            (
                    new Payment("1.1.2025", -10, "Payment 1", 0.05, 0.1),
                    new Transfer("2.1.2025", 6, "Transfer 1", "Marwan", "Omar"),
                    new Payment("3.1.2025", 5, "Payment 2", 0.1, 0.2)
            );

    pa1.createAccount("Marwan",L3);
   }
catch(TransactionAlreadyExistException e1){
    System.out.println(e1.getMessage());
}
catch(AccountAlreadyExistsException e2){
    System.out.println(e2.getMessage());
}
catch (TransactionAttributeException e3)
{
    System.out.println(e3.getMessage());
}



        System.out.println("Test der Methoden aus PrivateBank:\n");
try {
    pa1.addTransaction("Marwan",
            new Payment("1.1.2025", 10, "Payment 1", 0.25, 0.1));
    pa1.addTransaction("Marwan",
            new Transfer("1.2.2025", 8, "Transfer 1", "Omar", "Omar"));
}
catch (TransactionAlreadyExistException e1)
{
    System.out.println(e1.getMessage());
}
catch (AccountDoesNotExistException e2)
{
    System.out.println(e2.getMessage());
}
catch(TransactionAttributeException e3){
    System.out.println(e3.getMessage());
}
        System.out.println("addTransaction: mit 'Konto Marwan'");
        System.out.println(pa1.getTransactions("Marwan") + "\n");

        System.out.println("'Konto Marwan' getTransactionsSorted desc:");
        System.out.println(pa1.getTransactionsSorted("Marwan", false) + "\n");

        System.out.println("'Konto Marwan' getTransactionsSorted asc:");
        System.out.println(pa1.getTransactionsSorted("Marwan", true) + "\n");
        System.out.println(pa1.getAccountBalance("Marwan") + "\n");



        System.out.println("Testen von write- und readAccount Methoden: ");
        try
        {
            PB.writeAccount("Miro");
            PB.readAccount();
            System.out.println(PB.getTransactions("Miro"));
            System.out.println(PB2.getTransactions("Lala"));

        }
        catch (IOException e )
        {
            System.out.println(e.getMessage());
        }

*/

    }






