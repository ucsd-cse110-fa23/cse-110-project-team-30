package team30.mock;

public class mockDeleteButton {
    private String text;
    public mockDeleteButton(String s) {text = s;}

    public String getText() {return text;} 

    public void fire(mockRecipeDetail rl, mockRecipe recipe) {
        rl.getRecipeList().removeRecipe(recipe);
    }
}
