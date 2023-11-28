package team30.account;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import team30.mock.mockAccountDatabase;

public class AccountDatabaseTest {
    private mockAccountDatabase mdb;

    @BeforeEach
    public void setup() {
        mdb = new mockAccountDatabase();
    }

    @Test
    public void testCreateAccount() {
        String username = "10";
        String password = "10";
        mdb.createAccount(username, password);
        assertEquals(5, mdb.size);
    }

    @Test
    public void testvalidUserCorrect() {
        String username = "test";
        String password = "p_test";
        assertEquals(0, mdb.validUser(username, password));
    }

    @Test
    public void testvalidUserIncorrectPassword() {
        String username = "test";
        String password = "wrong password";
        assertEquals(1, mdb.validUser(username, password));
    }

    @Test
    public void testvalidUserNotExistUsername() {
        String username = "Not Exist Username";
        String password = "p_test";
        assertEquals(-1, mdb.validUser(username, password));
    }

    @Test
    public void testAccountExistDoesExist() {
        String username = "some really long user name";
        assertEquals(true, mdb.accountExist(username));
    }

    @Test
    public void testAccountExistButNotExist() {
        String username = "Not Exist Username";
        assertEquals(false, mdb.accountExist(username));
    }
}
