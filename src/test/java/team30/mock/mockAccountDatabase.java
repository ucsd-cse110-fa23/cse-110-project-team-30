package team30.mock;

import static org.mockito.ArgumentMatchers.eq;

import java.util.ArrayList;

import org.bson.conversions.Bson;

import com.mongodb.client.MongoCursor;

public class mockAccountDatabase {
    public ArrayList<String> usernames;
    public ArrayList<String> passwords;
    public int size;

    public mockAccountDatabase() {
        usernames = new ArrayList<>();
        passwords = new ArrayList<>();
        
        usernames.add("1");
        usernames.add("2");
        usernames.add("test");
        usernames.add("some really long user name");

        passwords.add("p_1");
        passwords.add("p_2");
        passwords.add("p_test");
        passwords.add("p_some really long user name");
        size = 4;
    }

    public void createAccount(String username, String password) {
        size++;
        usernames.add(username);
        passwords.add(password);
    }

    public int validUser(String username, String password) {
        if (usernames.indexOf(username) != -1) {
            if (passwords.get(usernames.indexOf(username)).equals(password)) {
                return 0;
            }
            else {
                return 1;
            }
        }
        else {
            return -1;
        }
    }

    public boolean accountExist(String username) {
        return (usernames.indexOf(username) == -1) ? false : true;
    }
}
