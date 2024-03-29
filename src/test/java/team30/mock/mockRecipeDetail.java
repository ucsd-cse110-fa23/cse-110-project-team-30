package team30.mock;

import java.util.ArrayList;

public class mockRecipeDetail {
    private String originalAF;
    private String currentScene;
    private mockDetailFooter footer;
    private mockDetailRecipe dRecipe;
    private mockList list;
    private mockRecipe recipe;

    public mockRecipeDetail(String af) {
        footer = new mockDetailFooter();
        originalAF = af;
        currentScene = originalAF;
        dRecipe = new mockDetailRecipe(new mockRecipe());
        list = new mockList();
    };

    public void openDetailWindow(mockRecipe recipe) {
        String af = createDetailView(recipe);
        currentScene = af;
    }

    public void closeDetailWindow() {
        currentScene = originalAF;
    }

    private String createDetailView(mockRecipe recipe) {
        String af = new String("detail scene");
        
        return af;
    }

    public mockDetailRecipe getDRecipe() {return dRecipe;}

    public mockRecipe getRecipe() {return recipe;}

    public String getAppFrame() {return currentScene;}

    public void saveRecipe() {
        String recipe_title = dRecipe.getDetailRecipeName();
        String meal_type = dRecipe.getMealType();
        String ingredients = dRecipe.getIngredients();
        ArrayList<String> steps = new ArrayList<String>();
        for (int i = 0; i < dRecipe.getSteps().size(); ++i) {
            steps.add(dRecipe.getSteps().get(i));
        }

        String mealTypeString = mockRecipe.validateMealType(meal_type);
        if(mealTypeString != "Breakfast" && mealTypeString != "Lunch" && mealTypeString != "Dinner") return;
        
        try {
            java.io.FileWriter outfile = new java.io.FileWriter("test.csv", true); //true = append
            // Recipe,Meal Type,Ingredients,Steps
            // format with semicolons in between different categories, like recipes.csv in Lab 6
            outfile.write(recipe_title + ";" + meal_type + ";" + ingredients + ";");
            for (String s : steps) {
                outfile.write(s + ";");
            }
            outfile.write("\n");
            outfile.close();
        }
        catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setDRecipe(mockRecipe recipe) {
        dRecipe = new mockDetailRecipe(recipe);
    }

    public void setRecipe(mockRecipe recipe) {
        this.recipe = recipe;
    }

    public void enableEdit(int step_index, String update_msg) {
        dRecipe.getSteps().set(step_index, update_msg);
    }

    public mockList getRecipeList() {return list;}
}
