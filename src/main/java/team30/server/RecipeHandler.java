package team30.server;

import com.sun.net.httpserver.*;

import team30.recipeList.Recipe;

import java.io.*;
import java.net.*;
import java.util.*;

import org.bson.types.ObjectId;

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
                response = detailsToString(recipe.getRecipeDetails());
                System.out.println("Queried for " + objectID + " and found " + detailsToString(recipe.getRecipeDetails()));
            } else {
                response = "No data found for " + objectID;
            }
        }
        return response;
    }

    private String handlePost(HttpExchange httpExchange) throws IOException {
        System.out.println("Handling post...");
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        String objectID = postData.substring(
                0,
                postData.indexOf(",")), recipeDetails = postData.substring(postData.indexOf(",") + 1);

        // Store data in hashmap
        Recipe recipe = new Recipe();
        recipe.setObjectID(new ObjectId(objectID));
        recipe.setRecipeDetails(detailsToArray(recipeDetails));
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

        if (recipeDB.getRecipe(new ObjectId(objectID)) != null) {
            response = "Updated entry {" + objectID + ", " + recipeDetails + "}";
            Recipe recipe = new Recipe();
            recipe.setObjectID(new ObjectId(objectID));
            recipe.setRecipeDetails(detailsToArray(recipeDetails));
            recipeDB.insertRecipe(recipe);
        } else {
            response = "Added entry {" + objectID + ", " + recipeDetails + "}";
            Recipe recipe = new Recipe();
            recipe.setObjectID(new ObjectId(objectID));
            recipe.setRecipeDetails(detailsToArray(recipeDetails));
            recipeDB.editRecipe(recipe);
        }

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
            Recipe recipe = recipeDB.getRecipe(new ObjectId(objectID));
            recipeDB.deleteRecipe(new ObjectId(objectID)); // Retrieve data from database
            if (recipe != null) {
                response = "Deleted entry {" + objectID + ", " + detailsToString(recipe.getRecipeDetails()) + "}";
                System.out.println("Queried for " + objectID + " and found " + detailsToString(recipe.getRecipeDetails()));
            } else {
                response = "No data found for " + objectID;
            }
        }
        return response;
    }

    private String detailsToString(ArrayList<String> details) {
        if (details.size() < 5) {
            System.out.println("ERROR: recipe details doesn't have the right amount of information");
        }
        String s = "";
        s += details.get(0) + ";; "; //name
        s += details.get(1) + ";; "; //meal type
        s += details.get(2) + ";; "; //ingredient list
        s += details.get(3) + ";; "; //image url
        for (int i = 4; i < details.size(); i++) { //steps
            s += details.get(i) + ";; ";
        }
        return s;
    }

    private ArrayList<String> detailsToArray(String detailsString) {
        ArrayList<String> details = new ArrayList<>();
        String[] detailsArray = detailsString.split(";; ");
        for (int i = 0; i < detailsArray.length; i++) {
            details.add(detailsArray[i]);
        }
        return details;
    }

}
