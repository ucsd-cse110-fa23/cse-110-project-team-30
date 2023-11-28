package team30.recipeList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.ArrayChangeListener;
import javafx.collections.FXCollections;
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
import team30.account.CreateAccount;
import team30.account.Login;
import team30.recipeList.VoiceRecorder.RecordingCompletionListener;
import team30.server.RecipeDatabase;
import team30.App.App;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import javafx.scene.paint.Color;

import com.mongodb.client.FindIterable;
import org.bson.Document;

class RecipeListCenter extends VBox {

    RecipeListCenter() {
        this.setSpacing(10); // sets spacing between tasks
        
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #f8f3c9;-fx-padding: 10;");
    }

    //updates indices on recipes in list
    public void updateTaskIndices() {
        int index = 1;
        for (int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof Recipe) {
                ((Recipe) this.getChildren().get(i)).setTaskIndex(index);
                index++;
            }
        }
    }

    //removes recipe from list
    void removeRecipe(Recipe recipeToRemove){
        this.getChildren().removeIf(recipe -> recipe instanceof Recipe && ((Recipe)recipe).equals(recipeToRemove));
        this.updateTaskIndices();
    }
}

class RecipeListHeader extends HBox {

    private Text titleText;
    private Button addButton;

    RecipeListHeader() {
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


public class RecipeList extends BorderPane implements RecordingCompletionListener {

    private RecipeListHeader header;
    private RecipeListCenter center;
    private ScrollPane scrollPane;

    private Recipe recipe;
    private Button addButton;
    private App app;

    private RecipeDatabase recipeDB;

    private ArrayList<Recipe> recipes;

    //voice recorder popup
    VoiceRecorder voiceRecorder;

    public RecipeList() {
        header = new RecipeListHeader();
        center = new RecipeListCenter();

        scrollPane = new ScrollPane(center);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        this.setTop(header);
        this.setCenter(scrollPane);

        addButton = header.getAddButton();
        recipe = new Recipe();
        recipeDB = app.getRecipeDB();

        recipes = new ArrayList<>();
        
        loadRecipes();
        addListeners();
    }

    public void getVoiceRecording() {
        System.out.println("starting voice recording...");
        voiceRecorder = new VoiceRecorder(app, this);
        voiceRecorder.setCompletionListener(this);
        voiceRecorder.openDetailWindow();
    }


    public void addListeners() {
        addButton.setOnAction(e -> {
            getVoiceRecording();

            String ingredientsRaw = voiceRecorder.getIngredientAudio();
            String mealtype = voiceRecorder.getMealType();

            if (voiceRecorder.successfulRecording()) {
                onRecordingCompleted(mealtype, ingredientsRaw);
            }
        });  
    }

    public void onRecordingCompleted(String mealType, String ingredientsRaw) {
        // This method will be called when the voice recording is completed
        // generate recipe with chatGPT
        System.out.println("Recording completed!");
        System.out.println("Meal Type: " + mealType);
        System.out.println("Ingredients: " + ingredientsRaw);

        ChatGPT chatGPT = new ChatGPT();
        try {
            // Generate recipe
            String generatedRecipe = chatGPT.generateRecipe(mealType, ingredientsRaw);
            System.out.println("Generated Recipe: ");
            //System.out.println(generatedRecipe);

            String[] lines = generatedRecipe.split("\\r?\\n|\\r");
            String recipeName = "", ingredients = "", imgurl = "";
            ArrayList<String> steps = new ArrayList<>();
            int count = 0; //0- recipeName, 1- ingredients, 2- instructions
            for (int i = 0; i < lines.length; i++) {
                System.out.println(lines[i]);
                if (!(lines[i].replaceAll("\\s", "") == "") && !(lines[i].replaceAll("\\n", "") == "") && count == 0) {
                    //recipeName (unlabelled)
                    recipeName = lines[i].toLowerCase();
                    count = 100;
                }
                if (lines[i].contains("Recipe Name: ")) {
                    //recipeName (labelled)
                    recipeName = lines[i].substring(13).toLowerCase();
                    count = 100;
                }

                if (lines[i].contains("Ingredients:")) {
                    count = 1;
                    continue;
                }
                else if (lines[i].contains("Instructions:")) {
                    count = 2;
                    continue;
                }
                
                if (count == 1) {
                    //ingredients
                    if (!ingredients.equals("") && !(lines[i].replaceAll("\\n", "") == ""))
                        ingredients += ", ";
                    ingredients += lines[i].toLowerCase().replaceAll("-", "");
                }

                if (count == 2) {
                    //steps
                    if (!(lines[i].replaceAll("\\s", "") == "") && !(lines[i].replaceAll("\\n", "") == ""))
                        steps.add(lines[i]);
                }
            }
            
            Recipe cur = new Recipe(recipeName, mealType, ingredients, steps, imgurl);
            addRecipe(cur);

            RecipeDetail tmp = new RecipeDetail(app, this, cur);
            tmp.setCancellable(true);
            System.out.println("OPENING NEW RECIPE...");
            tmp.openDetailWindow(cur);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public void addRecipe(Recipe cur) {
        FXCollections.reverse(center.getChildren());
        center.getChildren().add(cur);
        center.updateTaskIndices();
        app.getPostButton().fire(); //click HTTP post button        
        recipes.add(cur);
        FXCollections.reverse(center.getChildren());
    }

    public void loadRecipes() {
        try {
            long totalRecipes = recipeDB.countDocuments();
            System.out.println("Total recipes: " + totalRecipes);

            FindIterable<Document> iterDoc = recipeDB.find();
            Iterator<Document> it = iterDoc.iterator();
            while (it.hasNext()) {
                Recipe cur = recipeDB.getRecipe(it.next());
                app.getDetailOpenButtons().add(cur.getRecipeTitle());
                addRecipe(cur);
            }
        }
        catch (Exception e) {
            System.out.println("couldn't open database!");
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
    
    public RecipeDatabase getRecipeDB() {return recipeDB;}
    public void setRecipeList(App app) {this.app = app;}
    public RecipeListHeader getRecipeListHeader() {return header;}
    public RecipeListCenter getRecipeListCenter() {return center;}
    public ScrollPane getScrollPane() {return scrollPane;}
    public Button getAddButton() {return addButton;}
    public Recipe getRecipe() {return recipe;}
    public ArrayList<Recipe> getRecipes() {return recipes;}
}