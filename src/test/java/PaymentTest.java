import bank.*;
import bank.exceptions.*;
import static org.junit.jupiter.api.Assertions.*;

import  org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.ValueSource;

public class PaymentTest {
    private Payment paymentTest;

    @BeforeEach
    public void init() throws TransactionAttributeException {
        String date = "1.12.2025";
        double amount = 100.0;
        String description = "Payment-Test";
        double incomingInterest = 0.5;
        double outgoingInterest = 0.1;


        paymentTest = new Payment(date, amount, description, incomingInterest, outgoingInterest);

    }

    @Test
    public void ConstructorTest() {
        assertNotNull(paymentTest);
        assertEquals("1.12.2025", paymentTest.getdate());
        assertEquals(50.0, paymentTest.getamount());
        assertEquals("Payment-Test", paymentTest.getdescription());
        assertEquals(0.5, paymentTest.getincominginterest());
        assertEquals(0.1, paymentTest.getoutgoinginterest());






    }
    @ParameterizedTest
    @ValueSource(doubles = {-0.1, -1.5, 1.5,})
    public void setincominginterestTest(double werte )
    {
        Exception TransactionAttributeExceptionTest = assertThrows(TransactionAttributeException.class, ()->{paymentTest.setincominginterest(werte);});
        assertEquals("Error: Incominginterest must be a positive number between 0-1 please try again",
                TransactionAttributeExceptionTest.getMessage());

    }

    @ParameterizedTest
    @ValueSource(doubles = {-0.1, -1.5, 1.5,})
    public void setoutgoinginterestTest(double werte )
    {
        Exception TransactionAttributeExceptionTest = assertThrows(TransactionAttributeException.class, ()->{paymentTest.setoutgoinginterest(werte);});
        assertEquals("Error: Outgoinginterest must be a positive number between 0-1 please try again",
                TransactionAttributeExceptionTest.getMessage());

    }



    @Test
    public void CopyConstructorTest() {
        Payment pcopy = new Payment(paymentTest);
        assertEquals(pcopy, paymentTest);
        assertNotSame(pcopy, paymentTest);//verifiziert ob pcopy ein neues objekt ist und keine refrenz, die auf paymenTest zeigt

    }

    @Test
    public void toStringtest() {
        String expected = "Date: 1.12.2025 Amount: 50.0 Description: Payment-Test Incominginterest: 0.5 Outgoinginterest: 0.1";
        assertEquals(expected, paymentTest.toString());


    }

    @Test
    public void equalsTest() throws TransactionAttributeException {
        Payment p = new Payment("1.12.2025", 100.0, "Payment-Test", 0.5, 0.1);
        assertTrue(p.equals(paymentTest));


    }

    @Test
    public void calculateTest()
    {
        assertEquals(50, paymentTest.getamount());
    }

}
