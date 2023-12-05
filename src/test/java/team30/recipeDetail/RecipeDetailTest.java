package team30.recipeDetail;

import team30.recipeList.RecipeDetail;
import team30.recipeList.RecipeList;
import team30.mock.*;

import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.objenesis.instantiator.sun.MagicInstantiator;

import javafx.beans.binding.ObjectBinding;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.text.*;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Files;


public class RecipeDetailTest {
    private mockDetailFooter footer;
    private mockRecipeDetail rl;
    private mockRecipe concrete_recipe;

    @BeforeEach
    void setUp() {
        footer = new mockDetailFooter();
        rl = new mockRecipeDetail("original scene");

        ArrayList<String> steps = new ArrayList<>();
        steps.add("step 1");
        steps.add("step 2");
        steps.add("step 3");
        concrete_recipe = new mockRecipe("example", "Some ingredients", steps, "Dinner");
    }

    @AfterAll
    static void cleanUp() {
        // delete test.csv file
        String filePath = "test.csv";
        try {
            Path path = FileSystems.getDefault().getPath(filePath);
            if (Files.exists(path)) {
                Files.delete(path);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testFooterInitialization() {
        // open detail windows, then buttons show up
        rl.openDetailWindow(concrete_recipe);

        assertEquals("Edit", footer.getEdit().getText());
        assertEquals("Back", footer.getBack().getText());
        assertEquals("Delete", footer.getDelete().getText());
        assertEquals("Save", footer.getSave().getText());
    }

    @Test
    void testDetailRecipeInitialization() {
        String recipe_name = "example";
        String meal_type = "Dinner";
        String ingredients = "Some ingredients";
        ArrayList<String> steps = new ArrayList<>();
        steps.add(new String("step 1"));
        steps.add(new String("step 2"));
        steps.add(new String("step 3"));

        rl.setDRecipe(concrete_recipe);
        assertEquals(recipe_name, rl.getDRecipe().getDetailRecipeName());
        assertEquals(meal_type, rl.getDRecipe().getMealType());
        assertEquals(ingredients, rl.getDRecipe().getIngredients());
        assertEquals(steps, rl.getDRecipe().getSteps());
        assertEquals(3, rl.getDRecipe().getIndex());
    }

    @Test
    void testRecipeDetailInitialization() {
        assertEquals("original scene", rl.getAppFrame());
    }

    @Test
    void testRecipeDetailOpenDetailWindow() {
        rl.openDetailWindow(new mockRecipe());
        assertEquals("detail scene", rl.getAppFrame());
    }

    @Test
    void testRecipeDetailCloseDetailWindow() {
        rl.openDetailWindow(new mockRecipe());
        rl.closeDetailWindow();
        assertEquals("original scene", rl.getAppFrame());
    }

    @Test
    void testBackButton() {
        // open detail windows, then buttons show up
        rl.openDetailWindow(new mockRecipe());

        footer.getBack().fire(rl);
        assertEquals("original scene", rl.getAppFrame());
    }

    @Test 
    void testSaveButton() {
        // open detail windows, then buttons show up
        rl.openDetailWindow(concrete_recipe);

        rl.setDRecipe(concrete_recipe);
        footer.getSave().fire(rl);
        
        String filePath = "test.csv";

        try {
            // drop test.csv first
            Path path = FileSystems.getDefault().getPath(filePath);
            if (Files.exists(path)) {
                Files.delete(path);
            }

            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");
                assertEquals(values[0], rl.getDRecipe().getDetailRecipeName());
                assertEquals(values[1], rl.getDRecipe().getMealType());
                assertEquals(values[2], rl.getDRecipe().getIngredients());
                // compare if the number of steps match
                assertEquals(values.length - 3, rl.getDRecipe().getSteps().size());
                for (int i = 3; i < values.length; ++i) {
                    assertEquals(values[i], rl.getDRecipe().getSteps().get(i - 3));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void testSaveMultipleRecipe() {
        // open detail windows, then buttons show up
        rl.openDetailWindow(concrete_recipe);

        ArrayList<String> steps1 = new ArrayList<>();
        steps1.add("Step 1");
        steps1.add("Step 2");
        steps1.add("Step 3");
        steps1.add("Step 4");
        mockRecipe recipe1 = new mockRecipe("Recipe 1", "Some ingredients", steps1, "Breakfast");
        rl.setDRecipe(recipe1);
        footer.getSave().fire(rl);

        ArrayList<String> steps2 = new ArrayList<>();
        steps2.add("Step 1");
        steps2.add("Step 2");
        steps2.add("Step 3");
        steps2.add("Step 4");
        steps2.add("Step 5");
        steps2.add("Step 6");
        steps2.add("Step 7");
        mockRecipe recipe2 = new mockRecipe("Recipe 2", "Some ingredients", steps2, "Lunch");
        rl.setDRecipe(recipe2);
        footer.getSave().fire(rl);

        ArrayList<String> steps3 = new ArrayList<>();
        steps3.add("Step 1");
        mockRecipe recipe3 = new mockRecipe("Recipe 3", "Some ingredients", steps3, "Dinner");
        rl.setDRecipe(recipe3);
        footer.getSave().fire(rl);

        String filePath = "test.csv";
        try {
            // drop test.csv first
            Path path = FileSystems.getDefault().getPath(filePath);
            if (Files.exists(path)) {
                Files.delete(path);
            }

            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");
                assertEquals(values[0], rl.getDRecipe().getDetailRecipeName());
                assertEquals(values[1], rl.getDRecipe().getMealType());
                assertEquals(values[2], rl.getDRecipe().getIngredients());
                // compare if the number of steps match
                assertEquals(values.length - 3, rl.getDRecipe().getSteps().size());
                for (int i = 3; i < values.length; ++i) {
                    assertEquals(values[i], rl.getDRecipe().getSteps().get(i - 3));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testInvalidMealTypeSaves(){
        rl.openDetailWindow(concrete_recipe);

        ArrayList<String> steps1 = new ArrayList<>();
        steps1.add("Step 1");
        steps1.add("Step 2");
        steps1.add("Step 3");
        steps1.add("Step 4");
        mockRecipe recipe1 = new mockRecipe("Recipe 1", "Some ingredients", steps1, "Inedible");
        rl.setDRecipe(recipe1);
        footer.getSave().fire(rl);

        ArrayList<String> steps3 = new ArrayList<>();
        steps3.add("Step 1");
        mockRecipe recipe3 = new mockRecipe("Recipe 3", "Some ingredients", steps3, "Dinner");
        rl.setDRecipe(recipe3);
        footer.getSave().fire(rl);

        String filePath = "test.csv";
        try {
            // drop test.csv first
            Path path = FileSystems.getDefault().getPath(filePath);
            if (Files.exists(path)) {
                Files.delete(path);
            }

            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            assertEquals(null,line,"Check if nothing is added.");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testEditButton() {
        // open detail windows, then buttons show up
        rl.openDetailWindow(concrete_recipe);
        
        int step_index = 0;
        String update_msg = "update";
        rl.setDRecipe(concrete_recipe);
        footer.getEdit().fire(rl, step_index, update_msg);
        assertEquals(update_msg, rl.getDRecipe().getSteps().get(step_index));
    }

    @Test
    void testEditThenSave() {
        rl.openDetailWindow(concrete_recipe);

        // edit
        int step_index = 2;     // index bound = 2
        String update_msg = "Update to Something else";
        rl.setDRecipe(concrete_recipe);
        footer.getEdit().fire(rl, step_index, update_msg);

        // save
        footer.getSave().fire(rl);  
        String filePath = "test.csv";
        try {
            // drop test.csv first
            Path path = FileSystems.getDefault().getPath(filePath);
            if (Files.exists(path)) {
                Files.delete(path);
            }

            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");
                assertEquals(values[0], rl.getDRecipe().getDetailRecipeName());
                assertEquals(values[1], rl.getDRecipe().getMealType());
                assertEquals(values[2], rl.getDRecipe().getIngredients());
                // compare if the number of steps match
                assertEquals(values.length - 3, rl.getDRecipe().getSteps().size());
                for (int i = 3; i < values.length; ++i) {
                    assertEquals(values[i], rl.getDRecipe().getSteps().get(i - 3));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testEditThenBack() {
        // open detail windows, then buttons show up
        rl.openDetailWindow(concrete_recipe);

        // edit
        int step_index = 2;     // index bound = 2
        String update_msg = "Update to Something else";
        rl.setDRecipe(concrete_recipe);
        footer.getEdit().fire(rl, step_index,update_msg);
    
        // back
        footer.getBack().fire(rl);
        assertEquals("original scene", rl.getAppFrame());
    }

    @Test
    void testSaveThenBack() {       // in practice, save should automatically call back 
        // open detail windows, then buttons show up
        rl.openDetailWindow(concrete_recipe);
        
        // save
        rl.setDRecipe(concrete_recipe);
        footer.getSave().fire(rl);

        // back
        footer.getBack().fire(rl);
        assertEquals("original scene", rl.getAppFrame());
    }

    @Test
    void testDeleteButton() {
        // open detail windows, then buttons show up
        rl.getRecipeList().getAddButton().fire(rl.getRecipeList(), concrete_recipe);
        rl.openDetailWindow(concrete_recipe);

        footer.getDelete().fire(rl, concrete_recipe);

        assertFalse(rl.getRecipeList().getChildren().contains(concrete_recipe), "Recipe should be removed");
        assertEquals(0 , rl.getRecipeList().getChildren().size());
    }

    @Test
    void testDeleteMultipleRecipes() {      // add 3 recipes, delete 2, should remain only 1
        // add 3 recipes first
        mockRecipe recipeToRemove1 = new mockRecipe();
        rl.getRecipeList().getAddButton().fire(rl.getRecipeList(), recipeToRemove1);
        mockRecipe recipeToRemove2 = new mockRecipe();
        rl.getRecipeList().getAddButton().fire(rl.getRecipeList(), recipeToRemove2);
        mockRecipe recipeToRemove3 = new mockRecipe();
        rl.getRecipeList().getAddButton().fire(rl.getRecipeList(), recipeToRemove3);
        
        // remove first and third
        rl.openDetailWindow(recipeToRemove1);
        footer.getDelete().fire(rl, recipeToRemove1);
        footer.getBack().fire(rl);;
        rl.openDetailWindow(recipeToRemove3);
        footer.getDelete().fire(rl, recipeToRemove3);
        footer.getBack().fire(rl);;

        assertFalse(rl.getRecipeList().getChildren().contains(recipeToRemove1), "Recipe should be removed");
        assertFalse(rl.getRecipeList().getChildren().contains(recipeToRemove3), "Recipe should be removed");
        assertEquals(1 , rl.getRecipeList().getChildren().size());
    }

    @Test
    void testEditThenDelete() {
        rl.getRecipeList().getAddButton().fire(rl.getRecipeList(), concrete_recipe);
        rl.openDetailWindow(concrete_recipe);

        // edit
        int step_index = 2;     // index bound = 2
        String update_msg = "Update to Something else";
        rl.setDRecipe(concrete_recipe);
        footer.getEdit().fire(rl, step_index,update_msg);

        footer.getDelete().fire(rl, concrete_recipe);

        assertFalse(rl.getRecipeList().getChildren().contains(concrete_recipe), "Recipe should be removed");
        assertEquals(0 , rl.getRecipeList().getChildren().size());
    }   
    
    @Test
    void testDeleteThenBack() {     // in practice, delete should automatically call back
        rl.getRecipeList().getAddButton().fire(rl.getRecipeList(), concrete_recipe);
        rl.openDetailWindow(concrete_recipe);

        footer.getDelete().fire(rl, concrete_recipe);

        footer.getBack().fire(rl);

        assertFalse(rl.getRecipeList().getChildren().contains(concrete_recipe), "Recipe should be removed");
        assertEquals(0 , rl.getRecipeList().getChildren().size());
    }

    @Test
    void testRefreshButton() {
        ArrayList<String> steps = new ArrayList<>();
        steps.add("1. Heat the olive oil in a large skillet over medium heat.");
        steps.add("2. Add the tomato slices and season with salt and pepper.");
        steps.add("3. Cook the tomatoes until they are lightly browned, about 3 minutes.");
        steps.add("4. Crack the eggs into the skillet and season with additional salt and pepper.");
        steps.add("5. Cook the eggs until the whites are set and the yolk is cooked to your preference.");
        steps.add("6. Serve the tomato and egg skillet with your favorite condiments. Enjoy!");

        // open detail windows, then buttons show up
        rl.openDetailWindow(concrete_recipe);

        // before refresh button
        rl.setRecipe(concrete_recipe);
        rl.setDRecipe(concrete_recipe);
        footer.getRefresh().fire(rl);
        // after refresh button
        assertEquals("breakfast", rl.getDRecipe().getMealType());
        assertEquals("tomato and egg skillet", rl.getDRecipe().getDetailRecipeName());
        assertEquals("2 eggs, 1 tomato, sliced, salt and pepper, to taste, 2 teaspoons olive oil, ", rl.getDRecipe().getIngredients());
        for (int i = 0; i < rl.getDRecipe().getSteps().size(); ++i) {
            assertEquals(steps.get(i), rl.getDRecipe().getSteps().get(i));
        }

        assertEquals("breakfast", rl.getRecipe().getMealType());
        assertEquals("tomato and egg skillet", rl.getRecipe().getRecipeTitle());
        assertEquals("2 eggs, 1 tomato, sliced, salt and pepper, to taste, 2 teaspoons olive oil, ", rl.getRecipe().getIngredients());
        for (int i = 0; i < rl.getRecipe().getSteps().size(); ++i) {
            assertEquals(steps.get(i), rl.getRecipe().getSteps().get(i));
        }
    }

    @Test
    void testRefreshThenSave() {
        ArrayList<String> steps = new ArrayList<>();
        steps.add("1. Heat the olive oil in a large skillet over medium heat.");
        steps.add("2. Add the tomato slices and season with salt and pepper.");
        steps.add("3. Cook the tomatoes until they are lightly browned, about 3 minutes.");
        steps.add("4. Crack the eggs into the skillet and season with additional salt and pepper.");
        steps.add("5. Cook the eggs until the whites are set and the yolk is cooked to your preference.");
        steps.add("6. Serve the tomato and egg skillet with your favorite condiments. Enjoy!");

        // open detail windows, then buttons show up
        rl.openDetailWindow(concrete_recipe);

        // before refresh button
        rl.setRecipe(concrete_recipe);
        rl.setDRecipe(concrete_recipe);
        footer.getRefresh().fire(rl);
        // after refresh button
        assertEquals("breakfast", rl.getDRecipe().getMealType());
        assertEquals("tomato and egg skillet", rl.getDRecipe().getDetailRecipeName());
        assertEquals("2 eggs, 1 tomato, sliced, salt and pepper, to taste, 2 teaspoons olive oil", rl.getDRecipe().getIngredients());
        for (int i = 0; i < rl.getDRecipe().getSteps().size(); ++i) {
            assertEquals(steps.get(i), rl.getDRecipe().getSteps().get(i));
        }

        assertEquals("breakfast", rl.getRecipe().getMealType());
        assertEquals("tomato and egg skillet", rl.getRecipe().getRecipeTitle());
        assertEquals("2 eggs, 1 tomato, sliced, salt and pepper, to taste, 2 teaspoons olive oil", rl.getRecipe().getIngredients());
        for (int i = 0; i < rl.getRecipe().getSteps().size(); ++i) {
            assertEquals(steps.get(i), rl.getRecipe().getSteps().get(i));
        }

        // save
        rl.setDRecipe(concrete_recipe);
        footer.getSave().fire(rl);

        String filePath = "test.csv";

        try {
            // drop test.csv first
            Path path = FileSystems.getDefault().getPath(filePath);
            if (Files.exists(path)) {
                Files.delete(path);
            }

            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");
                assertEquals(values[0], rl.getDRecipe().getDetailRecipeName());
                assertEquals(values[1], rl.getDRecipe().getMealType());
                assertEquals(values[2], rl.getDRecipe().getIngredients());
                // compare if the number of steps match
                assertEquals(values.length - 3, rl.getDRecipe().getSteps().size());
                for (int i = 3; i < values.length; ++i) {
                    assertEquals(values[i], rl.getDRecipe().getSteps().get(i - 3));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}