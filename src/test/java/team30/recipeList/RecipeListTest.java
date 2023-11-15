package team30.recipeList;

import team30.recipeList.RecipeList;

import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.objenesis.instantiator.sun.MagicInstantiator;

import javafx.beans.binding.ObjectBinding;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import team30.mock.*;

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


public class RecipeListTest {
    private mockList recipe_list;
    private mockHeader header;

    @BeforeEach
    void setUp() {
        recipe_list = new mockList();
        header = new mockHeader();
    }

    @Test
    void testListInit() {
        int count = recipe_list.getChildren().size();
        assertEquals(0, count);
    }

    @Test
    void testAddButton() {
        recipe_list.getButton().fire(recipe_list, "");
        int count = recipe_list.getChildren().size();
        assertEquals(1, count);
    }

    @Test
    void testAddMultipleRecipe() {
        int numOfRecipes = 10;
        for (int i = 0; i < numOfRecipes; ++i) {
            recipe_list.getButton().fire(recipe_list, "");
        }
        int count = 0;
        for (int i = 0; i < recipe_list.getChildren().size(); i++) {
            if (recipe_list.getChildren().get(i) instanceof mockRecipe) {
                 count++;
            }
        }
        assertEquals(numOfRecipes, count);
    }

    @Test
    void testRecipeDefaultConstructor() {
        recipe_list.getButton().fire(recipe_list, "");
        String title = "";
        for (int i = 0; i < recipe_list.getChildren().size(); i++) {
            if (recipe_list.getChildren().get(i) instanceof mockRecipe) {
                title = ((mockRecipe)recipe_list.getChildren().get(i)).getRecipeTitle();
                break;
            }
        }
        assertEquals("example", title);
    }

    @Test
    void testRecipeConstructorWithParameters() {
        String recipe_name = "Noodle";
        String meal_type = "Dinner";
        String ingredients = "Eggs, noodle, water...";
        ArrayList<String> steps = new ArrayList<>();
        steps.add(new String("Step 1"));
        steps.add(new String("Step 2"));
        steps.add(new String("Step 3"));

        mockRecipe recipe = new mockRecipe(recipe_name, ingredients, steps, meal_type);
        assertEquals(recipe_name, recipe.getRecipeTitle());
        assertEquals(ingredients, recipe.getIngredients());
        for (int i = 0; i < steps.size(); ++i) {
            assertEquals(steps.get(i), recipe.getSteps().get(i));
        }
        assertEquals(meal_type, recipe.getMealType());
    }

    @Test
    void testSetUpRecipeTitle() {
        String testTitle = "example";
        recipe_list.getButton().fire(recipe_list, testTitle);
        String title = "";
        for (int i = 0; i < recipe_list.getChildren().size(); i++) {
            if (recipe_list.getChildren().get(i) instanceof mockRecipe) {
                title = ((mockRecipe)recipe_list.getChildren().get(i)).getRecipeTitle();
                break;
            }
        }
        assertEquals(testTitle, title);
    }

    @Test
    void testHeaderTitle() {
        assertEquals("PantryPal", header.getTitleText());
    }

    @Test void testHeaderInitialization() {
        assertNotEquals(null, header.getAddButton());
    }
}
