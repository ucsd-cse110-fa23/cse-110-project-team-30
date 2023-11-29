package team30.recipeList;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;



public class Recipe extends HBox {

    static String[] validMealTypes = {"Breakfast","Lunch","Dinner"};

    /**
     * Given a string representing a mealType,
     * changes the string to the standard mealType string if it is a valid meal type.
     * Else, it returns null
     * @return a properly capitalized mealType if the given string is valid, and null if not.
     */
    static String validateMealType(String mealType){
        for(String i : validMealTypes){
            if(mealType.toLowerCase().equals(i.toLowerCase()))
                return i;
        }
        return null;
    }

    private Label index;
    private Button recipe_title;
    private String meal_type;
    private String ingredients;
    private ArrayList<String> steps;
    private String imageurl;
    private boolean imageGenerated;

    private String objectID; //mongodb id

    public Recipe() {
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

        ingredients = "example ingredients";
        steps = new ArrayList<>();
        steps.add("step 1...");
        steps.add("step 2...");
        steps.add("step 3...");
        meal_type = "lunch";
        imageurl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSxcSpK0Chn1awF4y-TYCY_BSsBy1psaade2G957ylydxSDx3fVWbSlLtjE2-FXafRVf8E&usqp=CAU"; //random image
        objectID = ""; //will be set if saved
    }

    public Recipe(String recipe_name, String mealType, String ingredients, ArrayList<String> steps, String imageurl, boolean imageGenerated) {
        this();

        this.recipe_title.setText(recipe_name);
        this.ingredients = ingredients;
        this.steps = steps;
        this.meal_type = mealType;
        this.imageurl = imageurl;
        this.imageGenerated = imageGenerated;
    }

    public void setTaskIndex(int num) {
        this.index.setText(num + ""); // num to String
    }

    public void setObjectID(String id) {
        this.objectID = id;
    }

    public String getObjectID() {
        return this.objectID;
    }

    public Button getRecipeTitle() {
        return this.recipe_title;
    }

    public String getMealType() {
        return this.meal_type;
    }

    public void setMealType(String meal_type) {
        this.meal_type = meal_type;
    }

    public String getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(String ingredients){
        this.ingredients = ingredients;
    }

    public ArrayList<String> getSteps() {
        return this.steps;
    }

    public String getImageURL() {
        return this.imageurl;
    }

    public boolean getImageGenerated(){
        return this.imageGenerated;
    }

    public void setImageGenerated(boolean imageGenerated){
        this.imageGenerated = imageGenerated;
    }

    /**
     * Takes an image from the recipe's url, and if the recipe has not had an image generated for it, 
     * generate one to return.
     * @return the linked image if generatedImage is true, and a newly generated image if not.
     */
    public Image getImage(){
        Image recipeImage;
            
        if(!imageGenerated){
            //TODO: If the server stuff gets fixed, change this to use the server
            DallE dallE = new DallE();
            try{
                dallE.generateImage(this.getRecipeTitle().getText(), imageurl);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        
        File file = new File(this.getImageURL());
        URI fileURI = file.toURI();
        recipeImage = new Image(fileURI.toString());
        return recipeImage;
    }

    public boolean equals(Recipe other){
        if(!(this.index.getText().equals(other.index.getText()))) return false;
        if(!(this.ingredients.equals(other.ingredients))) return false;
        if(!(this.recipe_title.getText().equals(other.recipe_title.getText()))) return false;
        if(!this.meal_type.equals(other.meal_type)) return false;
        if(this.steps.size() != other.steps.size()) return false;
        for(int i = 0 ; i < steps.size() ; i++){
            if(!(this.steps.get(i).equals(other.steps.get(i)))) return false;
        }
        return true;
    }
} 