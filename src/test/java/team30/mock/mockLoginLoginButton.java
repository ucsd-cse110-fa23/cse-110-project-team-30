package team30.mock;

public class mockLoginLoginButton {
    private String text;
    public mockLoginLoginButton(String s) {text = s;}

    public String getText() {return text;} 

    public String fire(mockAccountDatabase mdb, String username, String password) {
        int valid = mdb.validUser(username, password);
        System.out.println(valid);
        if(valid == 0) {
            return "app scene";
        }
        else {
            return "login scene";
        }
    }
}
