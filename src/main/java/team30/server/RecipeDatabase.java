package team30.server;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

import team30.recipeList.Recipe; 

public class RecipeDatabase {

    MongoDatabase recipeDB;
    MongoCollection<Document> recipesCollection;
    MongoClient mongoClient;

    String uri = "mongodb+srv://lil043:VA4U7rBgvZ0EqlNO@cse110.ltw8f69.mongodb.net/?retryWrites=true&w=majority";

    //default constructor
    public RecipeDatabase() {
        try {
            mongoClient = MongoClients.create(uri);
            recipeDB = mongoClient.getDatabase("recipes_db");
            recipesCollection = recipeDB.getCollection("recipes");
            System.out.println("Success: accessed database");
        }
        catch (Exception e) {
            System.out.println("ERROR: failed to access database");
        }
    }

    //insert recipe into database
    //returns document id
    public String insertRecipe(Recipe r) {
        Document recipe = new Document("_id", new ObjectId());
        //recipe.put("name", r.getRecipeTitle().getText());
        recipe.append("name", r.getRecipeTitle().getText())
                .append("meal_type", r.getMealType())
                .append("ingredients", r.getIngredients())
                .append("steps", stepsToString(r.getSteps()))
                .append("imageurl", r.getImageURL());
        try {
            recipesCollection.insertOne(recipe);
            System.out.println("inserted new recipe!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return recipe.get("_id").toString();
    }

    //gets recipe from document
    public Recipe getRecipe(Document d) {
        String name, meal_type, ingredients, stepsString, imageurl;
        name = d.get("name").toString();
        meal_type = d.get("meal_type").toString();
        ingredients = d.get("ingredients").toString();
        stepsString = d.get("steps").toString();      
        imageurl = d.get("steps").toString();       

        ArrayList<String> steps = stepsFromString(stepsString);

        Recipe r = new Recipe(name, meal_type, ingredients, steps, imageurl);
        r.setObjectID(d.get("_id").toString());
        return r;
    }

    //edits recipe document
    public void editRecipe(Recipe r) {
        String objectID = r.getObjectID();
        if (objectID == "") {
            System.out.println("ERROR: cannot find matching document to edit");
            return;
        }

        Bson filter = eq("_id", new ObjectId(objectID));
        recipesCollection.updateMany(filter,
                Updates.combine(Updates.set("meal_type", r.getMealType()),
                                Updates.set("ingredients", r.getIngredients()),
                                Updates.set("steps", stepsToString(r.getSteps())),
                                Updates.set("imageurl", r.getImageURL())));   
        System.out.println("updated recipe successfully!");

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
                i += 1;
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

    public FindIterable<Document> find() {
        return recipesCollection.find();
    }

}
