package team30.mock;

public class mockButton {
    private String text;
    public void fire(mockList ml, String s) {
        text = s;
        mockRecipe recipe = new mockRecipe();
        ml.getChildren().add(recipe);
        ml.updateTaskIndices();
        System.out.println(recipe.getRecipeTitle());
    }

    public String getText() {return text;}
}
