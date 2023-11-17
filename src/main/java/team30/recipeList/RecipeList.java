package team30.recipeList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.ArrayChangeListener;
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

    /**
     * Removes the recipe from the list.
     */
    void removeRecipe(Recipe recipeToRemove){
        this.getChildren().removeIf(recipe -> recipe instanceof Recipe && ((Recipe)recipe).equals(recipeToRemove));
        this.updateTaskIndices();
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

        addButton = new Button("Generate");
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

    private Recipe recipe;

    private Button addButton;
    private RecipeList rl;

    //unseen buttons for HTTP functions
    private Button postButton, getButton, putButton, deleteButton;

    private String query;

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

        recipe = new Recipe();
        query = "";
        

        loadRecipes();
        addListeners();
    }

    AppFrame(Header hd, List ls, ScrollPane sp, Recipe recipe, Button button, RecipeList rl, Button postButton, Button getButton, Button putButton, Button deleteButton) {
        this();

        header = hd;
        recipeList = ls;
        scrollPane = sp;
        addButton = button;
        this.recipe = recipe;
        this.rl = rl;

        this.postButton = postButton;
        this.getButton = getButton;
        this.putButton = putButton;
        this.deleteButton = deleteButton;

        addListeners();
    }

    public void addListeners() {
        addButton.setOnAction(e -> {
            Recipe recipe = new Recipe();
            recipeList.getChildren().add(recipe);
            recipeList.updateTaskIndices();
            postButton.fire(); //click HTTP post button

            
            // set button action for open detail windown button
            recipe.getRecipeTitle().setOnAction(f -> {
                RecipeDetail ord = new RecipeDetail(rl, this, recipe);
                ord.openDetailWindow(recipe);
            });
        });
    }

    public void loadRecipes() {
        try {
            FileReader fr = new FileReader("src\\main\\java\\team30\\recipeList\\recipes.csv");
            BufferedReader br = new BufferedReader(fr);
            String str = br.readLine(); //Recipe,Meal Type,Ingredients,Steps header line
            while (br.ready()) {
                str = br.readLine();

                String recipeName = "";
                String mealType = "";
                String ingredients;
                ArrayList<String> steps = new ArrayList<String>();
                String imageurl = "";


                int count = 0;
                int stepcounter = 3;
                String ingredientsText = "", stepsText = "";
                for (int i = 0; i < str.length(); i++) {
                    if (str.substring(i, i+1).equals(";")) {
                        count++;
                    }
                    else if (count == 0) {
                        recipeName += str.substring(i, i+1);
                    }
                    else if (count == 1) {
                        mealType += str.substring(i, i+1);
                    }
                    else if (count == 2) {
                        ingredientsText += str.substring(i, i+1);
                    }
                    else if (count >= 3) {
                        if (stepcounter == count) {
                            stepsText += str.substring(i, i+1);
                        }
                        else {
                            //add prev step
                            steps.add(stepsText);
                            stepsText = "";
                            stepsText += str.substring(i, i+1);
                            stepcounter++;
                        }
                    }
                    else {
                        System.out.println("ERROR: invalid semicolon count!");
                    }
                }
                //add prev step
                steps.add(stepsText);
                ingredients = ingredientsText;

                
                Recipe cur = new Recipe(recipeName, mealType, ingredients, steps, imageurl);
                recipeList.getChildren().add(cur);

                
                recipeList.updateTaskIndices();
                postButton.fire(); //click HTTP post button

                cur.getRecipeTitle().setOnAction(f -> {
                    RecipeDetail ord = new RecipeDetail(rl, this, cur);
                    ord.openDetailWindow(cur);
                });
            }
            fr.close();
            br.close();
        }
        catch (Exception e) {
            System.out.println("no 'recipes.csv' file found!");
        }
    }

    public String getRecipeName() {
        return recipe.getRecipeTitle().getText();
    }

    public String[] getRecipeDetails() {
        String[] details = new String[3];
        details[0] = recipe.getMealType();
        details[1] = recipe.getIngredients();
        details[2] = "";
        for (int i = 0; i < recipe.getSteps().size(); ++i) {
            details[2] += recipe.getSteps().get(i);
        }
        return details;
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

    public String getQuery() {
        return query;
    }

    public void setRecipeList(RecipeList rl) {this.rl = rl;}
    public Header getHeader() {return header;}
    public List getRecipeList() {return recipeList;}
    public ScrollPane getScrollPane() {return scrollPane;}
    public Button getAddButton() {return addButton;}
    public Recipe getRecipe() {return recipe;}
}

// edited from public class Main
public class RecipeList extends Application {
    private AppFrame root;
    private Stage primStage;
    private Button postButton, getButton, putButton, deleteButton;
    Controller controller;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new AppFrame();
        Model model = new Model();
        
        postButton = root.getPostButton();
        getButton = root.getGetButton();
        putButton = root.getPutButton();
        deleteButton = root.getDeleteButton();
        
        controller = new Controller(this, model);

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

    public String getQuery() {
        return root.getQuery();
    }

    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}