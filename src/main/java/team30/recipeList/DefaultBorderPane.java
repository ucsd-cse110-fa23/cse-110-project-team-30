package team30.recipeList;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class DefaultBorderPane extends BorderPane {
    String tanLight = "#f1eae0", tanDark = "#ede1cf";
    String pink = "#ead1dc", purple = "#d9d2e9", blue = "#cfe2f3";
    String magenta = "#a64d79", green = "#a64d79";

    HBox header, footer;
    VBox middle;

    Text titleText;
    String defaultButtonStyle = "-fx-font-style: italic; -fx-padding: 10; -fx-background-insets: 5; -fx-font-weight: bold; -fx-font: 11 arial";

    DefaultBorderPane() {
        header = new HBox();
        titleText = new Text("PantryPal");
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 40; -fx-fill: " + magenta);
        header.getChildren().add(titleText);
        header.setAlignment(Pos.CENTER);
        
        middle = new VBox();
        footer = new HBox();

        this.setStyle("-fx-background-color: " + tanLight);

        this.setTop(header);
        this.setCenter(middle);
        this.setBottom(footer);
    }

    public void setButtonStyle(Button button) {
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #a1f2c8;  -fx-font-weight: bold; -fx-font: 15 arial; -fx-background-radius: 10";
        button.setStyle(defaultButtonStyle);
        // Adding hover effect
        button.setOnMouseEntered(e -> button.setStyle("-fx-font-style: italic; -fx-background-color: #7dedb3;  -fx-font-weight: bold; -fx-font: 15 arial; -fx-background-radius: 10"));
        button.setOnMouseExited(e -> button.setStyle("-fx-font-style: italic; -fx-background-color: #a1f2c8;  -fx-font-weight: bold; -fx-font: 15 arial; -fx-background-radius: 10"));
        
        // Adding click effect
        button.setOnMousePressed(e -> button.setStyle("-fx-font-style: italic; -fx-background-color: #117e2c;  -fx-font-weight: bold; -fx-font: 15 arial; -fx-background-radius: 10"));
        button.setOnMouseReleased(e -> button.setStyle("-fx-font-style: italic; -fx-background-color: #a1f2c8;  -fx-font-weight: bold; -fx-font: 15 arial; -fx-background-radius: 10"));
    }
    
}
