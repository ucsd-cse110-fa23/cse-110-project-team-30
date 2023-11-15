package team30.mock;

public class mockEditButton {
    private String text;
    public mockEditButton(String s) {text = s;}

    public String getText() {return text;} 

    public void fire(mockRecipeDetail rl, int step_index, String update_msg) {
        rl.enableEdit(step_index, update_msg);;
    }
}
