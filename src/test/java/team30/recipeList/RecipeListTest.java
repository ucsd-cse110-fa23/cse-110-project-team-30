package team30.recipeList;

import team30.recipeList.RecipeList;

import org.junit.jupiter.api.Test;
import org.objenesis.instantiator.sun.MagicInstantiator;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class mockRecipe extends HBox {
    private String index;
    private String recipe_title;

    mockRecipe(String s) {
        recipe_title = s;
    }    

    public String getIndex() {return index;}
    public String getRecipeTitle() {return recipe_title;}
    public void setIndex(String s) {index = s;}
}

class mockList extends VBox {
    private mockButton button;

    mockList() {button = new mockButton();}

    public void updateTaskIndices() {
        int index = 1;
        for (int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof mockRecipe) {
                ((mockRecipe)this.getChildren().get(i)).setIndex(Integer.toString(index));
                index++;
            }
        }
    }

    public mockButton getButton() {return button;}
}

class mockButton {
    public void fire(mockList ml, String s) {
        mockRecipe recipe;
        if (s == "") recipe = new mockRecipe("example");
        else recipe = new mockRecipe(s);
        ml.getChildren().add(recipe);
        ml.updateTaskIndices();
        System.out.println(recipe.getRecipeTitle());
    }
}

public class RecipeListTest {
    private mockList recipe_list;

    @BeforeEach
    void setUp() {
        recipe_list = new mockList();
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
    void testRecipeConstructor() {
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
    void testSetUpRecipeTitle() {
        String testTitle = "My Recipe Name";
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
}
