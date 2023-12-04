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
    private String recipe_title;
    private String meal_type;
    private String ingredients;
    private ArrayList<String> steps;
    private String imageurl;

    private ObjectId objectID; //mongodb id

    public Recipe() {
        steps = new ArrayList<>();
        objectID = new ObjectId(); 
    }  

    public Recipe(String recipe_name, String mealType, String ingredients, ArrayList<String> steps, String imageurl) {
        this();

        this.recipe_title = recipe_name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.meal_type = mealType;
        this.imageurl = imageurl;
    }

    public void setObjectID(ObjectId id) { this.objectID = id; }
    public ObjectId getObjectID() { return this.objectID; }
    public String getRecipeTitle() { return this.recipe_title; }
    public String getMealType() { return this.meal_type; }
    public void setMealType(String meal_type) { this.meal_type = meal_type; }
    public String getIngredients() { return this.ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }
    public ArrayList<String> getSteps() { return this.steps; }
    public String getImageURL() { return this.imageurl; }
    public boolean equals(Recipe other) { return this.getObjectID().equals(other.getObjectID()); }

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

    public void setRecipeDetails(ArrayList<String> details) {
        if (details.size() < 5) {
            System.out.println("ERROR: recipe details doesn't have the right amount of information");
            return;
        }
        recipe_title = details.get(0);
        meal_type = details.get(1);
        ingredients = details.get(2);
        imageurl = details.get(3);
        steps = new ArrayList<>();
        for (int i = 4; i < details.size(); i++) {
            steps.add(details.get(i));
        }
    }
    
} 