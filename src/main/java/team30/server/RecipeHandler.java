package team30.server;

import com.sun.net.httpserver.*;

import team30.recipeList.Recipe;

import java.io.*;
import java.net.*;
import java.util.*;

import org.bson.types.ObjectId;
import org.json.JSONObject;

public class RecipeHandler implements HttpHandler {

    //private final Map<String, Recipe> data; //ObjectID, recipe
    private RecipeDatabase recipeDB;

    public RecipeHandler(RecipeDatabase data) {
        this.recipeDB = data;
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();
        try {
            if (method.equals("GET")) {
                response = handleGet(httpExchange);
            } else if (method.equals("POST")) {
                response = handlePost(httpExchange);
            } else if (method.equals("PUT")) {
                response = handlePut(httpExchange);
            } else if (method.equals("DELETE")) {
                response = handleDelete(httpExchange);
            } else {
                throw new Exception("Not Valid Request Method");
            }
        } catch (Exception e) {
            System.out.println("An erroneous request");
            response = e.toString();
            e.printStackTrace();
        }
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
    }

    private String handleGet(HttpExchange httpExchange) throws IOException {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            String objectID = query.substring(query.indexOf("=") + 1);
            System.out.println((new ObjectId(objectID)).toString());
            Recipe recipe = recipeDB.getRecipe(new ObjectId(objectID)); // Retrieve data from hashmap
            if (recipe != null) {
                System.out.println("get successful!");
                response = recipe.toJSON().toString();
                System.out.println("Queried for " + objectID + " and found " + response);
            } else {
                response = "No data found for " + objectID;
            }
        }
        return response;
    }

    private String handlePost(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        String objectID = postData.substring(
                0,
                postData.indexOf(",")), recipeDetails = postData.substring(postData.indexOf(",") + 1);

        JSONObject json = new JSONObject();
        try {
            json = new JSONObject(recipeDetails);
        } catch (Exception e) {
            System.out.println("json failed to parse");
        }
        Recipe recipe = new Recipe();
        recipe.setObjectID(new ObjectId(objectID));
        recipe.setFromJSON(json);
        recipeDB.insertRecipe(recipe);

        String response = "Posted entry {" + objectID + ", " + recipeDetails + "}";
        System.out.println(response);
        scanner.close();

        return response;
    }

    private String handlePut(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        String objectID = postData.substring(
                0,
                postData.indexOf(",")), recipeDetails = postData.substring(postData.indexOf(",") + 1);

        // Store data in hashmap
        String response;
        System.out.println(recipeDetails);
        JSONObject json = new JSONObject();
        try {
            json = new JSONObject(recipeDetails);
        } catch (Exception e) {
            System.out.println("json failed to parse");
        }

        Recipe recipe = new Recipe();;
        if (recipeDB.getRecipe(new ObjectId(objectID)) != null) {
            response = "Updated entry {" + objectID + ", " + recipeDetails + "}";
            recipe.setFromJSON(json);
            recipe.setObjectID(new ObjectId(objectID));
        } else {
            response = "Added entry {" + objectID + ", " + recipeDetails + "}";
            recipe.setObjectID(new ObjectId(objectID));
            recipe.setFromJSON(json);
        }
        recipeDB.upsertRecipe(recipe);
        System.out.println(recipe.getIngredients());

        System.out.println(response);
        scanner.close();

        return response;
    }

    private String handleDelete(HttpExchange httpExchange) throws IOException {
        String response = "Invalid DELETE request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            String objectID = query.substring(query.indexOf("=") + 1);
            System.out.println(objectID);
            Recipe recipe = recipeDB.getRecipe(new ObjectId(objectID));
            recipeDB.deleteRecipe(new ObjectId(objectID)); // Retrieve data from database
            if (recipe != null) {
                response = "Deleted entry {" + objectID + ", " + recipe.toJSON().toString() + "}";
                System.out.println("Queried for " + objectID + " and found " + recipe.toJSON().toString());
            } else {
                response = "No data found for " + objectID;
            }
        }
        return response;
    }
}
