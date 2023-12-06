package team30.mock;

import static org.mockito.ArgumentMatchers.startsWith;

import java.util.ArrayList;

public class mockRecipe {

    static String[] validMealTypes = {"Breakfast","Lunch","Dinner"};

    /**
     * Given a string representing a mealType,
     * changes the string to the standard mealType string if it is a valid meal type.
     * Else, it returns null
     * @return a properly capitalized mealType if the given string is valid, and null if not.
     */
    static String validateMealType(String mealType){
        for(String i : validMealTypes){
            if(mealType.toLowerCase().equals(i.toLowerCase()))
                return i;
        }
        return null;
    }

    private String index;
    private String recipe_title;
    private String mealtype;
    private String ingredients;
    private ArrayList<String> steps;

    public mockRecipe() {
        recipe_title = "Example";
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
    public void setMealType(String s) {mealtype = s;}
}
