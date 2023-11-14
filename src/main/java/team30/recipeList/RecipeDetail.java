package team30.recipeList;

import javafx.application.Application;
import javafx.scene.control.ComboBox;
import javafx.collections.ArrayChangeListener;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import javafx.geometry.Rectangle2D;
import java.io.*;
import java.nio.file.StandardCopyOption;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import javafx.scene.paint.Color;

class DetailFooter extends DetailHeader {

    private Button edit;
    private Button delete;
    private Button save;
    private Button back;

    DetailFooter() {
        super();
        this.setPrefSize(500, 40);
        this.setStyle("-fx-background-color: #f8f3c9;");

        edit = new Button("Edit");
        delete = new Button("Delete");
        save = new Button("Save");
        back = new Button("Back");
        this.setButtonStyle(back);
        this.setButtonStyle(edit);
        this.setButtonStyle(delete);
        this.setButtonStyle(save);

        this.getChildren().remove(this.getSave());
        this.getChildren().remove(this.getBack());
        this.getChildren().remove(this.getTitleText());
        this.setMargin(save, new Insets(0, 0, 0, 10));
        this.setMargin(delete, new Insets(0, 0, 0, 10));
        this.setMargin(edit, new Insets(0, 0, 0, 10));
        this.getChildren().addAll(back, edit, delete, save);
        this.setAlignment(Pos.CENTER_RIGHT);
    }

    public Button getEdit() {return edit;}
    public Button getDelete() {return delete;}
    public Button getSave() {return save;}
    public Button getBack() {return back;}
}

class DetailRecipe extends VBox{
    // recipe info
    private Label recipe_name;
    private Label ingredients;
    private ArrayList<TextArea> steps;
    private Label mealtype;

    DetailRecipe (Recipe recipe) {
        this.setPrefSize(500, 560); // sets size of task
        this.setMaxHeight(VBox.USE_PREF_SIZE); 
        this.setMinHeight(VBox.USE_PREF_SIZE);
        this.setSpacing(15);
        this.setStyle("-fx-background-color: #f8f3c9;");

        // initia recipe info
        recipe_name = new Label(recipe.getRecipeTitle().getText());
        ingredients = new Label(recipe.getIngredients().getText());
        steps = new ArrayList<>();
        for (int i = 0; i < recipe.getSteps().size(); ++i) {
            steps.add(new TextArea(recipe.getSteps().get(i).getText()));
        }
        mealtype = new Label(recipe.getMealType());

        // adding first recipe_name & mealtype row
        HBox title_mealtype_HBox = new HBox();
        title_mealtype_HBox.setPrefSize(450, 40); // sets size of task
        title_mealtype_HBox.setMaxHeight(HBox.USE_PREF_SIZE); 
        title_mealtype_HBox.setMinHeight(HBox.USE_PREF_SIZE);
        title_mealtype_HBox.setSpacing(15);
        title_mealtype_HBox.setAlignment(Pos.BASELINE_CENTER);

        recipe_name.setPrefSize(315, 40);
        recipe_name.setStyle("-fx-background-color: #e5da3e; -fx-border-width: 1.5px; -fx-border-color: black; -fx-background-radius: 10; -fx-border-radius: 10;-fx-font-size: 40;-fx-alignment: CENTER;");

        mealtype.setStyle("-fx-background-color: #EDDCF0; -fx-background-radius: 20;-fx-alignment: CENTER;-fx-font-size: 20");
        mealtype.setPrefSize(120, 30);
        
        title_mealtype_HBox.getChildren().add(recipe_name);
        title_mealtype_HBox.getChildren().add(mealtype);

        // adding second row: ingredients
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
        steps_VBox.setSpacing(5);
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
        this.getChildren().add(ingredients_HBox);
        this.getChildren().add(steps_VBox);
    }

    public Label getRecipeName() {return recipe_name;}
    public Label getIngredients() {return ingredients;}
    public ArrayList<TextArea> getSteps() {return steps;}
    public Label getMealType() {return mealtype;}
}

class DetailHeader extends Header {

    DetailHeader() {
        super();
        this.getChildren().remove(this.getAddButton());
        this.setAlignment(Pos.CENTER_LEFT);
    }
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
        // this.getChildren().addAll(ingredient);

        this.ingredient.setPromptText("Ingredient");
    }

    public TextField getIngredient() {return ingredient;}

}

public class RecipeDetail {
    private AppFrame originalAF;
    private RecipeList rl;
    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

    private DetailRecipe dRecipe;

    private Recipe recipe;

    RecipeDetail(RecipeList rl, AppFrame af, Recipe r) {
        this.rl = rl;
        this.recipe = r;
        originalAF = new AppFrame(af.getHeader(), af.getRecipeList(),  af.getScrollPane(), af.getRecipe(), af.getAddButton(), rl, af.getPostButton(), af.getGetButton(), af.getPutButton(), af.getDeleteButton());
    };

    public void openDetailWindow(Recipe recipe) {
        AppFrame af = createDetailView(recipe);
        
        rl.getPrimStage().setScene(new Scene(af, 500, 600));
    }

    public void closeDetailWindow() {
        rl.getPrimStage().setScene(new Scene(originalAF,500, 600));
    }

    public void enableEdit() {
        for (int i = 0; i < dRecipe.getSteps().size(); ++i) {
            dRecipe.getSteps().get(i).setEditable(true);
            dRecipe.getSteps().get(i).setMouseTransparent(false);
        }
    }

    public void disableEdit() {
        for (int i = 0; i < dRecipe.getSteps().size(); ++i) {
            dRecipe.getSteps().get(i).setEditable(false);
            dRecipe.getSteps().get(i).setMouseTransparent(true);
        }
    }

    private AppFrame createDetailView(Recipe recipe) {
        AppFrame detailView = new AppFrame();
        DetailHeader dhead = new DetailHeader();
        DetailFooter dfooter = new DetailFooter(); 

        ScrollPane scrollPane = new ScrollPane(new DetailRecipe(recipe));
        this.recipe = recipe;

        dRecipe = new DetailRecipe(recipe);
        scrollPane = new ScrollPane(dRecipe);
        // ScrollPane scrollPane = new ScrollPane(new DetailRecipe(recipe));

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        
        detailView.setTop(dhead);
        detailView.setCenter(scrollPane);
        detailView.setBottom(dfooter);

        addListeners(dfooter.getBack(), dfooter.getSave(), dfooter.getEdit(), dfooter.getDelete());
        
        return detailView;
    }

    public AppFrame getOriginalAppFrame() {return originalAF;}

    public void updateRecipeList() {
        recipe.getSteps().clear();
        for (int i = 0; i < dRecipe.getSteps().size(); ++i) {
            recipe.getSteps().add(new TextField(dRecipe.getSteps().get(i).getText()));
        }
    }

    public void saveRecipe() {
        String recipe_title = dRecipe.getRecipeName().getText();
        String meal_type = dRecipe.getMealType().getText();
        String ingredients = dRecipe.getIngredients().getText();
        ArrayList<String> steps = new ArrayList<String>();
        for (int i = 0; i < recipe.getSteps().size(); ++i) {
            steps.add(dRecipe.getSteps().get(i).getText());
        }
        
        try {
            java.io.FileWriter outfile = new java.io.FileWriter("src\\main\\java\\team30\\recipeList\\recipes.csv", true); //true = append
            // Recipe,Meal Type,Ingredients,Steps
            // format with semicolons in between different categories, like recipes.csv in Lab 6
            outfile.write(recipe_title + ";" + meal_type + ";" + ingredients + ";");
            for (String s : steps) {
                outfile.write(s + ";");
            }
            outfile.write("\n");
            outfile.close();
        }
        catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void addListeners(Button back, Button save, Button edit, Button delete) {
        // listener for Back
        back.setOnAction(e -> {
            closeDetailWindow();
        });

        // listener for save
        save.setOnAction(e -> {
            disableEdit();
            updateRecipeList();
            saveRecipe();
        });

        // listener for edit
        edit.setOnAction(e -> {
            enableEdit();
        });

        // listener for delete
        delete.setOnAction(e -> {
            //Kinda sketchy, may need to change if originalAF makes a deep copy of values.
            this.originalAF.getRecipeList().removeRecipe(this.recipe);
            this.originalAF.setQuery(this.recipe.getRecipeTitle().getText());
            this.originalAF.getDeleteButton().fire();
            closeDetailWindow();
        });
    }
}
