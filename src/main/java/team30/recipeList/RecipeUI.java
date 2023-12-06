package team30.recipeList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

public class RecipeUI extends HBox {
    String tanLight = "#f1eae0", tanDark = "#ede1cf";
    String pink = "#ead1dc", purple = "#d9d2e9", blue = "#cfe2f3";
    String magenta = "#a64d79", green = "#a64d79";

    Label index;
    Button titleButton;
    Label meal_tag;

    Recipe recipe;

    public RecipeUI(Recipe r) {
        this.setPrefSize(450, 40); // sets size of task
        this.setMaxHeight(HBox.USE_PREF_SIZE); 
        this.setMinHeight(HBox.USE_PREF_SIZE);
        this.setSpacing(10);

        // index number
        index = new Label();
        index.setStyle("-fx-background-color: " + tanDark + "; -fx-background-radius: 20; -fx-border-width: 1.5px; -fx-border-color: black; -fx-border-radius: 20");
        index.setPrefSize(40, 40); // set size of Index label
        index.setTextAlignment(TextAlignment.CENTER); // Set alignment of index label
        index.setAlignment(Pos.CENTER);
        index.setPadding(new Insets(0, 0, 0, 0)); // adds some padding to the task
        this.getChildren().add(index); // add index label to task

        // button
        titleButton = new Button(r.getRecipeTitle());
        titleButton.setPrefSize(400, 40);
        titleButton.setStyle("-fx-background-color: " + tanDark + "; -fx-border-width: 1.5px; -fx-border-color: black; -fx-background-radius: 10; -fx-border-radius: 10");

        // Adding hover effect
        titleButton.setOnMouseEntered(e -> titleButton.setStyle("-fx-background-color: #dabc94; -fx-border-width: 1.5px; -fx-border-color: black; -fx-background-radius: 10; -fx-border-radius: 10"));
        titleButton.setOnMouseExited(e -> titleButton.setStyle("-fx-background-color: " + tanDark + "; -fx-border-width: 1.5px; -fx-border-color: black; -fx-background-radius: 10; -fx-border-radius: 10"));
        
        // Adding click effect
        titleButton.setOnMousePressed(e -> titleButton.setStyle("-fx-background-color: #dabc94; -fx-border-width: 1.5px; -fx-border-color: black; -fx-background-radius: 10; -fx-border-radius: 10"));
        titleButton.setOnMouseReleased(e -> titleButton.setStyle("-fx-background-color: " + tanDark + "; -fx-border-width: 1.5px; -fx-border-color: black; -fx-background-radius: 10; -fx-border-radius: 10"));
        this.getChildren().add(titleButton);

        recipe = r;
        setMealTag(r.getMealType());
    }

    public void setMealTag(String mealType) {
        //meal tag
        boolean newMealTag = false;
        boolean validMealTag = true;
        if (meal_tag == null) {
            newMealTag = true;
            meal_tag = new Label();
            meal_tag.setTextAlignment(TextAlignment.CENTER);
            meal_tag.setPrefSize(100, 40);
            meal_tag.setAlignment(Pos.CENTER);
            //make space
            titleButton.setPrefSize(300, 40);
        }

        if (mealType.toLowerCase().equals("breakfast")) {
            meal_tag.setText("breakfast");
            meal_tag.setStyle("-fx-background-color: " + pink + "; -fx-background-radius: 20; -fx-border-width: 1.5px; -fx-border-color: black; -fx-border-radius: 20");
        }
        else if (mealType.toLowerCase().equals("lunch")) {
            meal_tag.setText("lunch");
            meal_tag.setStyle("-fx-background-color: " + purple + "; -fx-background-radius: 20; -fx-border-width: 1.5px; -fx-border-color: black; -fx-border-radius: 20");
        }
        else if (mealType.toLowerCase().equals("dinner")) {
            meal_tag.setText("dinner");
            meal_tag.setStyle("-fx-background-color: " + blue + "; -fx-background-radius: 20; -fx-border-width: 1.5px; -fx-border-color: black; -fx-border-radius: 20");
        }
        else {
            System.out.println("ERROR: invalid meal type: [" + mealType + "]");
            validMealTag = false;
        }

        if (validMealTag && newMealTag) {
            this.getChildren().add(meal_tag);
        }

    }

    public void setTaskIndex(int num) { this.index.setText(num + ""); }
    public Recipe getRecipe() { return recipe; }
    public Button getRecipeTitle() { return titleButton; }
    public void setRecipe(Recipe r) {this.recipe = r;}
} 