package team30.recipeList;

import team30.recipeList.RecipeList;

import org.junit.jupiter.api.Test;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

// class mockRecipe extends HBox{
//     private Label index;
//     private Button recipe_title;

//     mockRecipe() {
//         index = mock(Label.class);
//         recipe_title = mock(Button.class);
//         this.getChildren().add(index);
//         this.getChildren().add(recipe_title);
//     }

//     public void setTaskIndex(int num) {
//         this.index.setText(num + ""); // num to String
//         // this.contactNameText.setPromptText("Name");
//         // this.emailAddressText.setPromptText("Email address");
//         // this.phoneNumberText.setPromptText("Phone number");
//     }

//     public Button getRecipeTitle() {
//         return this.recipe_title;
//     }
// }

public class RecipeListTest {
    private List recipe_list;

    /**
     * mock method for testing addButton functionality.
     * Create this coz I kept getting initialization error of creating a real button or get button from Header.
     */
    // void mockButton() {
    //     Recipe recipe = new mockRecipeRecipe();
    //     recipe_list.getChildren().add(recipe);
    //     recipe_list.updateTaskIndices();
    // }



    @BeforeEach
    void setUp() {
        recipe_list = new List();
    }

    @Test
    void testListInit() {
        int count = recipe_list.getChildren().size();
        assertEquals(0, count);
    }

    // @Test
    // void testAddButton() {
    //     Recipe recipe = new Recipe("example");
    //     recipe_list.getChildren().add(recipe);
    //     recipe_list.updateTaskIndices();
    //     int count = recipe_list.getChildren().size();
    //     assertEquals(1, count);
    // }

}
