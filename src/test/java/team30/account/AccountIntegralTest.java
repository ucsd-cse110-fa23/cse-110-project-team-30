package team30.account;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import team30.mock.mockAccountDatabase;
import team30.mock.mockCreateAccountBackButton;
import team30.mock.mockCreateAccountCreateButton;
import team30.mock.mockLoginCreateButton;
import team30.mock.mockLoginLoginButton;

public class AccountIntegralTest {
    private mockAccountDatabase mdb;
    private mockLoginLoginButton loginButton;
    private mockLoginCreateButton loginCreateButton;
    private mockCreateAccountBackButton backButton;
    private mockCreateAccountCreateButton createAccountCreateButton;

    @BeforeEach
    void setup() {
        mdb = new mockAccountDatabase();
        loginButton = new mockLoginLoginButton("login");
        loginCreateButton = new mockLoginCreateButton("login_create");
        backButton = new mockCreateAccountBackButton("back");
        createAccountCreateButton = new mockCreateAccountCreateButton("create_create");
    }

    @Test
    public void testCreateAccountThenBack() {
        loginCreateButton.fire();
        assertEquals("login scene", backButton.fire());
    }

    @Test
    public void testLoginFailThenOpenCreateScene() {
        String username = "wrong username";
        String password = "wrong password";
        assertEquals("login scene", loginButton.fire(mdb, username, password));
        assertEquals("create account scene", loginCreateButton.fire());
    }

    @Test
    public void testLoginFailThenOpenCreateThenCreateFailAndLastCreateSuccess() {
        String username = "wrong username";
        String password = "wrong password";
        assertEquals("login scene", loginButton.fire(mdb, username, password));     // login fail
        assertEquals("create account scene", loginCreateButton.fire());                     // open create scene
        username = "1";
        password = "something";     // try to create a already existed account
        assertEquals("create account scene", createAccountCreateButton.fire(mdb, username, password));
        username = "new username";
        password = "new password";      // then create an account that does not exist
        assertEquals("app scene", createAccountCreateButton.fire(mdb, username, password));
    }
}
