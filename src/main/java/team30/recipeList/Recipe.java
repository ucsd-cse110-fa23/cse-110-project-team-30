package team30.recipeList;

import java.util.ArrayList;

import org.bson.types.ObjectId;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    private ObjectId objectID; //mongodb id

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

    public Recipe(String recipe_name, String mealType, String ingredients, ArrayList<String> steps, String imageurl) {
        this();

        this.recipe_title.setText(recipe_name);
        this.ingredients = ingredients;
        this.steps = steps;
        this.meal_type = mealType;
        this.imageurl = imageurl;
    }

    public void setTaskIndex(int num) {
        this.index.setText(num + ""); // num to String
    }

    public void setObjectID(ObjectId id) {
        this.objectID = id;
    }

    public ObjectId getObjectID() {
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

    public boolean equals(Recipe other){
        return (this.objectID == other.getObjectID());
    }

    public ArrayList<String> getRecipeDetails() {
        ArrayList<String> details = new ArrayList<>();
        details.add(this.getRecipeTitle().getText()); //0 - name
        details.add(this.getMealType()); //1 - meal type
        details.add(this.getIngredients()); //2 - ingredients
        details.add(this.getImageURL()); //3 - imageurl
        for (int i = 0; i < this.getSteps().size(); ++i) { //4 - inf: recipe steps
            details.add(this.getSteps().get(i));
        }
        return details;
    }

    public void setRecipeDetails(ArrayList<String> details) {
        if (details.size() < 5) {
            System.out.println("ERROR: recipe details doesn't have the right amount of information");
        }
        recipe_title.setText(details.get(0));
        meal_type = details.get(1);
        ingredients = details.get(2);
        imageurl = details.get(3);

        steps = new ArrayList<>();

        for (int i = 4; i < details.size(); i++) {
            steps.add(details.get(i));
        }
    }
} 