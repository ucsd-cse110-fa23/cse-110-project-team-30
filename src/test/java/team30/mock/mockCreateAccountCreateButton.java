package team30.mock;

public class mockCreateAccountCreateButton {
    private String text;
    public mockCreateAccountCreateButton(String s) {text = s;}

    public String getText() {return text;} 

    public String fire(mockAccountDatabase mdb, String username, String password) {
        if (mdb.accountExist(username)) {
            return "create account scene";
        }
        else {
            mdb.createAccount(username, password);
            return "app scene";
        }
    }
}
