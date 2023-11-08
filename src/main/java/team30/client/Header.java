package team30.client;


import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
//import team30.recipeList.controller.RecipeController;

public class Header extends HBox {
    /* 
    private Text titleText;
    private Button addButton;

    public Header(RecipeController controller) {
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #f8f3c9;");

        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #a1f2c8; -fx-font-weight: bold; -fx-font: 15 arial; -fx-background-radius: 10";

        titleText = new Text("PantryPal");
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 40;");
        this.getChildren().add(titleText);
        titleText.setFill(Color.GRAY);

        this.setSpacing(200);

        addButton = new Button("Generate Recipe");
        addButton.setStyle(defaultButtonStyle);

        addButton.setOnAction(e -> controller.handleGenerateRecipe());
        
        this.getChildren().add(addButton);
        this.setAlignment(Pos.CENTER_LEFT);
    }

    public Button getAddButton() {
        return addButton;
    }
    */
}