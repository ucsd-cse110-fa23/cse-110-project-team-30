package team30.server;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Updates.set;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
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
    // String uri = "mongodb+srv://m1ren:IHcPb6UPAHtXNQfK@cluster0.nybipuz.mongodb.net/?retryWrites=true&w=majority";

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

    public void upsertRecipe(Recipe r) {
        if (getRecipe(r.getObjectID()) == null) {
            //insert
            System.out.println("inserting recipe...");
            insertRecipe(r);
        }
        else {
            //edit
            System.out.println("editing recipe...");
            editRecipe(r);
        }
    }

    //insert recipe into database
    public void insertRecipe(Recipe r) {
        Document recipe = new Document("_id", r.getObjectID());
        //recipe.put("name", r.getRecipeTitle().getText());
        recipe.append("name", r.getRecipeTitle())
                .append("username", r.getUsername())
                .append("meal_type", r.getMealType())
                .append("ingredients", r.getIngredients())
                .append("steps", stepsToString(r.getSteps()))
                .append("imageurl", r.getImageURL())
                .append("username", r.getUsername());
        try {
            recipesCollection.insertOne(recipe);
            System.out.println("inserted new recipe!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //gets recipe from document
    public Recipe getRecipe(Document d, String username) {
        String name, meal_type, ingredients, stepsString, imageurl;
        name = d.get("name").toString();
        meal_type = d.get("meal_type").toString();
        ingredients = d.get("ingredients").toString();
        stepsString = d.get("steps").toString();      
        imageurl = d.get("imageurl").toString();       
        ArrayList<String> steps = stepsFromString(stepsString);
        Recipe r = new Recipe(name, meal_type, ingredients, steps, imageurl, false, username);
        r.setObjectID((ObjectId)d.get("_id"));
        return r;
    }

    //gets recipe by objectID
    public Recipe getRecipe(ObjectId id) {
        Document d = recipeDB.getCollection("recipes").find(eq("_id", id)).first();
        if (d == null) {
            System.out.println("failed to get recipe");
            return null;
        }

        Recipe r = new Recipe();
        r.setRecipeName(d.get("name").toString());
        r.setUsername(d.get("username").toString());
        r.setMealType(d.get("meal_type").toString());
        r.setIngredients(d.get("ingredients").toString());
        r.setImageURL(d.get("imageurl").toString());
        r.setSteps(stepsFromString(d.get("steps").toString())); 
        r.setObjectID(id);   
        return r;
    }
    
    //edits recipe document
    public void editRecipe(Recipe r) {
        ObjectId objectID = r.getObjectID();
        if (objectID == null) {
            System.out.println("ERROR: cannot find matching document to edit");
            return;
        }

        Bson filter = eq("_id", objectID);
        recipesCollection.updateMany(filter, 
                Updates.combine(Updates.set("meal_type", r.getMealType()),
                                Updates.set("ingredients", r.getIngredients()),
                                Updates.set("steps", stepsToString(r.getSteps())),
                                Updates.set("imageurl", r.getImageURL())));   
        System.out.println("updated recipe successfully!");

    }

    //deletes recipe document
    public void deleteRecipe(Recipe r) {
        ObjectId objectID = r.getObjectID();
        if (objectID == null) { //wasn't in database
            return;
        }

        Bson filter = eq("_id", objectID);
        recipesCollection.findOneAndDelete(filter);
        System.out.println("deleted recipe successfully!");

    }

    //deletes recipe document
    public void deleteRecipe(ObjectId id) {
        Bson filter = eq("_id", id);
        recipesCollection.findOneAndDelete(filter);
        System.out.println("deleted recipe successfully!");

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
        String[] stepsArray = steps.split(";;");
        for (int i = 0; i < stepsArray.length; i++) {
            al.add(stepsArray[i]);
        }
        return al;
    }

    public long countDocuments() {
        return recipesCollection.countDocuments();
    }

    public FindIterable<Document> find() {
        return recipesCollection.find();
    }

    public FindIterable<Document> find_by_user(String username) {
        Document filter = new Document("username", username);
        return recipesCollection.find(filter);
    }
}
