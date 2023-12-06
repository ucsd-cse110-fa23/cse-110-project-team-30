package team30.recipeList;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

import org.bson.types.ObjectId;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

//Recipe UI
public class Recipe extends HBox {
    
    String tanLight = "#f1eae0", tanDark = "#ede1cf";
    String pink = "#ead1dc", purple = "#d9d2e9", blue = "#cfe2f3";
    String magenta = "#a64d79", green = "#a64d79";

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
    private String recipe_title_String;
    private String meal_type;
    private String ingredients;
    private ArrayList<String> steps;
    private String imageurl;
    private boolean imageGenerated;
    private String username;
    private RecipeUI recipeUI;
    private ObjectId objectID; //mongodb id

    private Label meal_tag;

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

        steps = new ArrayList<>();
        objectID = new ObjectId();

    }

    public Recipe(String recipe_name, String mealType, String ingredients, ArrayList<String> steps, String imageurl, boolean imageGenerated, String username) {

        this();

        this.recipe_title_String = recipe_name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.meal_type = mealType;
        this.imageurl = imageurl;
        this.imageGenerated = imageGenerated;
        this.username = username;
        setMealTag(meal_type);
    }

    public void setMealTag(String mealType) {
        //meal tag
        boolean newMealTag = false;
        boolean validMealTag = true;
        if (meal_tag == null) {
            newMealTag = true;
            meal_tag = new Label();
            meal_tag.setTextAlignment(TextAlignment.CENTER);
            meal_tag.setPrefSize(100, 40);
            meal_tag.setAlignment(Pos.CENTER);
            //make space
            recipe_title.setPrefSize(300, 40);
        }

        if (mealType.toLowerCase().equals("breakfast")) {
            meal_tag.setText("breakfast");
            meal_tag.setStyle("-fx-background-color: " + pink + "; -fx-background-radius: 20; -fx-border-width: 1.5px; -fx-border-color: black; -fx-border-radius: 20");
        }
        else if (mealType.toLowerCase().equals("lunch")) {
            meal_tag.setText("lunch");
            meal_tag.setStyle("-fx-background-color: " + purple + "; -fx-background-radius: 20; -fx-border-width: 1.5px; -fx-border-color: black; -fx-border-radius: 20");
        }
        else if (mealType.toLowerCase().equals("dinner")) {
            meal_tag.setText("dinner");
            meal_tag.setStyle("-fx-background-color: " + blue + "; -fx-background-radius: 20; -fx-border-width: 1.5px; -fx-border-color: black; -fx-border-radius: 20");
        }
        else {
            System.out.println("ERROR: invalid meal type: [" + mealType + "]");
            validMealTag = false;
        }

        if (validMealTag && newMealTag) {
            this.getChildren().add(meal_tag);
        }

    }

    public void setObjectID(ObjectId id) { this.objectID = id; }
    public ObjectId getObjectID() { return this.objectID; }
    public String getRecipeTitle() { return this.recipe_title_String; }
    public String getMealType() { return this.meal_type; }
    public void setMealType(String meal_type) { this.meal_type = meal_type; }
    public String getIngredients() { return this.ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }
    public ArrayList<String> getSteps() { return this.steps; }
    public String getImageURL() { return this.imageurl; }
    public boolean equals(Recipe other) { return this.getObjectID().equals(other.getObjectID()); }
    public Button getTitleButton() {return recipe_title;}
    public RecipeUI getRecipeUI() {return recipeUI;}
    public void setRecipeUI(RecipeUI UI) {this.recipeUI = UI;}

    public ArrayList<String> getRecipeDetails() {
        ArrayList<String> details = new ArrayList<>();
        details.add(this.getRecipeTitle()); //0 - name
        details.add(this.getMealType()); //1 - meal type
        details.add(this.getIngredients()); //2 - ingredients
        details.add(this.getImageURL()); //3 - imageurl
        for (int i = 0; i < this.getSteps().size(); ++i) { //4 - inf: recipe steps
            details.add(this.getSteps().get(i));
        }
        return details;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    } 

    public boolean getImageGenerated(){
        return this.imageGenerated;
    }

    public void setImageGenerated(boolean imageGenerated){
        this.imageGenerated = imageGenerated;
    }

    public String getName(){
        return this.recipe_title.getText();
    }

    /**
     * Takes an image from the recipe's url, and if the recipe has not had an image generated for it, 
     * generate one to return. It also changes imageGenerated to true to reflect the changes.
     * @return the linked image if generatedImage is true, and a newly generated image if not.
     */
    public Image getImage(){
        imageurl = ImageManager.ensurePathExists(imageurl, this.getName(), !imageGenerated);
        imageGenerated = true;
        return ImageManager.getImage(imageurl);
    }

    public void setRecipeDetails(ArrayList<String> details) {
        if (details.size() < 5) {
            System.out.println("ERROR: recipe details doesn't have the right amount of information");
            return;
        }
        recipe_title_String = details.get(0);
        meal_type = details.get(1);
        ingredients = details.get(2);
        imageurl = details.get(3);
        steps = new ArrayList<>();
        for (int i = 4; i < details.size(); i++) {
            steps.add(details.get(i));
        }
    }
    
} 