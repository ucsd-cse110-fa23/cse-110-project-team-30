package team30.recipeList;

import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import javafx.geometry.Rectangle2D;
import java.io.*;
import java.net.URI;
import java.nio.file.StandardCopyOption;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.util.Pair;
import team30.server.RecipeDatabase;

import java.util.ArrayList;

class DetailFooter extends HBox {

    private Button edit;
    private Button delete;
    private Button save;
    private Button back;

    private Button cancel;

    String tanLight = "#f1eae0", tanDark = "#ede1cf";
    String pink = "#ead1dc", purple = "#d9d2e9", blue = "#cfe2f3";
    String magenta = "#a64d79", green = "#a64d79";

    DetailFooter() {
        super();
        this.setPrefSize(500, 40);

        edit = new Button("Edit");
        delete = new Button("Delete");
        save = new Button("Save");
        back = new Button("Back");
        cancel = new Button("Cancel");
        this.setButtonStyle(back);
        this.setButtonStyle(edit);
        this.setButtonStyle(delete);
        this.setButtonStyle(save);
        this.setButtonStyle(cancel);
        cancel.setVisible(false);

        this.setSpacing(10);

        this.getChildren().addAll(back, edit, delete, save, cancel);
        this.setAlignment(Pos.CENTER_RIGHT);
        
        this.setStyle("-fx-background-color: " + tanDark);
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

    public Button getEdit() {return edit;}
    public Button getDelete() {return delete;}
    public Button getSave() {return save;}
    public Button getBack() {return back;}
    public Button getCancel() {return cancel;}
}

class DetailRecipe extends VBox {
    // recipe info
    private Label recipe_name;
    private TextArea ingredients;
    private ArrayList<TextArea> steps;
    private TextField mealtype;
    private Image recipeImage;

    String tanLight = "#f1eae0", tanDark = "#ede1cf";
    String pink = "#ead1dc", purple = "#d9d2e9", blue = "#cfe2f3";
    String magenta = "#a64d79", green = "#a64d79";

    DetailRecipe (Recipe recipe) {
        this.setPrefSize(500, 800); // sets size of task
        this.setMaxHeight(VBox.USE_PREF_SIZE); 
        this.setMinHeight(VBox.USE_PREF_SIZE);
        this.setSpacing(15);
        this.setStyle("-fx-background-color: " + tanLight);

        // initial recipe info
        recipe_name = new Label(recipe.getRecipeTitle());
        ingredients = new TextArea(recipe.getIngredients());
        steps = new ArrayList<>();
        for (int i = 0; i < recipe.getSteps().size(); ++i) {
            steps.add(new TextArea(recipe.getSteps().get(i)));
        }
        mealtype = new TextField(recipe.getMealType());

        // adding first recipe_name & mealtype row
        HBox title_mealtype_HBox = new HBox();
        title_mealtype_HBox.setPrefSize(450, 60); // sets size of task
        title_mealtype_HBox.setMaxHeight(HBox.USE_PREF_SIZE); 
        title_mealtype_HBox.setMinHeight(HBox.USE_PREF_SIZE);
        title_mealtype_HBox.setSpacing(15);
        title_mealtype_HBox.setAlignment(Pos.BASELINE_CENTER);

        recipe_name.setPrefSize(315, 40);
        recipe_name.setStyle("-fx-background-color: #e5da3e; -fx-border-width: 1.5px; -fx-border-insets: 5, -fx-background-insets: 5; -fx-border-color: black; -fx-background-radius: 10; -fx-border-radius: 10;-fx-font-size: 20;-fx-alignment: CENTER");
        mealtype.setStyle("-fx-background-color: #EDDCF0; -fx-background-radius: 20;-fx-alignment: CENTER;-fx-font-size: 20");
        mealtype.setPrefSize(120, 30);
        
        title_mealtype_HBox.getChildren().add(recipe_name);
        title_mealtype_HBox.getChildren().add(mealtype);


        //Add second row: Image
        ImageView imageView = new ImageView();
        recipeImage = recipe.getImage();
        imageView.setImage(recipeImage);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(false);
        VBox image_VBox = new VBox(imageView);
        
        image_VBox.setPrefSize(200, 200); // sets size of task
        image_VBox.setMaxHeight(VBox.USE_PREF_SIZE);
        image_VBox.setMinHeight(VBox.USE_PREF_SIZE);
        image_VBox.setSpacing(15);
        image_VBox.setAlignment(Pos.CENTER);

        // adding third row: ingredients
        HBox ingredients_HBox = new HBox();
        ingredients_HBox.setPrefSize(450, 70);
        ingredients_HBox.setMaxHeight(HBox.USE_PREF_SIZE); 
        ingredients_HBox.setMinHeight(HBox.USE_PREF_SIZE);
        ingredients_HBox.setSpacing(20);
        ingredients_HBox.setAlignment(Pos.BOTTOM_LEFT);
        
        Label ingredients_title = new Label("Ingredients");
        ingredients_title.setStyle("-fx-background-color: #CDFADC; -fx-background-radius: 20; -fx-alignment: CENTER;-fx-font-size: 20");
        ingredients_title.setPrefSize(130, 40);

        ingredients.setPrefSize(300, 60);
        ingredients.setStyle("-fx-padding: 0 0 0 20;-fx-background-color: #e5da3e; -fx-border-width: 1px; -fx-border-color: black; -fx-background-radius: 10; -fx-border-radius: 10;-fx-font-size: 13;-fx-alignment: TOP_LEFT;");
        ingredients.setWrapText(true); 

        ingredients_HBox.getChildren().add(ingredients_title);
        ingredients_HBox.getChildren().add(ingredients);
        
        // adding steps
        VBox steps_VBox = new VBox();
        steps_VBox.setPrefSize(450, 460); // sets size of task
        steps_VBox.setMaxHeight(VBox.USE_PREF_SIZE); 
        steps_VBox.setMinHeight(VBox.USE_PREF_SIZE);
        steps_VBox.setSpacing(15);
        Label steps_title = new Label("Steps");
        steps_title.setStyle("-fx-background-color: #CDF7FA; -fx-background-radius: 20; -fx-alignment: CENTER;-fx-font-size: 20");
        steps_title.setPrefSize(80, 40);
        steps_VBox.getChildren().add(steps_title);
        
        int index_num = 1;
        for (int i = 0; i < steps.size(); ++i, index_num++) {
            HBox individual_step_HBox = new HBox();
            individual_step_HBox.setPrefSize(400, 30);
            individual_step_HBox.setMaxHeight(HBox.USE_PREF_SIZE); 
            individual_step_HBox.setMinHeight(HBox.USE_PREF_SIZE);
            individual_step_HBox.setSpacing(20);
            individual_step_HBox.setAlignment(Pos.CENTER);

            // index number
            Label index = new Label();
            index.setStyle("-fx-background-color: #e5da3e; -fx-background-radius: 20");
            index.setText(String.valueOf(index_num)); // create index label
            index.setPrefSize(50, 30); // set size of Index label
            index.setTextAlignment(TextAlignment.CENTER); // Set alignment of index label
            index.setAlignment(Pos.CENTER);
            index.setPadding(new Insets(0, 0, 0, 0)); // adds some padding to the task

            steps.get(i).setPrefSize(350, 30);
            steps.get(i).setStyle("-fx-padding: 0 0 0 20;-fx-background-color: #e5da3e; -fx-border-width: 1px; -fx-border-color: black; -fx-background-radius: 10; -fx-border-radius: 10;-fx-font-size: 12;-fx-alignment: TOP_LEFT;");
            steps.get(i).setWrapText(true);
            steps.get(i).setMouseTransparent(true);
            steps.get(i).setEditable(false);


            individual_step_HBox.getChildren().add(index);
            individual_step_HBox.getChildren().add(steps.get(i));

            steps_VBox.getChildren().add(individual_step_HBox);
        }

        this.getChildren().add(title_mealtype_HBox);
        //add image
        this.getChildren().add(image_VBox);
        this.getChildren().add(ingredients_HBox);
        this.getChildren().add(steps_VBox);
        this.setSpacing(10);
    }

    public Label getRecipeName() {return recipe_name;}
    public TextArea getIngredients() {return ingredients;}
    public ArrayList<TextArea> getSteps() {return steps;}
    public TextField getMealType() {return mealtype;}
}

class Ingredient extends HBox {
    private TextField ingredient;

    Ingredient(String string) {
        ingredient = new TextField(string);
        ingredient.setPrefSize(250, 20); // set size of text field
        ingredient.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // set background color of texfield
        //index.setTextAlignment(TextAlignment.LEFT); // set alignment of text field
        ingredient.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        this.getChildren().add(ingredient); // add textlabel to contact

        this.ingredient.setPromptText("Ingredient");
    }

    public TextField getIngredient() {return ingredient;}

}

public class RecipeDetail {
    private RecipeListUI recipeListUI; //original app frame
    private DefaultBorderPane recipeViewAF; //current app frame
    private RecipeList rl;

    private DetailRecipe dRecipe;
    private Recipe recipe;

    private RecipeDatabase recipeDB;

    private boolean editMode;

    Scene recipeViewScene;

    String tanLight = "#f1eae0", tanDark = "#ede1cf";
    String pink = "#ead1dc", purple = "#d9d2e9", blue = "#cfe2f3";
    String magenta = "#a64d79", green = "#a64d79";

    //HTTP buttons
    private Button postButton, getButton, putButton, deleteButton;
    private WindowChange windowChange;

    DetailFooter dfooter;

    RecipeDetail(RecipeList rl, RecipeListUI af, Recipe r) {
        this.rl = rl;
        this.recipe = r;
        recipeListUI = af;
        recipeDB = recipeListUI.getRecipeDB();
        recipeViewAF = new DefaultBorderPane();
        
        //footer
        dfooter = new DetailFooter(); 
        ScrollPane scrollPane = new ScrollPane(new DetailRecipe(recipe));

        dRecipe = new DetailRecipe(recipe);
        scrollPane = new ScrollPane(dRecipe);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        //recipeViewAF.setTop(header);
        recipeViewAF.setCenter(scrollPane);
        recipeViewAF.setBottom(dfooter);

        addListeners(dfooter.getBack(), dfooter.getSave(), dfooter.getEdit(), dfooter.getDelete(), dfooter.getCancel());

        postButton = recipeListUI.getPostButton();
        getButton = recipeListUI.getGetButton();
        putButton = recipeListUI.getPutButton();
        deleteButton = recipeListUI.getDeleteButton();

        this.disableEdit();

        recipeViewScene = new Scene(recipeViewAF, 500, 600);

        windowChange = new WindowChange();
        windowChange.setRecipeDetail(this);
    };

    public void openDetailWindow(Recipe recipe) {
        windowChange.openWindow(this);
    }

    public Scene getScene() {
        return recipeViewScene;
    }

    public void closeDetailWindow() {
        disableEdit();
        windowChange.closeWindow();
    }

    public void enableEdit() {
        editMode = true;
        ((DetailFooter)recipeViewAF.getBottom()).getEdit().setText("Stop");
        dRecipe.getIngredients().setEditable(true);
        dRecipe.getMealType().setEditable(true);
        for (int i = 0; i < dRecipe.getSteps().size(); ++i) {
            dRecipe.getSteps().get(i).setEditable(true);
            dRecipe.getSteps().get(i).setMouseTransparent(false);
        }
    }

    public void disableEdit() {
        editMode = false;
        ((DetailFooter)recipeViewAF.getBottom()).getEdit().setText("Edit");
        dRecipe.getIngredients().setEditable(false);
        dRecipe.getMealType().setEditable(false);
        for (int i = 0; i < dRecipe.getSteps().size(); ++i) {
            dRecipe.getSteps().get(i).setEditable(false);
            dRecipe.getSteps().get(i).setMouseTransparent(true);
        }
    }

    public void updateRecipeList() {
        recipe.setMealType(dRecipe.getMealType().getText());
        recipe.setIngredients(dRecipe.getIngredients().getText());
        recipe.getSteps().clear();
        for (int i = 0; i < dRecipe.getSteps().size(); ++i) {
            recipe.getSteps().add(dRecipe.getSteps().get(i).getText());
        }
    }

    public void setCancellable(boolean b) { dfooter.getCancel().setVisible(b); }

    /**
     * Method holding what the edit button does.
     */
    public void editEvent(){
        if(editMode)
                disableEdit();
            else
                enableEdit();
    }

    public void deleteEvent(){
        recipeListUI.getRecipeList().remove(this.recipe);
        recipeListUI.update();
        recipeDB.deleteRecipe(this.recipe);
        deleteButton.fire(); //server delete
        closeDetailWindow();
    }

    /**
     * Runs the event that happens whenever save button is pressed.
     * @param showAlert - a flag that says whether to show the alert or not.
     */
    public void saveEvent(boolean showAlert){
        disableEdit();
        updateRecipeList();
        putButton.fire(); //server put
    }

    public Button getEditButton(){
        return ((DetailFooter)recipeViewAF.getBottom()).getEdit();
    }

    public void addListeners(Button back, Button save, Button edit, Button delete, Button cancel) {
        // listener for Back
        back.setOnAction(e -> {
            setCancellable(false);
            closeDetailWindow();
        });
        // listener for save
        save.setOnAction(e -> {
            saveEvent(true);
        });
        // listener for edit
        edit.setOnAction(e -> {
            editEvent();
        });
        // listener for delete
        delete.setOnAction(e -> {
            deleteEvent();
        });
        // listener for cancel
        cancel.setOnAction(e -> {
            //don't save recipe to list
            recipeListUI.getRecipeList().remove(this.recipe);
            recipeListUI.update();
            recipeDB.deleteRecipe(this.recipe);
            closeDetailWindow();
        });
    }
}
