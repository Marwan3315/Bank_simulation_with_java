package bank;
import com.google.gson.*;
import java.lang.reflect.Type;
import bank.exceptions.*;

public class TransactionSerializer implements JsonSerializer<Transaction>, JsonDeserializer<Transaction> {
@Override
 public JsonElement serialize(Transaction src,  Type typeOfSrc , JsonSerializationContext context)
 {
  JsonObject json = new JsonObject();
  json.addProperty("CLASSNAME" , src.getClass().getSimpleName());
  JsonObject instance = new JsonObject();
  instance.addProperty("date", src.getdate());
  instance.addProperty("amount",src.getamount());
  instance.addProperty("description",src.getdescription());
  json.add("INSTANCE", instance);

   if(src instanceof Payment)
   {
       Payment P = (Payment) src;
       instance.addProperty("incominginterest", P.getincominginterest());
       instance.addProperty("outgoinginterest", P.getoutgoinginterest());
   }

   if(src instanceof Transfer)
   {
       Transfer T = (Transfer)src;
       instance.addProperty("sender", T.getsender());
       instance.addProperty("recipient", T.getrecipient());
   }





    json.add("INSTANCE", instance);

    return json;
}



@Override
 public Transaction deserialize(JsonElement json, Type typeOfT,JsonDeserializationContext context)
 {
     // das Objekt, das gelesen werden, soll in ein Objekt umgewandelt werden
      JsonObject jsonObject = json.getAsJsonObject();

      String TypeofTransaction = jsonObject.get("CLASSNAME").getAsString();
      JsonObject instance = jsonObject.get("INSTANCE").getAsJsonObject();

     String date = instance.get("date").getAsString();
     double fileAmount = instance.get("amount").getAsDouble();
     String description = instance.get("description").getAsString();

      Transaction transaction = null;

  if (TypeofTransaction.equals("Payment"))
  {
     try
     {

         double incominginterest = instance.get("incominginterest").getAsDouble();
         double outgoinginterest = instance.get("outgoinginterest").getAsDouble();
         double calculatedBaseAmount = fileAmount;

         // Rückwärtsrechnung:
         // Formel war: amount = base - (base * interest)  =>  amount = base * (1 - interest)
         // Umstellung: base = amount / (1 - interest)

         if (fileAmount > 0 && incominginterest > 0 && incominginterest < 1) {
             calculatedBaseAmount = fileAmount / (1.0 - incominginterest); // 50 / 0.5 = 100
         }
         else if (fileAmount < 0 && outgoinginterest > 0 && outgoinginterest < 1) {
             // Formel war: amount = base + (base * outgoing) => amount = base * (1 + outgoing)
             calculatedBaseAmount = fileAmount / (1.0 + outgoinginterest);
         }
         transaction = new Payment(date, calculatedBaseAmount, description, incominginterest, outgoinginterest);
         return transaction;
     }
     catch(TransactionAttributeException e )
     {
           System.out.println(e.getMessage());

     }


  }
  if(TypeofTransaction.equals("IncomingTransfer"))
  {
      try
      {
          String sender = instance.get("sender").getAsString();
          String recipient = instance.get("recipient").getAsString();
          transaction = new IncomingTransfer(date, fileAmount, description, sender, recipient);
      }

      catch(TransactionAttributeException e){

          System.out.println(e.getMessage());
      }

  }


  if(TypeofTransaction.equals("OutgoingTransfer"))
  {
      try {
          String sender = instance.get("sender").getAsString();
          String recipient = instance.get("recipient").getAsString();
          transaction = new OutgoingTransfer(date, fileAmount, description, sender, recipient);
          return transaction;
      }

      catch(TransactionAttributeException e){
          System.out.println(e.getMessage());
      }

  }

  return transaction;

 }

}
