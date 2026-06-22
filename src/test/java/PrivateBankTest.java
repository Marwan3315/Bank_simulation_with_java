
import bank.*;
import bank.exceptions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PrivateBankTest {

    private PrivateBank testBank1;
    private PrivateBank testBank2;
    private PrivateBank testBankalt;

    private static final String BANK_TEST_DIR = "PrivateBank_Test";
    private static final String BANK_TEST_ALT_DIR = "PrivateBank_Test/Alt";

    // -------------------------------------------------------
    // Helper: Aufräumen (Bleibt gleich)
    // -------------------------------------------------------
    private static void cleanupFiles() {
        String[] dirs = {BANK_TEST_DIR, BANK_TEST_ALT_DIR};
        for (String dirPath : dirs) {
            File directory = new File(dirPath);
            if (directory.exists() && directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File f : files) {
                        if (f.isFile() && f.getName().endsWith(".json")) {
                            f.delete();
                        }
                    }
                }
            }
        }
    }

    @BeforeAll
    public static void beforeAll() {
        new File(BANK_TEST_DIR).mkdirs();
        new File(BANK_TEST_ALT_DIR).mkdirs();
        cleanupFiles();
    }

    @AfterEach
    public void afterEach() {
        cleanupFiles();
    }

    @BeforeEach
    public void init() {
        try {
            testBank1 = new PrivateBank("TestBank", 0.5, 0.3, BANK_TEST_DIR);
            testBank2 = new PrivateBank("TestBank", 0.5, 0.3, BANK_TEST_DIR);
            testBankalt = new PrivateBank("TestBank", 0.5, 0.3, BANK_TEST_ALT_DIR);
        } catch (Exception e) {
            System.out.println("Init Fehler: " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // TESTS (Nur Public Methoden)
    // -------------------------------------------------------

    @Test
    public void testPrivateBankConstructor() {
        // Wir nutzen die Getter statt der Attribute
        assertEquals("TestBank", testBank1.getname());
        assertEquals(0.5, testBank1.getincominginterest());
        assertEquals(0.3, testBank1.getoutgoinginterest());
        assertEquals(BANK_TEST_DIR, testBank1.getdirectoryName());
    }

    @Test
    public void testPrivateBankCopyConstructor() throws Exception{
        PrivateBank originalBank = new PrivateBank("OriginalBank", 0.5, 0.3, BANK_TEST_DIR);
        PrivateBank copiedBank = new PrivateBank(originalBank);

        // Vergleich über Public Getter
        assertEquals(originalBank.getname(), copiedBank.getname());
        assertEquals(originalBank.getincominginterest(), copiedBank.getincominginterest());
        assertEquals(originalBank.getoutgoinginterest(), copiedBank.getoutgoinginterest());
        assertEquals(originalBank.getdirectoryName(), copiedBank.getdirectoryName());

        // Wir prüfen, ob die Objekte gleich sind (über deine equals Methode)
        assertEquals(originalBank, copiedBank);
        // Aber es dürfen nicht dieselben Speicheradressen sein
        assertNotSame(originalBank, copiedBank);
    }

    @Test
    public void testCreateAccount() {
        String newAccount = "TestAccount";

        assertDoesNotThrow(() -> testBank1.createAccount(newAccount));

        // ERSATZ FÜR containsKey:
        // Wir holen die Transaktionen. Wenn das Konto da ist, darf das Ergebnis nicht null sein.
        assertNotNull(testBank1.getTransactions(newAccount), "Konto sollte existieren");

        assertThrows(AccountAlreadyExistsException.class,
                () -> testBank1.createAccount(newAccount));
    }

    @Test
    public void testCreateAccountWithTransactions() throws Exception {
        String newAccount = "TestAccount1";

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Payment("2025-11-25", 100.0, "Test payment", 0.5, 0.3));
        transactions.add(new IncomingTransfer("2025-11-25", 100.0, "description", "A", "B"));
        transactions.add(new Transfer("2025-11-25", 100.0,"Transfer", "A", "B" ));
         // falsche Transactions

        assertThrows(TransactionAttributeException.class, () -> {
            testBank1.createAccount("TestAccount1", List.of(
                    new Payment("2025-11-25", -100.0, "false Payment", 1.5, 0.0)
            ));
        });


        assertDoesNotThrow(() -> testBank1.createAccount(newAccount, transactions));

        // Public Prüfung: Sind die Transaktionen drin?
        assertEquals(transactions, testBank1.getTransactions(newAccount));

        assertThrows(AccountAlreadyExistsException.class,
                () -> testBank1.createAccount(newAccount, transactions));
    }

    @Test
    public void testAddTransaction() throws Exception {
        String newAccount = "TestAccount7";
        testBank1.createAccount(newAccount);

        Transaction t1 = new Payment("2025-11-25", 100, "Test payment", 0.5, 0.3);
        Transaction t2 = new IncomingTransfer("2025-11-25", 100, "description", "A", "B");

        testBank1.addTransaction(newAccount, t1);
        testBank1.addTransaction(newAccount, t2);

        // Wir prüfen über die Public Methode containsTransaction
        assertTrue(testBank1.containsTransaction(newAccount, t1));
        assertTrue(testBank1.containsTransaction(newAccount, t2));

        // Oder wir prüfen die ganze Liste
        List<Transaction> list = testBank1.getTransactions(newAccount);
        assertEquals(2, list.size());
        assertTrue(list.contains(t1));


          assertThrows(TransactionAttributeException.class,
                () -> {testBank1.addTransaction(newAccount,  new Payment("2025-11-25",100,"FalschePayment", 1.5, 0.0));});

        assertThrows(TransactionAlreadyExistException.class,
                () -> testBank1.addTransaction(newAccount, t1));
    }

    @Test
    public void testGetAccountBalance() throws Exception {
        String newAccount = "TestAccount8";

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Payment("2025-11-25", 3, "Test payment", 0.5, 0.3)); // + 1.5 (bei 0.5 incoming)
        transactions.add(new IncomingTransfer("2025-11-25", 10, "desc", "X", "Y")); // + 10
        transactions.add(new OutgoingTransfer("2025-11-25", 5, "desc", "Y", "X"));  // - 5

        testBank1.createAccount(newAccount, transactions);

        // 1.5 + 10 - 5 = 6.5
        assertEquals(6.5, testBank1.getAccountBalance(newAccount));
    }

    @Test
    public void testRemoveTransaction() throws Exception {
        String account = "TestAccount2";
        Payment t = new Payment("2025-11-25", 100, "Test", 0.2, 0.1);
        List<Transaction> list = new ArrayList<>();
        list.add(t);

        testBank1.createAccount(account, list);

        assertDoesNotThrow(() -> testBank1.removeTransaction(account, t));

        // Prüfung: Transaktion darf nicht mehr gefunden werden
        assertFalse(testBank1.containsTransaction(account, t));

        assertThrows(TransactionDoesNotExistException.class,
                () -> testBank1.removeTransaction(account, t));
    }

    @Test
    public void testReadAccount() throws Exception {
        // 1. Setup: Wir schreiben Daten mit Bank A
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Payment("2025-11-25", 100, "Test payment", 0.5, 0.3));

        PrivateBank writer = new PrivateBank("TestBank", 0.5, 0.3, BANK_TEST_ALT_DIR);
        writer.createAccount("TestAccount1", transactions);

        // 2. Action: Wir lesen Daten mit Bank B (neues Objekt)
        PrivateBank reader = new PrivateBank("TestBank", 0.5, 0.3, BANK_TEST_ALT_DIR);

        // Methode ohne Parameter aufrufen
        reader.readAccount();

        // 3. Assertion: Wir prüfen NUR mit public methoden
        // Wenn das Konto existiert, darf getTransactions nicht null sein
        assertNotNull(reader.getTransactions("TestAccount1"));

        // Prüfen, ob die Transaktion korrekt geladen wurde
        assertEquals(1, reader.getTransactions("TestAccount1").size());
        assertEquals(transactions.get(0), reader.getTransactions("TestAccount1").get(0));
    }

    @Test
    public void testToString() throws Exception {
        testBank1.createAccount("TestAccount1");

        String output = testBank1.toString();

        // Wir prüfen nur, ob wichtige Schlagwörter im String vorkommen
        assertTrue(output.contains("TestBank"));
        assertTrue(output.contains("TestAccount1"));
        assertTrue(output.contains("0.5")); // incoming interest
    }

}