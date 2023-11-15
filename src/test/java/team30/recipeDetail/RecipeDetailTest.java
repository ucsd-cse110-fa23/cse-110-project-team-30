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
    
    @BeforeEach
    void setUp() {
        footer = new mockDetailFooter();
        rl = new mockRecipeDetail("original scene");
    }

    @Test
    void testFooterInitialization() {
        assertNotEquals(null, footer.getEdit());
        assertNotEquals(null, footer.getBack());
        assertNotEquals(null, footer.getDelete());
        assertNotEquals(null, footer.getSave());
    }

    @Test
    void testDetailRecipeInitialization() {
        String recipe_name = "Noodle";
        String meal_type = "Dinner";
        String ingredients = "Eggs, noodle, water...";
        ArrayList<String> steps = new ArrayList<>();
        steps.add(new String("Step 1"));
        steps.add(new String("Step 2"));
        steps.add(new String("Step 3"));
        mockRecipe recipe = new mockRecipe(recipe_name, ingredients, steps, meal_type);
        mockDetailRecipe detailRecipe = new mockDetailRecipe(recipe);
        assertEquals(recipe_name, detailRecipe.getDetailRecipeName());
        assertEquals(meal_type, detailRecipe.getMealType());
        assertEquals(ingredients, detailRecipe.getIngredients());
        assertEquals(steps, detailRecipe.getSteps());
        assertEquals(3, detailRecipe.getIndex());
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
        rl.openDetailWindow(new mockRecipe());
        footer.getBack().fire(rl);
        assertEquals("original scene", rl.getAppFrame());
    }

    // TODO: add test methods for save, edit, and delete button
    @Test 
    void testSaveButton() {
        ArrayList<String> steps = new ArrayList<>();
        steps.add("Step 1");
        steps.add("Step 2");
        steps.add("Step 3");
        steps.add("Step 4");
        mockRecipe recipe = new mockRecipe("Recipe name", "Some ingredients", steps, "Breakfast");
        rl.setDRecipe(recipe);
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