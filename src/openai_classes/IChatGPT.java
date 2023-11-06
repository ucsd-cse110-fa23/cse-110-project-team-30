package openai_classes;

import team30.meal.MealType;

/**
 * An interface for ChatGPT
 * The class is meant to make a call to ChatGPT to return a recipe.
 */
public interface IChatGPT {
    /**
     * Given a meal type and ingredients,
     * generates a resulting recipe.
     * @param type - the type of meal of the recipe
     * @param ingredients - the ingredients used in the recipe
     * @return a string with a name and step by step instructions.
     */
    public String generateMeal(MealType type, String ingredients);
}