package team30.recipeList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import javafx.geometry.Rectangle2D;
import java.io.*;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import javafx.scene.paint.Color;

class Recipe extends HBox {

    private Label index;
    private Button recipe_title;

    Recipe(String recipe_name) {
        this.setPrefSize(450, 40); // sets size of task
        this.setSpacing(10);
        // this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 5px; -fx-border-color: black; -fx-border-width: 0; -fx-font-weight: bold; -fx-background-radius: 10"); // sets background
                                                                                                     // color of task

        // index number
        index = new Label();
        index.setStyle("-fx-background-color: #e5da3e; -fx-background-radius: 20");
        index.setText(""); // create index label
        index.setPrefSize(40, 40); // set size of Index label
        index.setTextAlignment(TextAlignment.CENTER); // Set alignment of index label
        index.setAlignment(Pos.CENTER);
        index.setPadding(new Insets(0, 0, 0, 0)); // adds some padding to the task
        this.getChildren().add(index); // add index label to task

        // button
        recipe_title = new Button(recipe_name);
        recipe_title.setPrefSize(400, 40);
        recipe_title.setStyle("-fx-background-color: #e5da3e; -fx-border-width: 1.5px; -fx-border-color: black; -fx-background-radius: 10; -fx-border-radius: 10");
        this.getChildren().add(recipe_title);
    }

    public void setTaskIndex(int num) {
        this.index.setText(num + ""); // num to String
        // this.contactNameText.setPromptText("Name");
        // this.emailAddressText.setPromptText("Email address");
        // this.phoneNumberText.setPromptText("Phone number");
    }

    public Button getRecipeTitle() {
        return this.recipe_title;
    }

}

class List extends VBox {

    List() {
        this.setSpacing(10); // sets spacing between tasks
        
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #f8f3c9;-fx-padding: 10;");
    }

    public void updateTaskIndices() {
        int index = 1;
        for (int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof Recipe) {
                ((Recipe) this.getChildren().get(i)).setTaskIndex(index);
                index++;
            }
        }
    }
}

class Header extends HBox {

    Text titleText;
    private Button addButton;

    Header() {
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #f8f3c9;");

        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #a1f2c8;  -fx-font-weight: bold; -fx-font: 15 arial; -fx-background-radius: 10";

        titleText = new Text("PantryPal");
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 40;");
        this.getChildren().add(titleText);
        titleText.setFill(Color.GRAY); // Set the font color
        
        this.setSpacing(200);   // set spacing between text and generate button

        addButton = new Button("Generate Recipe");
        addButton.setStyle(defaultButtonStyle);
        this.getChildren().add(addButton);
        this.setAlignment(Pos.CENTER_LEFT);


    }

    public Button getAddButton() {
        return addButton;
    }
}

class AppFrame extends BorderPane {

    private Header header;
    private List recipeList;

    private ScrollPane scrollPane;

    private String recipeName;
    private String[] recipeDetails = new String[3];

    private Button addButton;

    //unseen buttons for HTTP functions
    private Button postButton, getButton, putButton, deleteButton;

    AppFrame() {
        header = new Header();
        recipeList = new List();

        scrollPane = new ScrollPane(recipeList);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        this.setTop(header);
        this.setCenter(scrollPane);

        addButton = header.getAddButton();

        postButton = new Button("Post");
        getButton = new Button("Get");
        putButton = new Button("Put");
        deleteButton = new Button("Delete");

        addListeners();
    }

    public void addListeners() {
        addButton.setOnAction(e -> {
            recipeName = "example";
            recipeDetails[0] = "meal type";
            recipeDetails[1] = "ingredients";
            recipeDetails[2] = "steps";
            Recipe recipe = new Recipe(recipeName);
            recipeList.getChildren().add(recipe);
            recipeList.updateTaskIndices();
            postButton.fire(); //click HTTP post button
        });
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String[] getRecipeDetails() {
        return recipeDetails;
    }

    public Button getPostButton() {
        return postButton;
    }

    public Button getGetButton() {
        return getButton;
    }

    public Button getPutButton() {
        return putButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }
}

// edited from public class Main
public class RecipeList extends Application {

    private AppFrame root;
    private Button postButton, getButton, putButton, deleteButton;
    Controller controller;

    private String recipeName;
    private String[] recipeDetails = new String[3];

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new AppFrame();
        Model model = new Model();
        
        postButton = root.getPostButton();
        getButton = root.getGetButton();
        putButton = root.getPutButton();
        deleteButton = root.getDeleteButton();
        
        controller = new Controller(this, model);

        primaryStage.setTitle("PantryPal");
        primaryStage.setScene(new Scene(root, 500, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setPostButtonAction(EventHandler<ActionEvent> eventHandler) {
        postButton.setOnAction(eventHandler);
    }

    public void setGetButtonAction(EventHandler<ActionEvent> eventHandler) {
        getButton.setOnAction(eventHandler);
    }

    public void setPutButtonAction(EventHandler<ActionEvent> eventHandler) {
        putButton.setOnAction(eventHandler);
    }

    public void setDeleteButtonAction(EventHandler<ActionEvent> eventHandler) {
        deleteButton.setOnAction(eventHandler);
    }

    public String getRecipeName() {
        return root.getRecipeName();
    }

    public String[] getRecipeDetails() {
        return root.getRecipeDetails();
    }

    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}