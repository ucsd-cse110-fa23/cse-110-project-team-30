package team30.recipeList;

import javafx.application.Application;
import javafx.collections.ArrayChangeListener;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
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
import team30.meal.MealType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import javafx.scene.paint.Color;

class Recipe extends HBox {

    private Label index;
    private Button recipe_title;
    private ArrayList<String> ingredients;
    private ArrayList<String> steps;
    private MealType mealtype;

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

        // Adding hover effect
        recipe_title.setOnMouseEntered(e -> recipe_title.setStyle("-fx-background-color: #dccf1e; -fx-border-width: 1.5px; -fx-border-color: black; -fx-background-radius: 10; -fx-border-radius: 10"));
        recipe_title.setOnMouseExited(e -> recipe_title.setStyle("-fx-background-color: #e5da3e; -fx-border-width: 1.5px; -fx-border-color: black; -fx-background-radius: 10; -fx-border-radius: 10"));
        
        // Adding click effect
        recipe_title.setOnMousePressed(e -> recipe_title.setStyle("-fx-background-color: #b4a918; -fx-border-width: 1.5px; -fx-border-color: black; -fx-background-radius: 10; -fx-border-radius: 10"));
        recipe_title.setOnMouseReleased(e -> recipe_title.setStyle("-fx-background-color: #e5da3e; -fx-border-width: 1.5px; -fx-border-color: black; -fx-background-radius: 10; -fx-border-radius: 10"));
        this.getChildren().add(recipe_title);

        ingredients = null;
        steps = null;
        mealtype = null;
    }

    Recipe(String recipe_name, ArrayList<String> ingredients, ArrayList<String> steps, MealType mealType) {
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

        // Adding hover effect
        recipe_title.setOnMouseEntered(e -> recipe_title.setStyle("-fx-background-color: #dccf1e; -fx-border-width: 1.5px; -fx-border-color: black; -fx-background-radius: 10; -fx-border-radius: 10"));
        recipe_title.setOnMouseExited(e -> recipe_title.setStyle("-fx-background-color: #e5da3e; -fx-border-width: 1.5px; -fx-border-color: black; -fx-background-radius: 10; -fx-border-radius: 10"));
        
        // Adding click effect
        recipe_title.setOnMousePressed(e -> recipe_title.setStyle("-fx-background-color: #b4a918; -fx-border-width: 1.5px; -fx-border-color: black; -fx-background-radius: 10; -fx-border-radius: 10"));
        recipe_title.setOnMouseReleased(e -> recipe_title.setStyle("-fx-background-color: #e5da3e; -fx-border-width: 1.5px; -fx-border-color: black; -fx-background-radius: 10; -fx-border-radius: 10"));
        this.getChildren().add(recipe_title);

        // deep copy initializing ingredients
        for (int i = 0; i < ingredients.size(); ++i) {
            this.ingredients.add(new String(ingredients.get(i)));
        }
        
        // deep copy initializing steps
        for (int i = 0; i < steps.size(); ++i) {
            this.steps.add(new String(steps.get(i)));
        }

        this.mealtype = mealType;
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

    private Text titleText;
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
        // Adding hover effect
        addButton.setOnMouseEntered(e -> addButton.setStyle("-fx-font-style: italic; -fx-background-color: #7dedb3;  -fx-font-weight: bold; -fx-font: 15 arial; -fx-background-radius: 10"));
        addButton.setOnMouseExited(e -> addButton.setStyle("-fx-font-style: italic; -fx-background-color: #a1f2c8;  -fx-font-weight: bold; -fx-font: 15 arial; -fx-background-radius: 10"));
        
        // Adding click effect
        addButton.setOnMousePressed(e -> addButton.setStyle("-fx-font-style: italic; -fx-background-color: #117e2c;  -fx-font-weight: bold; -fx-font: 15 arial; -fx-background-radius: 10"));
        addButton.setOnMouseReleased(e -> addButton.setStyle("-fx-font-style: italic; -fx-background-color: #a1f2c8;  -fx-font-weight: bold; -fx-font: 15 arial; -fx-background-radius: 10"));
        this.getChildren().add(addButton);
        this.setAlignment(Pos.CENTER_LEFT);

    }

    public Text getTitleText() {return titleText;}

    public Button getAddButton() {
        return addButton;
    }
}

class AppFrame extends BorderPane {

    private Header header;
    private List recipeList;

    private ScrollPane scrollPane;

    private Button addButton;

    private RecipeList rl;

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
            
            // set button action for open detail windown button
            recipe.getRecipeTitle().setOnAction(f -> {
                OpenRecipeDetail ord = new OpenRecipeDetail();
                ord.openDetailWindow(recipe, this, rl);

                // set button actions for save, edit, back, delete button
                // ord.getSaveButton().setOnAction(g -> {

                // });
                // ord.getBackButton().setOnAction(g -> {

                // });
                // ord.getEditButton().setOnAction(g -> {

                // });
                // ord.getDeleteButton().setOnAction(g -> {

                // });
            });
        });
    }

    public void setRecipeList(RecipeList rl) {this.rl = rl;}
    public Header getHeader() {return header;}
    public List getRecipeList() {return recipeList;}
    public ScrollPane getScrollPane() {return scrollPane;}
    public Button getAddButton() {return addButton;}

    // public void setHeader(Header hd) {header = hd;}
    // public void setRecipeList(List rl) {recipeList = rl;}
    // public void set(Header hd) {header = hd;}
    // public void setHeader(Header hd) {header = hd;}
}

// edited from public class Main
public class RecipeList extends Application {
    private AppFrame root;
    private Stage primStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new AppFrame();
        this.primStage = primaryStage;
        root.setRecipeList(this);
        primaryStage.setTitle("PantryPal");
        primaryStage.setScene(new Scene(root, 500, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Stage getPrimStage() {return primStage;}
    public void setAppFrame(AppFrame af) {root = af;}
}