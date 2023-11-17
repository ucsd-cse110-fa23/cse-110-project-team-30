package team30.server;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;
import static java.util.Arrays.asList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import javafx.scene.control.TextField;
import team30.recipeList.Recipe; 

public class RecipeDatabase {

    MongoDatabase recipeDB;
    MongoCollection<Document> recipesCollection;

    String uri = "mongodb+srv://lil043:VA4U7rBgvZ0EqlNO@cse110.ltw8f69.mongodb.net/?retryWrites=true&w=majority";

    //default constructor
    public RecipeDatabase() {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase recipeDB = mongoClient.getDatabase("recipes_db");
            MongoCollection<Document> recipesCollection = recipeDB.getCollection("recipes");
        }
        catch (Exception e) {
            System.out.println("ERROR: failed to access database");
        }
    }

    //insert recipe into database
    public void insertRecipe(Recipe r) {
        Document recipe = new Document("_id", new ObjectId());
        recipe.append("name", r.getRecipeTitle())
                .append("meal_type", r.getMealType())
                .append("ingredients", r.getIngredients())
                .append("steps", stepsToString(r.getSteps()))
                .append("imageurl", r.getImageURL());
        recipesCollection.insertOne(recipe);
    }

    //gets recipe by its id in database (for iteration through database)
    public Recipe getRecipe(int id) {
        String name, meal_type, ingredients, stepsString, imageurl;

        Document d = recipesCollection.find(eq("_id", id)).first();
        name = (String) d.get("name");
        meal_type = (String) d.get("meal_type");
        ingredients = (String) d.get("ingredients");
        stepsString = (String) d.get("steps");      
        imageurl = (String) d.get("steps");       

        ArrayList<String> steps = stepsFromString(stepsString);

       return new Recipe(name, meal_type, ingredients, steps, imageurl);
    }

    //convert steps from arraylist form to string form
    String stepsToString(ArrayList<String> steps) {
        String s = "";
        for (String step : steps) {
            if (s != "") 
                s += ";;"; //separate steps with ;;
            s += step;
        }
        return s;
    }

    //convert steps from string form to arraylist form
    ArrayList<String> stepsFromString(String steps) {
        ArrayList<String> al = new ArrayList<>();
        String stepsText = "";
        for (int i = 0; i < steps.length() - 1; i++) {
            if (steps.substring(i, i+2).equals(";;")) {
                i += 2;
                al.add(stepsText);
                stepsText = "";
            }
            else {
                stepsText += steps.substring(i, i+1);
            }
        }
        if (stepsText != "")
            al.add(stepsText);
        return al;
    }

    public long countDocuments() {
        return recipesCollection.countDocuments();
    }

}
