package team30.recipeDetail;

import team30.recipeList.RecipeDetail;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Files;

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

    public void fire(mockRecipeDetail rl) {rl.saveRecipe();}
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

class mockRecipeDetail {
    private String originalAF;
    private String currentScene;
    private mockDetailFooter footer;
    private mockDetailRecipe dRecipe;

    mockRecipeDetail(String af) {
        footer = new mockDetailFooter();
        originalAF = af;
        currentScene = originalAF;
        dRecipe = new mockDetailRecipe(new mockRecipe());
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

    public mockDetailRecipe getDRecipe() {return dRecipe;}

    public String getAppFrame() {return currentScene;}

    public void saveRecipe() {
        String recipe_title = dRecipe.getDetailRecipeName();
        String meal_type = dRecipe.getMealType();
        String ingredients = dRecipe.getIngredients();
        ArrayList<String> steps = new ArrayList<String>();
        for (int i = 0; i < dRecipe.getSteps().size(); ++i) {
            steps.add(dRecipe.getSteps().get(i));
        }
        
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

