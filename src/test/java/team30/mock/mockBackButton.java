package team30.mock;

public class mockBackButton {
    private String text;
    public mockBackButton(String s) {text = s;}

    public String getText() {return text;}

    public void fire(mockRecipeDetail rl) {
        rl.closeDetailWindow();
    } 
}
