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
    private TextField ingredients;
    private ArrayList<TextField> steps;
    private MealType mealtype;

    Recipe() {
        this.setPrefSize(450, 40); // sets size of task
        this.setMaxHeight(HBox.USE_PREF_SIZE); 
        this.setMinHeight(HBox.USE_PREF_SIZE);
        this.setSpacing(10);

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
        recipe_title = new Button("example");
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

    Recipe(String recipe_name, TextField ingredients, ArrayList<TextField> steps, MealType mealType) {
        this.setPrefSize(450, 40); // sets size of task
        this.setMaxHeight(HBox.USE_PREF_SIZE); 
        this.setMinHeight(HBox.USE_PREF_SIZE);
        this.setSpacing(10);

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
        this.ingredients.setText(ingredients.getText());
        
        // deep copy initializing steps
        for (int i = 0; i < steps.size(); ++i) {
            this.steps.add(new TextField(steps.get(i).getText()));
        }

        this.mealtype = mealType;
    }


    public void setTaskIndex(int num) {
        this.index.setText(num + ""); // num to String
    }

    public Button getRecipeTitle() {
        return this.recipe_title;
    }

    public MealType getMealType() {
        return this.mealtype;
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

        titleText = new Text("PantryPal");
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 40;");
        this.getChildren().add(titleText);
        titleText.setFill(Color.GRAY); // Set the font color
        
        this.setMargin(this.getTitleText(), new Insets(0, 200, 0, 0));

        addButton = new Button("Generate Recipe");
        setButtonStyle(addButton);
        this.getChildren().add(addButton);
        this.setAlignment(Pos.CENTER_LEFT);

    }

    public Text getTitleText() {return titleText;}

    public Button getAddButton() {
        return addButton;
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

    public void setButtonStyle(Button button, String style, String hover_enter, String hover_exist, String click_press, String click_release) {
        button.setStyle(style);

        // Adding hover effect
        button.setOnMouseEntered(e -> button.setStyle(hover_enter));
        button.setOnMouseExited(e -> button.setStyle(hover_exist));
        
        // Adding click effect
        button.setOnMousePressed(e -> button.setStyle(click_press));
        button.setOnMouseReleased(e -> button.setStyle(click_release));
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

    AppFrame(Header hd, List ls, ScrollPane sp, Button button, RecipeList rl) {
        header = hd;
        recipeList = ls;
        scrollPane = sp;
        addButton = button;
        this.rl = rl;

        this.setTop(header);
        this.setCenter(scrollPane);

        addButton = header.getAddButton();

        addListeners();
    }

    public void addListeners() {
        addButton.setOnAction(e -> {
            Recipe recipe = new Recipe();
            recipeList.getChildren().add(recipe);
            recipeList.updateTaskIndices();
            
            // set button action for open detail windown button
            recipe.getRecipeTitle().setOnAction(f -> {
                RecipeDetail ord = new RecipeDetail(rl, this);
                ord.openDetailWindow(recipe);
            });
        });
    }

    public void setRecipeList(RecipeList rl) {this.rl = rl;}
    public Header getHeader() {return header;}
    public List getRecipeList() {return recipeList;}
    public ScrollPane getScrollPane() {return scrollPane;}
    public Button getAddButton() {return addButton;}
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