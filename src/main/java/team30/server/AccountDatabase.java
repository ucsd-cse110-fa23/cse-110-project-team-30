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
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

import team30.recipeList.Recipe; 

public class AccountDatabase {
    MongoDatabase recipeDB;
    MongoCollection<Document> accountCollection;
    MongoClient mongoClient;

    // String uri = "mongodb+srv://lil043:VA4U7rBgvZ0EqlNO@cse110.ltw8f69.mongodb.net/?retryWrites=true&w=majority";

    String uri = "mongodb+srv://m1ren:IHcPb6UPAHtXNQfK@cluster0.nybipuz.mongodb.net/?retryWrites=true&w=majority";

    //default constructor
    public AccountDatabase() {
        mongoClient = MongoClients.create(uri);
        recipeDB = mongoClient.getDatabase("recipes_db");
        // recipeDB.createCollection("accounts");
        accountCollection = recipeDB.getCollection("accounts");
        try {
            mongoClient = MongoClients.create(uri);
            recipeDB = mongoClient.getDatabase("recipes_db");
        }
        catch (Exception e) {
            System.out.println("ERROR: failed to access database");
        }
    }

    public void createAccount(String username, String password) {
            Document acc = new Document("username",username).append("password", password);
            try {
                accountCollection.insertOne(acc);
                System.out.println("inserted new account!");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
    }

    /**
     * search db to find if username and password match
     * if 0, match
     * if 1, username exists, but password not match
     * if -1, username does not exist
     */
    public int validUser(String username, String password) {
        Bson filter = eq("username", username);
        MongoCursor<Document> cursor = accountCollection.find(filter).iterator();
        if (cursor.hasNext()) {
            Document account = cursor.next();
            String db_password = account.getString("password");
            if (db_password.equals(password)) return 0;
            else return 1;
        } else {
            return -1;
        }
    }
}

