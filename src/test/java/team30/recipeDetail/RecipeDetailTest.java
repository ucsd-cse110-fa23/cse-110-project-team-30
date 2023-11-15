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
}