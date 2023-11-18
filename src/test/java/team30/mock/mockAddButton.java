package team30.mock;

public class mockAddButton {
    private String text;
    public void fire(mockList ml, mockRecipe recipe) {
        ml.getChildren().add(recipe);
        ml.updateTaskIndices();
        System.out.println(recipe.getRecipeTitle());
    }

    public String getText() {return text;}
}
