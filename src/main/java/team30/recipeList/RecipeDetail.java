package team30.recipeList;

import javafx.application.Application;
import javafx.scene.control.ComboBox;
import javafx.collections.ArrayChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
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
import java.util.List;

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
    // private TextField recipe_name;
    // private TextField ingredients;
    // private ArrayList<TextField> steps;
    // private MealType mealtype;

    DetailRecipe (Recipe recipe) {
        this.setPrefSize(500, 560); // sets size of task
        this.setMaxHeight(VBox.USE_PREF_SIZE); 
        this.setMinHeight(VBox.USE_PREF_SIZE);
        this.setSpacing(15);
        this.setStyle("-fx-background-color: #f8f3c9;");

        // initia recipe info
        Label recipe_name = new Label(recipe.getRecipeTitle().getText());
        Label ingredients = new Label(recipe.getIngredients().getText());
        ArrayList<Label> steps = new ArrayList<>();
        for (int i = 0; i < recipe.getSteps().size(); ++i) {
            steps.add(new Label(recipe.getSteps().get(i).getText()));
        }
        Label mealtype = new Label(recipe.getMealType());

        // dropdown menu
        // ComboBox<String> mealtype = new ComboBox<>();
        // mealtype.setPromptText(recipe.getMealType().getMealType());
        // mealtype.getItems().addAll("Breakfast", "Lunch", "Dinner");

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
        ingredients.setText("Ingredients: " + ingredients.getText());  

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


            individual_step_HBox.getChildren().add(index);
            individual_step_HBox.getChildren().add(steps.get(i));

            steps_VBox.getChildren().add(individual_step_HBox);
        }

        this.getChildren().add(title_mealtype_HBox);
        this.getChildren().add(ingredients_HBox);
        this.getChildren().add(steps_VBox);
    }
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

    RecipeDetail(RecipeList rl, AppFrame af) {
        this.rl = rl;
        // store ingredients
        //adds new ingredient
        // this.ingredients.add(new Ingredient("carrot"));
        // this.ingredients.add(new Ingredient("onion"));
        // this.ingredients.add(new Ingredient("broccoli"));
        //this.ingredient.add(new Ingredient("carrot"));  
         //this.ingredients.add(new Ingredient("rice"));
       
        
        this.ingredients.clear();
        
        updateRecipeIngredients();

        
        originalAF = new AppFrame(af.getHeader(), af.getRecipeList(), af.getScrollPane(), af.getAddButton(), rl);
    };

    public void openDetailWindow(Recipe recipe) {
        AppFrame af = createDetailView(recipe);
        
        rl.getPrimStage().setScene(new Scene(af, 500, 600));
    }

    public void closeDetailWindow() {
        rl.getPrimStage().setScene(new Scene(originalAF,500, 600));
    }

    private AppFrame createDetailView(Recipe recipe) {
        AppFrame detailView = new AppFrame();
        DetailHeader dhead = new DetailHeader();
        DetailFooter dfooter = new DetailFooter(); 
        ScrollPane scrollPane = new ScrollPane(new DetailRecipe(recipe));
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        
        detailView.setTop(dhead);
      /*
        // TODO: uncomment this to test Detail Recipe
        // detailView.setCenter(new DetailRecipe(recipe));

       //adds all ingredients to the screen
         VBox vb = new VBox();
        for (int i = 0; i < this.ingredients.size(); i++) {
           vb.getChildren().add(this.ingredients.get(i));
        }
        detailView.setCenter(vb);*/
        detailView.setCenter(scrollPane);
        detailView.setBottom(dfooter);

        addListeners(dfooter.getBack(), dfooter.getSave(), dfooter.getEdit(), dfooter.getDelete());
        
        return detailView;
    }

    public AppFrame getOriginalAppFrame() {return originalAF;}

    public void saveIngredient() {

        //first clear the csv before saving what is on the screen
        // clear the contents of the CSV file
        try {
            java.io.FileWriter outfile = new java.io.FileWriter("/Users/leahkuruvila/Desktop/pantrypal/cse-110-project-team-30/src/main/java/team30/recipeList/ingredient.csv", false);
            outfile.close();
        } catch (Exception e) {
            e.getStackTrace();
        }

        //save the ingredients on the screen to the csv
        try {
            java.io.FileWriter outfile2 = new java.io.FileWriter("/Users/leahkuruvila/Desktop/pantrypal/cse-110-project-team-30/src/main/java/team30/recipeList/ingredient.csv", true); //true = append
            for (int i = 0; i < this.ingredients.size(); i++) {   // Iterates through each contact
                if (this.ingredients.get(i) instanceof Ingredient) {
                    String line1 = ((Ingredient) this.ingredients.get(i)).getIngredient().getText();   // ingredient
                    outfile2.write(line1 + " " + "\n");
                }
            }
            outfile2.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void updateRecipeIngredients() {

        //clear the ingredient arraylist
        this.ingredients.clear();

        //create new array list
        ArrayList<Ingredient> recipeIngredients = new ArrayList<Ingredient>();

        //iterate through the csv file that contains saved ingredients and add each ingredient to the arraylist
        try {
            BufferedReader br = new BufferedReader(new FileReader("/Users/leahkuruvila/Desktop/pantrypal/cse-110-project-team-30/src/main/java/team30/recipeList/ingredient.csv"));
            String line = "";
            while ((line = br.readLine()) != null) {
                //creates a string array of each ingredient
                String[] curr_ingredient = line.split(",");

                //adds each ingredient to the arraylist
                for(String current : curr_ingredient) {
                    recipeIngredients.add(new Ingredient(current));
                }
                
            }
            br.close();
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        //iterate through array list from recipe ingredient and add it to the screen
        for (int i = 0; i < recipeIngredients.size(); i++) {
            Ingredient ingredient = recipeIngredients.get(i);
            this.ingredients.add(new Ingredient(ingredient.getIngredient().getText()));
            //System.out.println(ingredient);
        }

    }




    public void addListeners(Button back, Button save, Button edit, Button delete) {
        // listener for Back
        back.setOnAction(e -> {
            closeDetailWindow();
        });

        // TODO: listener for save
        save.setOnAction(e -> {
        // for (Ingredient ingredient : ingredients) {
        //     ingredient.saveIngredient();
        // }
        saveIngredient();
        });

        // TODO: listener for edit

        // TODO: listener for delete
    }
}
