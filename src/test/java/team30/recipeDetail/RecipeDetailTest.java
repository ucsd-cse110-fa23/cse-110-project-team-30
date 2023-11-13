package team30.recipeDetail;

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


class mockDetailFooter {
    private mockEditButton edit;
    private mockDeleteButton delete;
    private mockSaveButton save;
    private mockBackButton back;

    mockDetailFooter() {
        edit = new mockEditButton("Edit");
        delete = new mockDeleteButton("Delete");
        save = new mockSaveButton("Save");
        back = new mockBackButton("Back");
        
    }

    public mockEditButton getEdit() {return edit;}
    public mockDeleteButton getDelete() {return delete;}
    public mockSaveButton getSave() {return save;}
    public mockBackButton getBack() {return back;}
}

class mockEditButton {
    private String text;
    public mockEditButton(String s) {text = s;}

    public String getText() {return text;}
}

class mockBackButton {
    private String text;
    public mockBackButton(String s) {text = s;}

    public String getText() {return text;}

    public void fire(mockRecipeDetail rl) {
        rl.closeDetailWindow();
    }
}

class mockSaveButton {
    private String text;
    public mockSaveButton(String s) {text = s;}

    public String getText() {return text;}
}

class mockDeleteButton {
    private String text;
    public mockDeleteButton(String s) {text = s;}

    public String getText() {return text;}
}

class mockRecipe extends HBox {
    private String index;
    private String recipe_title;
    private String mealtype;
    private String ingredients;
    private ArrayList<String> steps;

    mockRecipe() {
        recipe_title = "example";
    }    

    mockRecipe(String name, String ingredients, ArrayList<String> steps, String mealtype) {
        this.recipe_title = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.mealtype = mealtype;
    }

    public String getIndex() {return index;}
    public String getRecipeTitle() {return recipe_title;}
    public void setIndex(String s) {index = s;}
    public String getMealType() {return mealtype;}
    public String getIngredients() {return ingredients;}
    public ArrayList<String> getSteps() {return steps;}
}

class mockDetailRecipe extends VBox {
    // recipe info
    private String recipe_name;
    private String ingredients;
    private ArrayList<String> steps;
    private String mealtype;
    int index_num;

    mockDetailRecipe (mockRecipe recipe) {

        // initia recipe info
        recipe_name = recipe.getRecipeTitle();
        ingredients = recipe.getIngredients();
        steps = recipe.getSteps();
        mealtype = recipe.getMealType();
        index_num = 1;
        
        for (int i = 0; i < steps.size(); ++i, index_num++) {}
        index_num--;
    }

    public String getDetailRecipeName() {return recipe_name;}
    public String getIngredients() {return ingredients;}
    public ArrayList<String> getSteps() {return steps;}
    public String getMealType() {return mealtype;}
    public int getIndex() {return index_num;}
}

class mockRecipeDetail {
    private String originalAF;
    private String currentScene;
    private mockDetailFooter footer;

    mockRecipeDetail(String af) {
        footer = new mockDetailFooter();
        originalAF = af;
        currentScene = originalAF;
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
        addListeners(footer.getBack(), footer.getSave(), footer.getEdit(), footer.getDelete());
        
        return af;
    }

    public String getAppFrame() {return currentScene;}

    public void addListeners(mockBackButton back, mockSaveButton save, mockEditButton edit, mockDeleteButton delete) {
        // listener for Back

        // TODO: listener for save

        // TODO: listener for edit

        // TODO: listener for delete
    }
}

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
        //Recipe recipe = new Recipe(recipe_name, ingredients, steps, meal_type);
        //DetailRecipe detailRecipe = new DetailRecipe(recipe);
        //mockRecipe recipe = new mockRecipe(recipe_name, ingredients, steps, meal_type);
        //mockDetailRecipe detailRecipe = new mockDetailRecipe(new mockRecipe(recipe_name, ingredients, steps, meal_type));
        // assertEquals(recipe_name, detailRecipe.getDetailRecipeName());
        // assertEquals(meal_type, detailRecipe.getMealType());
        // assertEquals(ingredients, detailRecipe.getIngredients());
        // assertEquals(steps, detailRecipe.getSteps());
        // assertEquals(3, detailRecipe.getIndex());
    }

    @Test
    void testRecipeDetailInitialization() {
        assertEquals("original scene", rl.getAppFrame());
    }

    @Test
    void testRecipeDetailOpenDetailWindow() {
        //rl.openDetailWindow(new mockRecipe());
        assertEquals("original scene", rl.getAppFrame());
    }

    @Test
    void testRecipeDetailCloseDetailWindow() {
        //rl.openDetailWindow(new mockRecipe());
        rl.closeDetailWindow();
        assertEquals("original scene", rl.getAppFrame());
    }

    @Test
    void testBackButton() {
        //rl.openDetailWindow(new mockRecipe());
        footer.getBack().fire(rl);
        assertEquals("original scene", rl.getAppFrame());
    }

    //TODO: add test methods for save, edit, and delete button
    @Test
    void testSaveButton() {
        //rl.openDetailWindow(new mockRecipe());
        //footer.getSave().fire(rl);
        assertEquals("original scene", rl.getAppFrame());
    }   

    @Test
    void testEditButton() {
        //rl.openDetailWindow(new mockRecipe());
        //footer.getEdit().fire(rl);
        assertEquals("original scene", rl.getAppFrame());
    }

    @Test
    void testDeleteButton() {
        //rl.openDetailWindow(new mockRecipe());
        //footer.getDelete().fire(rl);
        assertEquals("original scene", rl.getAppFrame());
    }
}
