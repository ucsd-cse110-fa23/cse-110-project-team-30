package team30.recipeList;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import team30.mock.*;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


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
        mockRecipe recipe = new mockRecipe();
        recipe_list.getAddButton().fire(recipe_list, recipe);
        int count = recipe_list.getChildren().size();
        assertEquals(1, count);
    }

    @Test
    void testAddMultipleRecipe() {
        int numOfRecipes = 10;
        for (int i = 0; i < numOfRecipes; ++i) {
            mockRecipe recipe = new mockRecipe();
            recipe_list.getAddButton().fire(recipe_list, recipe);
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
        mockRecipe recipe = new mockRecipe();
        recipe_list.getAddButton().fire(recipe_list, recipe);
        String title = "";
        for (int i = 0; i < recipe_list.getChildren().size(); i++) {
            if (recipe_list.getChildren().get(i) instanceof mockRecipe) {
                title = ((mockRecipe)recipe_list.getChildren().get(i)).getRecipeTitle();
                break;
            }
        }
        assertEquals("Example", title);
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
        recipe_list.getAddButton().fire(recipe_list, recipe);
        assertEquals(recipe_name, recipe.getRecipeTitle());
        assertEquals(ingredients, recipe.getIngredients());
        for (int i = 0; i < steps.size(); ++i) {
            assertEquals(steps.get(i), recipe.getSteps().get(i));
        }
        assertEquals(meal_type, recipe.getMealType());
    }

    @Test
    void testHeaderTitle() {
        assertEquals("PantryPal", header.getTitleText());
    }

    @Test void testHeaderInitialization() {
        assertNotEquals(null, header.getAddButton());
    }

    @Test
    void createRecipe() {
        Recipe r = new Recipe();
        r.setMealType("dinner");
        assertNotEquals(r, null);
        assertEquals("dinner", r.getMealType());
    }
    
    // @Test
    // void testValidator(){

    //     String empty = "";
    //     String invalid = "inedible Lunch";
    //     String lunchLower = "lunch";
    //     String breakfastLower = "breakfast";
    //     String dinnerLower = "dinner";
    //     String lunchUpper = "LUNCH";
    //     String breakfastUpper = "BREAKFAST";
    //     String dinnerUpper = "DINNER";

    //     assertEquals(Recipe.validateMealType(empty),null, "Validator doesn't give null if string is empty.");
    //     assertEquals(Recipe.validateMealType(invalid),null, "Validator doesn't give null to an invalid string");
    //     assertEquals(Recipe.validateMealType(lunchLower),"Lunch","Validator doesn't correctly validate lowercase lunch");
    //     assertEquals(Recipe.validateMealType(dinnerLower),"Dinner","Validator doesn't correctly validate lowercase dinner");
    //     assertEquals(Recipe.validateMealType(breakfastLower),"Breakfast","Validator doesn't correctly validate lowercase breakfast");
    //     assertEquals(Recipe.validateMealType(lunchUpper),"Lunch","Validator doesn't correctly validate uppercase lunch");
    //     assertEquals(Recipe.validateMealType(dinnerUpper),"Dinner","Validator doesn't correctly validate uppercase dinner");
    //     assertEquals(Recipe.validateMealType(breakfastUpper),"Breakfast","Validator doesn't correctly validate upcase breakfast");
    // }
    
}
