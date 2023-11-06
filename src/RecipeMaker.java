import team30.meal.MealType;
import openai_classes.*;

import java.io.File;

/**
 * This class handles the creation of Recipes.
 * Given a recipe generator through IChatGPT,
 *  and a voice file decipherer through IWhisper,
 * it gets gets information on the type of recipe
 *  and generates a recipe through that.
 */
public class RecipeMaker {
    IWhisper whisper;
    IChatGPT chatGPT;

    RecipeMaker(IWhisper whisper, IChatGPT chatGPT){
        this.whisper = whisper;
        this.chatGPT = chatGPT;
    }

    /**
     * Given an audio file, makes a call to Whisper to get the ingredients spoken in the file.
     * @param file - audio file to get the list of ingredients from.
     * @return a string containing the speech within the given audio file, expected to be a list of ingredients.
     */
    private String getIngredients(File file){
        return null;
    }

    /**
     * Given an audio file, makes a call to Whisper to get the Meal Type.
     * If a valid mealType is named, it is returned. Otherwise null is returned.
     * @param file - audio file to convert to text
     * @return the mealType that matches what's said in the file, or null if the word generated is invalid.
     */
    private MealType getMealType(File file){
        return null;
    }

    /**
     * Given the mealType and ingredients, calls ChatGPT to generate instructions.
     * @param mealType - the type of meal to be created by ChatGPT
     * @param ingredients - the type of ingredients to be used in the recipe created by ChatGPT.
     * @return a string containing step by step instructions to create the recipe.
     */
    private String getInstructions(String mealType, String ingredients){
        return null;
    }

    /**
     * Given the mealType, title, ingredients, and instructions, creates a new recipe class with fields matching the arguements.
     * @param mealType - intended value for the mealType
     * @param title - intended value for the title.
     * @param ingredients - intended value for the ingredients
     * @param instructions - intended value for the instructions.
     * @return a recipe with fields matching the arguments given.
     */
    Recipe combineIntoRecipe(MealType mealType, String title, String ingredients, String instructions){
        return new Recipe(mealType, title, ingredients, instructions);
    }

    /**
     * Given two files, creates a recipe out of the information in both files.
     * @param mealTypeFile - an audio file containing a spoken type of meal.
     * @param ingredientsFile - an audio file containing a spoken list of ingredients.
     * @return a recipe of the meal type spoken in mealTypeFile and of ingredients spoken in ingredientsFile.
     */
    Recipe createRecipe(File mealTypeFile, File ingredientsFile){
        return null;
    }
}