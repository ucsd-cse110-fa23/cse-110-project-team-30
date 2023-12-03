package team30.account;

import org.junit.jupiter.api.Test;
import team30.mock.*;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class LoginTest {
    private mockAccountDatabase mdb;

    private mockLoginLoginButton loginButton;
    private mockLoginCreateButton createButton;

    @BeforeEach
    void setup() {
        mdb = new mockAccountDatabase();
        loginButton = new mockLoginLoginButton("login");
        createButton = new mockLoginCreateButton("create");
    }

    @Test
    public void testLoginButtonCorrect() {
        String username = "2";
        String password = "p_2";  
        assertEquals("app scene", loginButton.fire(mdb, username, password));
    }

    @Test
    public void testLoginButtonIncorrectPassword() {
        String username = "2";
        String password = "some wrong password";
        assertEquals("login scene", loginButton.fire(mdb, username, password));
    }

    @Test
    public void testLoginButtonNotExistUsername() {
        String username = "Some not exist username";
        String password = "p_2";  
        assertEquals("login scene", loginButton.fire(mdb, username, password));
    }

    @Test
    public void testCreateButton() {
        assertEquals("create account scene", createButton.fire());
    }
}
