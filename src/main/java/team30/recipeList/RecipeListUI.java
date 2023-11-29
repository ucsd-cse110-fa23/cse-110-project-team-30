package team30.recipeList;

import org.bson.types.ObjectId;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import team30.recipeList.VoiceRecorder.RecordingCompletionListener;
import team30.server.RecipeDatabase;

import java.util.ArrayList;

/*
 * UI class for list spacing
 */
class ListVBox extends VBox {

    ListVBox() {
        this.setSpacing(10); // sets spacing between tasks   
        this.setPrefSize(500, 560);
    }
    //updates indices on recipes in list
    public void updateTaskIndices() {
        int index = this.getChildren().size();
        for (int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof Recipe) {
                ((Recipe) this.getChildren().get(i)).setTaskIndex(index);
                index--;
            }
        }
    }
    //removes recipe from list
    void removeRecipe(Recipe recipeToRemove){
        this.getChildren().removeIf(recipe -> recipe instanceof Recipe && ((Recipe)recipe).equals(recipeToRemove));
        this.updateTaskIndices();
    }
}

/*
 * UI class for entire recipe list sreen
 */
class RecipeListUI extends DefaultBorderPane implements RecordingCompletionListener {
    private ListVBox recipeList;
    private ScrollPane scrollPane;

    private Recipe recipe;
    private Button addButton;
    private RecipeList rl;

    //unseen buttons for HTTP functions
    private Button postButton, getButton, putButton, deleteButton;
    private String query;

    //loaded by controller when server starts
    private RecipeDatabase recipeDB; 

    //voice recorder popup
    VoiceRecorder voiceRecorder;

    RecipeListUI() {
        recipeList = new ListVBox();
        recipeList.setStyle("-fx-background-color: " + tanLight + ";-fx-padding: 10;");

        scrollPane = new ScrollPane(recipeList);
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

        recipe = new Recipe();
        query = "";

        addListeners();
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
        FXCollections.reverse(recipeList.getChildren());
        recipeList.getChildren().add(cur);
        recipeList.updateTaskIndices();
        recipe = cur;
        //postButton.fire(); //click HTTP post button

        cur.getRecipeTitle().setOnAction(f -> {
            RecipeDetail ord = new RecipeDetail(rl, this, cur);
            ord.openDetailWindow(cur);
        });
        
        FXCollections.reverse(recipeList.getChildren());
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
    public ListVBox getRecipeList() {return recipeList;}
    public ScrollPane getScrollPane() {return scrollPane;}
    public Button getAddButton() {return addButton;}
    public Recipe getRecipe() {return recipe;}
}
