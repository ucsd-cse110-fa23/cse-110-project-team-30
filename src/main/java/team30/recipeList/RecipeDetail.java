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
import java.util.List;

import javafx.scene.paint.Color;

class DetailFooter extends DetailHeader {

    private Button edit;
    private Button delete;

    DetailFooter() {
        super();
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");

        edit = new Button("Edit");
        delete = new Button("Delete");
        this.setButtonStyle(edit);
        this.setButtonStyle(delete);

        this.getChildren().remove(this.getSave());
        this.getChildren().remove(this.getBack());
        this.getChildren().remove(this.getTitleText());
        this.setMargin(delete, new Insets(0, 0, 0, 10));
        this.getChildren().addAll(edit, delete);
        this.setAlignment(Pos.CENTER_RIGHT);
    }

    public Button getEdit() {return edit;}
    public Button getDelete() {return delete;}
}

class DetailRecipe extends Recipe{
    
    DetailRecipe() {

    }

    DetailRecipe (Recipe recipe) {

        ComboBox<String> mealtype = new ComboBox<>();
        mealtype.setPromptText(recipe.getMealType().getMealType());
        mealtype.getItems().addAll("Breakfast", "Lunch", "Dinner");
        HBox title_mealtype = new HBox();
        title_mealtype.getChildren().add(new TextField(recipe.getRecipeTitle().getText()));
        title_mealtype.getChildren().add(mealtype);
        this.getChildren().add(title_mealtype);
    }
}

class DetailHeader extends Header {
    private Button save;
    private Button back;

    DetailHeader() {
        super();
        this.getChildren().remove(this.getAddButton());
        
        this.setMargin(this.getTitleText(), new Insets(0, 210, 0, 0));

        save = new Button("Save");
        back = new Button("Back");
        this.setButtonStyle(save);
        this.setButtonStyle(back);
        
        this.setMargin(save, new Insets(0, 10, 0, 0));

        this.getChildren().addAll(save, back);
        this.setAlignment(Pos.CENTER_LEFT);
    }

    public Button getSave() {return save;}
    public Button getBack() {return back;}
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
        
        detailView.setTop(dhead);
        // TODO: uncomment this to test Detail Recipe
        // detailView.setCenter(new DetailRecipe(recipe));

       //adds all ingredients to the screen
         VBox vb = new VBox();
        for (int i = 0; i < this.ingredients.size(); i++) {
           vb.getChildren().add(this.ingredients.get(i));
        }
        detailView.setCenter(vb);
        detailView.setBottom(dfooter);

        addListeners(dhead.getBack(), dhead.getSave(), dfooter.getEdit(), dfooter.getDelete());
        
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
