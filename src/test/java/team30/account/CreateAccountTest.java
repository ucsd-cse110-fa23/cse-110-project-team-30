package team30.account;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import team30.mock.mockAccountDatabase;
import team30.mock.mockCreateAccountBackButton;
import team30.mock.mockCreateAccountCreateButton;

public class CreateAccountTest {
    private mockAccountDatabase mdb;
    private mockCreateAccountBackButton backButton;
    private mockCreateAccountCreateButton createButton;

    @BeforeEach
    void setup() {
        mdb = new mockAccountDatabase();
        backButton = new mockCreateAccountBackButton("back");
        createButton = new mockCreateAccountCreateButton("create");
    }

    @Test
    public void testBackButton() {
        assertEquals("login scene", backButton.fire());
    }

    @Test
    public void testCreateIfAlreadyExist() {
        String username = "1";
        String password = "some password doesn't matter";
        assertEquals("create account scene", createButton.fire(mdb, username, password));
        assertEquals(4, mdb.size);
    }

    @Test
    public void testCreateIfNotExist() {
        String username = "some not exist username";
        String password = "p_some not exist username";
        assertEquals("app scene", createButton.fire(mdb, username, password));
        assertEquals(5, mdb.size);
    }
}
