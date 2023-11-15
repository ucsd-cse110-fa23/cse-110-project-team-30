package team30.mock;

import java.util.ArrayList;

public class mockDetailRecipe {
   // recipe info
   private String recipe_name;
   private String ingredients;
   private ArrayList<String> steps;
   private String mealtype;
   int index_num;

   public mockDetailRecipe (mockRecipe recipe) {
       // initia recipe info
       recipe_name = recipe.getRecipeTitle();
       ingredients = recipe.getIngredients();
       steps = recipe.getSteps();
       mealtype = recipe.getMealType();
       index_num = 1;
       
       if (steps != null) {
           for (int i = 0; i < steps.size(); ++i, index_num++) {}
           index_num--;
       }
   }

   public String getDetailRecipeName() {return recipe_name;}
   public String getIngredients() {return ingredients;}
   public ArrayList<String> getSteps() {return steps;}
   public String getMealType() {return mealtype;}
   public int getIndex() {return index_num;} 
}
