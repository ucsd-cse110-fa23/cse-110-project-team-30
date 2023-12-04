package team30.recipeList;

import org.bson.types.ObjectId;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import team30.server.ChatGPT;
import team30.server.RecipeDatabase;
import team30.server.VoiceRecorder;
import team30.server.VoiceRecorder.RecordingCompletionListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// UI class for entire recipe list sreen
public class RecipeListUI extends DefaultBorderPane /*implements Observer*/ implements RecordingCompletionListener { //implements RecordingCompletionListener
    private VBox recipeListUI;
    private ScrollPane scrollPane;

    private Recipe recipe;
    private Button addButton;
    private RecipeList rl;

    //unseen buttons for HTTP functions
    private Button postButton, getButton, putButton, deleteButton;
    private String query;

    //loaded when server starts
    private RecipeDatabase recipeDB; 
    private List<Recipe> recipeList;

    //voice recorder popup
    VoiceRecorder voiceRecorder;

    public RecipeListUI() {
        recipeListUI = new VBox();
        recipeListUI.setSpacing(10); // sets spacing between tasks   
        recipeListUI.setPrefSize(500, 560);
        recipeListUI.setStyle("-fx-background-color: " + tanLight + ";-fx-padding: 10;");

        scrollPane = new ScrollPane(recipeListUI);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        this.setCenter(scrollPane);

        addButton = new Button("Generate");
        setButtonStyle(addButton);
        addButton.setAlignment(Pos.CENTER_RIGHT);
        header.setSpacing(20);
        header.getChildren().add(addButton);

        postButton = new Button("Post");
        getButton = new Button("Get");
        putButton = new Button("Put");
        deleteButton = new Button("Delete");
        query = "";

        addListeners();
    }

    //updates indices on recipes in list
    public void updateTaskIndices() {
        int index = recipeListUI.getChildren().size();
        for (int i = 0; i < recipeListUI.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof RecipeUI) {
                ((RecipeUI) recipeListUI.getChildren().get(i)).setTaskIndex(index);
                index--;
            }
        }
    }

    public void update() {
        recipeListUI.getChildren().clear();
        for (Recipe recipe : recipeList) {
            RecipeUI recipeUI = new RecipeUI(recipe);
            this.getChildren().add(recipeUI);
        }
    }

    public void getVoiceRecording() {
        voiceRecorder = new VoiceRecorder(rl, this);
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
        // generate recipe with chatGPT
        System.out.println("Recording completed!");
        System.out.println("Meal Type: " + mealType);
        System.out.println("Ingredients: " + ingredientsRaw);

        ChatGPT chatGPT = new ChatGPT();
        try {
            // Generate recipe
            String generatedRecipe = chatGPT.generateRecipe(mealType, ingredientsRaw);
            //System.out.println("Generated Recipe: ");
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

            RecipeDetail tmp = new RecipeDetail(rl, this, cur);
            tmp.setCancellable(true);
            System.out.println("Opening new recipe...");
            tmp.openDetailWindow(cur);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public void addRecipe(Recipe cur) {
        Collections.reverse(recipeList);
        recipeListUI.getChildren().add(cur);
        update();
        this.updateTaskIndices();
        recipe = cur;
        //postButton.fire(); //click HTTP post button

        getRecipeUI(cur).getRecipeTitle().setOnAction(f -> {
            RecipeDetail ord = new RecipeDetail(rl, this, cur);
            ord.openDetailWindow(cur);
        });
        
        Collections.reverse(recipeList);
    }

    public RecipeUI getRecipeUI(Recipe cur) {
        for (int i = 0; i < recipeListUI.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof RecipeUI) {
                String uiID = ((RecipeUI) recipeListUI.getChildren().get(i)).getRecipe().getObjectID().toString();
                if (uiID.equals(cur.getObjectID().toString())) {
                    return (RecipeUI) recipeListUI.getChildren().get(i);
                }
            }
        }
        return null;
    }

    public ObjectId getRecipeObjectID() {
        return recipe.getObjectID();
    }

    public Button getPostButton() {return postButton;}
    public Button getGetButton() {return getButton;}
    public Button getPutButton() {return putButton;}
    public Button getDeleteButton() {return deleteButton;}
    public String getQuery() {return query;}
    public RecipeDatabase getRecipeDB() {return recipeDB;}

    public void setRecipeList(RecipeList rl) {this.rl = rl;}
    public HBox getHeader() {return header;}
    public List<Recipe> getRecipeList() {return recipeList;}
    public ScrollPane getScrollPane() {return scrollPane;}
    public Button getAddButton() {return addButton;}
    public Recipe getRecipe() {return recipe;}
}
