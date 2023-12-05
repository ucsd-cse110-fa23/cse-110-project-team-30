package team30.mock;

import org.json.JSONArray;
import org.json.JSONObject;

import team30.recipeList.ChatGPT;
import team30.recipeList.ImageManager;
import team30.recipeList.Recipe;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import team30.mock.*;

public class mockChatGPT{
    public String generateRecipe(String mealType, String ingredientsRaw){
        // a sample real chatGPT generated recipe
        return  "Recipe Name: Tomato and Egg Skillet\n" + //
                "\n" + //
                "Ingredients:\n" + //
                "2 eggs\n" + //
                "1 tomato, sliced\n" + //
                "Salt and pepper, to taste\n" + //
                "2 teaspoons olive oil\n" + //
                "\n" + //
                "Instructions:\n" + //
                "1. Heat the olive oil in a large skillet over medium heat.\n" + //
                "2. Add the tomato slices and season with salt and pepper.\n" + //
                "3. Cook the tomatoes until they are lightly browned, about 3 minutes.\n" + //
                "4. Crack the eggs into the skillet and season with additional salt and pepper.\n" + //
                "5. Cook the eggs until the whites are set and the yolk is cooked to your preference.\n" + //
                "6. Serve the tomato and egg skillet with your favorite condiments. Enjoy!\n" + //
                "";
   }

   public mockRecipe makeRecipeByChatGPTResponse(String mealType, String ingredientsRaw, String username) {
        String generatedRecipe = generateRecipe(mealType, ingredientsRaw);
        String[] lines = generatedRecipe.split("\\r?\\n|\\r");
        String recipeName = "", ingredients = "", imgurl = "";
        ArrayList<String> steps = new ArrayList<>();
        int count = 0; //0- recipeName, 1- ingredients, 2- instructions
        for (int i = 0; i < lines.length; i++) {
            System.out.println(lines[i]);
            if (!(lines[i].replaceAll("\\s", "") == "") && !(lines[i].replaceAll("\\n", "") == "") && count == 0) {
                //recipeName (unlabelled)
                recipeName = lines[i].toLowerCase();
                count = 100;
            }
            if (lines[i].contains("Recipe Name: ")) {
                //recipeName (labelled)
                recipeName = lines[i].substring(13).toLowerCase();
                count = 100;
            }
            
            if (lines[i].contains("Ingredients:")) {
                count = 1;
                continue;
            }
            else if (lines[i].contains("Instructions:")) {
                count = 2;
                continue;
            }
            
            if (count == 1) {
                //ingredients
                if (!ingredients.equals("") && !(lines[i].replaceAll("\\n", "") == ""))
                ingredients += ", ";
                ingredients += lines[i].toLowerCase().replaceAll("-", "");
            }
            
            if (count == 2) {
                //steps
                if (!(lines[i].replaceAll("\\s", "") == "") && !(lines[i].replaceAll("\\n", "") == ""))
                steps.add(lines[i]);
            }
        }

        return new mockRecipe(recipeName, ingredients, steps, mealType);
    }
}
