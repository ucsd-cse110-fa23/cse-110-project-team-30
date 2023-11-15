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

public class RecipeDatabase {
    public static void main( String[] args ) throws IOException {
        String uri = "mongodb+srv://lil043:VA4U7rBgvZ0EqlNO@cse110.ltw8f69.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase recipeDB = mongoClient.getDatabase("recipes_db");
            MongoCollection<Document> recipesCollection = recipeDB.getCollection("recipes");


            // LAB CODE
            
            // List<Document> recipes = new ArrayList<>();
            // for (int i = 0; i < recipesCollection.countDocuments(); i++) {
            //     recipes.add(new Document("_id", new ObjectId()).append("recipe", records.get(i).get(0)) //name
            //                                                     .append("recipe", records.get(i).get(1)) //meal type
            //                                                     .append("description", records.get(i).get(2)) //ingredients
            //                                                     .append("hours", records.get(i).get(2))); //steps

            // }
            // recipesCollection.insertMany(recipes, new InsertManyOptions().ordered(false));

            // System.out.println("Total recipes: " + recipesCollection.countDocuments());
            
            // Document spinach = recipesCollection.find(eq("recipe", "Savory Spinach Delight")).first();
            // System.out.println("Savory Spinach Delight takes " + spinach.get("hours") + " hours to make.");


            // Bson updateOperation = set("hours", 4.5);
            // recipesCollection.updateOne(eq("recipe", "Savory Spinach Delight"), updateOperation);spinach = recipesCollection.find(eq("recipe", "Savory Spinach Delight")).first();
            // System.out.println("Savory Spinach Delight takes " + spinach.get("hours") + " hours to make.");

            // Bson filter = eq("recipe", "Spicy Shrimp Tacos");
            // System.out.println("Deleted Spicy Shrimp Tacos");
            // recipesCollection.deleteOne(filter);

            // System.out.println("Total recipes: " + recipesCollection.countDocuments());
        }
    }
}
