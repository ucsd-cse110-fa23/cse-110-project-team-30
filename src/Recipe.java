import team30.meal.*;

/**
 * Representation of a recipe.
 */
public class Recipe {
    MealType mealType;
    String name;
    String ingredients;
    String steps;

    Recipe(MealType mealType, String name, String ingredients, String steps){
        this.mealType = mealType;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    MealType getMealType(){
        return this.mealType;
    }

    String getsteps(){
        return this.steps;
    }

    String getIngredients(){
        return this.ingredients;
    }

    String getName(){
        return this.name;
    }

    void setSteps(String steps){
        this.steps = steps;
    }

    void setName(String name){
        this.name = name;
    }
}
