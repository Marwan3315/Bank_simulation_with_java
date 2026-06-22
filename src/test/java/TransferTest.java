import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import bank.*;
import bank.exceptions.*;


public class TransferTest {
    private Transfer transferTest;

    @BeforeEach
    public void ini() throws TransactionAttributeException
    {
        String date = "1.12.2025";
        double amount = 100.0;
        String description = "Transfer-Test";
        String sender = "A";
        String recipient=  "B";

        transferTest = new Transfer(date, amount, description, sender, recipient);


    }

@Test
public void FirstConstructorTest() throws TransactionAttributeException
{
        String date = "31.12.2025";
        double amount = 10.0;
        String description = "TestErsterKonstruktor";

        Transfer T = new Transfer(date, amount, description);
        T.setdate("15.12.2025");
        T.setamount(20.0);
        T.setdescription("Testsetters");
        assertEquals("15.12.2025", T.getdate());
        assertEquals(20.0, T.getamount());
        assertEquals("Testsetters",T.getdescription());



}
    @Test
    public void ConstructorTest()
    {
        assertEquals("1.12.2025", transferTest.getdate());
        assertEquals(100.0, transferTest.getamount());
        assertEquals("Transfer-Test", transferTest.getdescription());
        assertEquals("A",transferTest.getsender());
        assertEquals("B", transferTest.getrecipient());

        assertThrows(TransactionAttributeException.class, ()->{transferTest.setamount(-50.0);});


    }


    @Test
    public void CopyConstructorTest()
    {
        Transfer Tcopy = new Transfer(transferTest);
        assertEquals(Tcopy, transferTest);
        assertNotSame(Tcopy, transferTest);

    }

    @Test
    public void toStringTest()
    {
        String expected ="Date: 1.12.2025 Amount: 100.0 Description: Transfer-Test Sender: A Recipient: B";
        assertEquals(expected, transferTest.toString());


    }

    @Test
    public void equalsTest() throws TransactionAttributeException
    {
        Transfer T = new Transfer("31.12.2025",200.0, "TransferEqualsTest", "A", "B");
        Transfer pcopy = new Transfer(transferTest);
        assertTrue(pcopy.equals(transferTest));
        assertFalse(T.equals(transferTest));

    }
}
