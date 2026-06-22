package bank;
import java.io.*;
import java.util.*;
import java.util.Map;
import bank.exceptions.*;
import com.google.gson.*;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

/**
 * Repräsentiert eine private Bank, die Konten und Transaktionen verwaltet.
 * Implementiert das {@link Bank}-Interface.
 */
public class PrivateBank implements Bank
{
    /**
     * Der Name der Bank.
     */
    protected String name;
    /**
     * Der Zinssatz (zwischen 0 und 1) für eingehende Zahlungen (Einzahlungen).
     */
    protected double incominginterest;
    /**
     * Der Zinssatz (zwischen 0 und 1) für ausgehende Zahlungen (Auszahlungen).
     */
    protected double outgoinginterest;
    /**
     * Eine Zuordnung von Kontonamen (String) zu einer Liste von Transaktionen (List<Transaction>),
     * die zu diesem Konto gehören.
     */
    protected  Map<String, List<Transaction>> accountsToTransactions = new HashMap<>();
    /**
     * Speichert den Namen des Speicherorts von den Konten
     */
   private String directoryName;

    /**
     * Erstellt eine neue Instanz einer PrivateBank.
     *
     * @param name Der Name der Bank.
     * @param incominginterest Der Zinssatz für Einzahlungen (muss zwischen 0 und 1 liegen).
     * @param outgoinginterest Der Zinssatz für Auszahlungen (muss zwischen 0 und 1 liegen).
     */
    public PrivateBank(String name , double incominginterest, double outgoinginterest, String directory) throws IOException
    {
        this.name = name;
        setincominginterest(incominginterest);
        setoutgoinginterest(outgoinginterest);
        this.directoryName = directory;

        try {
            readAccount();
        }catch(IOException e)
        {
            System.out.println(e.getMessage());

        }
    }

    /**
     * Copy-Konstruktor. Erstellt eine tiefe Kopie einer bestehenden PrivateBank-Instanz.
     *
     * @param B Die PrivateBank-Instanz, die kopiert werden soll.
     */
    public PrivateBank(PrivateBank B) throws IOException
    {
        this.name = B.name;
        this.incominginterest = B.incominginterest;
        this.outgoinginterest = B.outgoinginterest;
        this.directoryName = B.directoryName;
        this.accountsToTransactions = new HashMap<>(B.accountsToTransactions);
    }

    /**
     * Ruft den Namen der Bank ab.
     *
     * @return Der Name der Bank.
     */
    public String getname(){return this.name;}

    /**
     * Legt den Namen der Bank fest.
     *
     * @param name Der neue Name der Bank.
     */
    public void setname(String name){this.name = name;}

    /**
     * Ruft den Zinssatz für Einzahlungen ab.
     *
     * @return Der Zinssatz (zwischen 0 und 1).
     */
    public double getincominginterest(){return this.incominginterest;}

    /**
     * Legt den Zinssatz für Einzahlungen fest.
     * Der Wert wird nur gesetzt, wenn er im gültigen Bereich [0, 1] liegt.
     *
     * @param incominginterest Der neue Zinssatz (sollte zwischen 0 und 1 liegen).
     */
    public void setincominginterest(double incominginterest)
    {
        if (incominginterest >= 0 && incominginterest <= 1)
        {
            this.incominginterest = incominginterest;
        }
        else
        {

            System.out.println("Error : Incominginterest value must be a positive number between 0 and 1 ");
        }
    }

    /**
     * Ruft den Zinssatz für Auszahlungen ab.
     *
     * @return Der Zinssatz (zwischen 0 und 1).
     */
    public double getoutgoinginterest(){return this.outgoinginterest;}

    /**
     * Legt den Zinssatz für Auszahlungen fest.
     * Der Wert wird nur gesetzt, wenn er im gültigen Bereich [0, 1] liegt.
     *
     * @param outgoinginterest Der neue Zinssatz (sollte zwischen 0 und 1 liegen).
     */
    public void setoutgoinginterest(double outgoinginterest)
    {
        if (outgoinginterest >= 0 && outgoinginterest <= 1)
        {
            this.outgoinginterest = outgoinginterest;
        }

        else
        {
            System.out.println("Error: outgoinginterest value must be a positive number between 0 and 1 ");

        }

    }

    public String getdirectoryName(){return this.directoryName; }

    public void setdirectoryName(String directoryName){
        this.directoryName = directoryName;
    }

    /**
     * Gibt eine String-Repräsentation der Bank zurück, einschließlich Name, Zinssätzen
     * und einer Liste aller Konten mit ihren Transaktionen.
     *
     * @return Ein formatierter String mit den Details der Bank.
     */
    @Override
    public String toString()
    {
        return("name: " + this.name + "\nincominginterest: "+ this.incominginterest + "\noutgoinginterest: " + this.outgoinginterest +
                /*"\n" + this.accountsToTransactions*/ maptoString()
        );
    }

    /**
     * Hilfsmethode zur Konvertierung der Map {@code accountsToTransactions} in einen lesbaren String.
     *
     * @return Ein String, der alle Konten und die zugehörigen Transaktionen auflistet.
     */
    public String maptoString() {
        String ausgabe =" ";
        for (String key : accountsToTransactions.keySet()) {
            ausgabe  =  ausgabe + ("\naccount : " + key + " Transactions : " + accountsToTransactions.get(key) + "\n");
        }
        return ausgabe;
    }

    /**
     * Vergleicht diese PrivateBank mit einem anderen Objekt auf Gleichheit.
     * Zwei Banken gelten als gleich, wenn Name, Zinssätze und die Konten-Transaktions-Map identisch sind.
     *
     * @param obj Das Objekt, mit dem verglichen werden soll.
     * @return {@code true}, wenn die Objekte gleich sind, andernfalls {@code false}.
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if(this.getClass()!= obj.getClass())
        {
            return false;

        }
        PrivateBank B = (PrivateBank)obj;

        return(this.name.equals(B.name) &&
                Double.compare(this.incominginterest, B.incominginterest) == 0 &&
                Double.compare(this.outgoinginterest, B.outgoinginterest)== 0 &&
                this.accountsToTransactions.equals(B.accountsToTransactions) );
    }


  public  void createAccount(String account) throws AccountAlreadyExistsException {

         if (this.accountsToTransactions.containsKey(account)) {
             throw new AccountAlreadyExistsException("Error: The account you are trying to create already exists");

         } else {

             this.accountsToTransactions.put(account, new ArrayList<>());
         }
     }
   public void createAccount(String account, List<Transaction> transactions) throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException, IOException
        {


            createAccount(account);


            List<Transaction> list = accountsToTransactions.get(account);
            for (Transaction t : transactions) {

                // Duplicate?
                if (list.contains(t)) {
                    throw new TransactionAlreadyExistException("Error: The transaction already exists");
                }


                if (t instanceof Payment) {
                    Payment p = (Payment) t;

                    // Nur berschreiben, wenn das Payment noch keine eigenen Zinsen hat
                    if (p.getincominginterest() == 0.0) {
                        p.setincominginterest(this.getincominginterest());
                    }
                    if (p.getoutgoinginterest() == 0.0) {
                        p.setoutgoinginterest(this.getoutgoinginterest());
                    }
                }

                // Transfer handling
                if (t instanceof Transfer) {
                    Transfer tr = (Transfer) t;
                    if (tr.getamount() < 0) {
                        throw new TransactionAttributeException("Error: amount must be positive");
                    }
                }

                // Now add
                list.add(t);
            }
            try {
                writeAccount(account);
            }
            catch(IOException e )
            {
                System.out.println(e.getMessage());
            }

        }


      public void addTransaction(String account, Transaction transaction)
            throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, IOException {
           if(!accountsToTransactions.containsKey(account))
           {
               throw new AccountDoesNotExistException("Error: The account does not exist");
           }


           else if(accountsToTransactions.get(account).contains(transaction))
           {
               throw new TransactionAlreadyExistException("Error: The Transaction already exists");

           }
           /*else{
               if (transaction instanceof Payment)
               {
                   ((Payment) transaction).setincominginterest(this.getincominginterest());
                   ((Payment) transaction).setoutgoinginterest(this.getoutgoinginterest());
               }
               accountsToTransactions.get(account).add(transaction);
           }*/else {
               if (transaction instanceof Payment) {
                   Payment p = (Payment) transaction;

                   // Nur überschreiben, wenn noch keine eigenen Zinsen gesetzt sind
                   if (p.getincominginterest() == 0.0) {
                       p.setincominginterest(this.getincominginterest());
                   }
                   if (p.getoutgoinginterest() == 0.0) {
                       p.setoutgoinginterest(this.getoutgoinginterest());
                   }
               }
               accountsToTransactions.get(account).add(transaction);
           }

          try {
              writeAccount(account);
          }
          catch(IOException e )
          {
              System.out.println(e.getMessage());
          }

        }


       public void removeTransaction(String account, Transaction transaction)
            throws AccountDoesNotExistException, TransactionDoesNotExistException, IOException{
             if (!accountsToTransactions.containsKey(account)){
                 throw new AccountDoesNotExistException("Error: The account does not exist");
             }

             else if (!accountsToTransactions.get(account).contains(transaction)){
                 throw new TransactionDoesNotExistException("Error: Transaction does not exist");
             }
             accountsToTransactions.get(account).remove(transaction);
           try {
               writeAccount(account);
           }
           catch(IOException e )
           {
               System.out.println(e.getMessage());
           }

        }

      public boolean containsTransaction(String account, Transaction transaction) {
          return (this.accountsToTransactions.get(account).contains(transaction));
      }
      public double getAccountBalance(String account) throws AccountDoesNotExistException {

          double balance = 0;
          for (int i = 0; i < accountsToTransactions.get(account).size(); i++)
          {
              balance += accountsToTransactions.get(account).get(i).calculate();
          }
          return balance;

      }

     public List<Transaction> getTransactions(String account){
        return accountsToTransactions.get(account);
     }

      public List<Transaction> getTransactionsSorted(String account, boolean asc){
    if (accountsToTransactions.get(account) == null)
    {
        return null;
    }
    List<Transaction> sortedList = new ArrayList<Transaction>(accountsToTransactions.get(account));
    Comparator<Transaction> byamount =  Comparator.comparingDouble(Transaction::calculate);
         if(asc)
         {
            sortedList.sort(byamount);
         }
         else
         {
             sortedList.sort(byamount.reversed());
         }
         return sortedList;
      }

      public List<Transaction> getTransactionsByType(String account, boolean positive){
             List<Transaction> transactions = new ArrayList<Transaction>(accountsToTransactions.get(account));
             List<Transaction> resultList = new ArrayList<>();
             for(Transaction trans : transactions){
                 if(positive && trans.calculate() >=0){
                     resultList.add(trans);
                 }
                 if(!positive && trans.calculate() < 0 ){
                     resultList.add(trans);
                 }
             }
        return resultList;




      }

     private void writeAccount(String account) throws IOException
     {
         File dir = new File(this.directoryName);
         Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Transaction.class, new TransactionSerializer())
                 .setPrettyPrinting()
                 .create();
         String filename = this.directoryName + "/Konto " + account +".json";
          try
             {
                 BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
                 List<Transaction> transactions = accountsToTransactions.get(account);
                 String jsonString = gson.toJson(transactions);
                   writer.write(jsonString);
                   writer.close();
             }

            catch(IOException e )
            {
               System.out.println(e.getMessage());
            }


     }


     public void readAccount() throws IOException
     {
         File dir = new File(this.directoryName);

         // Prüfen, ob das Verzeichnis existiert
         if (!dir.exists() || !dir.isDirectory()) {
             return; // Nichts zu tun, wenn Ordner nicht da ist
         }

         // Alle Dateien im Ordner auflisten
         File[] files = dir.listFiles();

         if (files != null) {
             Gson gson = new GsonBuilder()
                     .registerTypeHierarchyAdapter(Transaction.class, new TransactionSerializer())
                     .create();

             for (File file : files) {
                 String fileName = file.getName();

                 // Nur Dateien betrachten, die dem Schema entsprechen: "Konto [Name].json"
                 if (fileName.startsWith("Konto ") && fileName.endsWith(".json")) {

                     // Den Kontonamen aus dem Dateinamen extrahieren
                     // "Konto " ist 6 Zeichen lang, ".json" ist 5 Zeichen lang
                     String accountName = fileName.substring(6, fileName.length() - 5);


                     try  {
                         // Datei einlesen
                         BufferedReader reader = new BufferedReader(new FileReader(file));
                         // WICHTIG: Gson sagen, dass es eine List<Transaction> ist
                         // Dafür brauchen wir TypeToken (Reflect)
                         Type listType = (new TypeToken<List<Transaction>>(){}).getType();

                         List<Transaction> transactions = gson.fromJson(reader, listType);

                         // Map aktualisieren
                         accountsToTransactions.put(accountName, transactions);

                     } catch (IOException e)
                     {
                         System.out.println(e.getMessage());

                     }
                 }
             }
         }


     }
    public void deleteAccount(String account) throws AccountDoesNotExistException, IOException
    {

        File dir = new File(this.directoryName);

        if(!dir.exists()|| !dir.isDirectory()){
            return;
        }
        File[] files = dir.listFiles();

       if (!accountsToTransactions.containsKey(account))
       {
           throw new AccountDoesNotExistException("Error: account does not  exist");
       }

      for(File file : files) {

              String accountName = file.getName().substring(6, file.getName().length() - 5);

              if (accountName.equals(account))
              {
                  accountsToTransactions.remove(account);
                  file.delete();


              }


          }

    }

public List<String>getAllAccounts()
{

    List<String> accounts = new ArrayList<String>(accountsToTransactions.keySet());
    return accounts;



}

}
