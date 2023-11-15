package team30.mock;

import java.util.ArrayList;

public class mockRecipe {
    private String index;
    private String recipe_title;
    private String mealtype;
    private String ingredients;
    private ArrayList<String> steps;

    public mockRecipe() {
        recipe_title = "example";
        index = "";
        mealtype = "";
        ingredients = "";
        steps = new ArrayList<>();
    }    

    public mockRecipe(String name, String ingredients, ArrayList<String> steps, String mealtype) {
        index = "";
        this.recipe_title = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.mealtype = mealtype;
    }

    public String getIndex() {return index;}
    public String getRecipeTitle() {return recipe_title;}
    public void setIndex(String s) {index = s;}
    public String getMealType() {return mealtype;}
    public String getIngredients() {return ingredients;}
    public ArrayList<String> getSteps() {return steps;} 
}
