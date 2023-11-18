package team30.mock;

public class mockSaveButton {
    private String text;
    public mockSaveButton(String s) {text = s;}

    public String getText() {return text;}

    public void fire(mockRecipeDetail rl) {rl.saveRecipe();}
}
