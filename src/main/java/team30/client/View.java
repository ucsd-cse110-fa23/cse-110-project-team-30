package team30.client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class View extends HBox{
    // private TextField languageField, yearField, queryField;
    // private Button postButton, getButton, putButton, deleteButton;
    // private GridPane grid;
    private Label index;
    private Button recipe_title;

    public View(String recipe_name){
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

    private Button addButton;

    AppFrame() {
        header = new Header();
        recipeList = new List();

        scrollPane = new ScrollPane(recipeList);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        this.setTop(header);
        this.setCenter(scrollPane);

        addButton = header.getAddButton();

        addListeners();
    }

    public void addListeners() {
        addButton.setOnAction(e -> {
            Recipe recipe = new Recipe("example");
            recipeList.getChildren().add(recipe);
            recipeList.updateTaskIndices();
        });
    }
}
}
    // public View() {
    //     grid = new GridPane();
    //     grid.setPadding(new Insets(20, 20, 20, 20));
    //     grid.setHgap(10);
    //     grid.setVgap(10);

    //     Label languageLabel = new Label("Language:");
    //     languageField = new TextField();
    //     grid.add(languageLabel, 0, 0);
    //     grid.add(languageField, 1, 0);

    //     Label yearLabel = new Label("Year:");
    //     yearField = new TextField();
    //     grid.add(yearLabel, 0, 1);
    //     grid.add(yearField, 1, 1);

    //     Label queryLabel = new Label("Query:");
    //     queryField = new TextField();
    //     grid.add(queryLabel, 0, 2);
    //     grid.add(queryField, 1, 2);

    //     postButton = new Button("Post");
    //     getButton = new Button("Get");
    //     putButton = new Button("Put");
    //     deleteButton = new Button("Delete");

    //     grid.add(postButton, 0, 3);
    //     grid.add(getButton, 1, 3);
    //     grid.add(putButton, 0, 4);
    //     grid.add(deleteButton, 1, 4);

    // }

    // public String getLanguage() {
    //     return languageField.getText();
    // }

    // public String getYear() {
    //     return yearField.getText();
    // }

    // public String getQuery() {
    //     return queryField.getText();
    // }

    // public GridPane getGrid() {
    //     return grid;
    // }

    // public void setPostButtonAction(EventHandler<ActionEvent> eventHandler) {
    //     postButton.setOnAction(eventHandler);
    // }

    // public void setGetButtonAction(EventHandler<ActionEvent> eventHandler) {
    //     getButton.setOnAction(eventHandler);
    // }

    // public void setPutButtonAction(EventHandler<ActionEvent> eventHandler) {
    //     putButton.setOnAction(eventHandler);
    // }

    // public void setDeleteButtonAction(EventHandler<ActionEvent> eventHandler) {
    //     deleteButton.setOnAction(eventHandler);
    // }

    // public void showAlert(String title, String content) {
    //     Alert alert = new Alert(Alert.AlertType.INFORMATION);
    //     alert.setTitle(title);
    //     alert.setHeaderText(null);
    //     alert.setContentText(content);
    //     alert.showAndWait();
    // }