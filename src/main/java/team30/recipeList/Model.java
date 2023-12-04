package team30.recipeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

import java.net.URI;


public class Model {
    //for recipes
    public String performRequest(String method, String objectID, Recipe recipe, String query) {
        try {
            String urlString = "http://localhost:8100/recipe/";
            if (query != null) {
                urlString += "?=" + query;
            }
            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoOutput(true);

            if (method.equals("POST") || method.equals("PUT")) {
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(objectID + "," + recipe);
                out.flush();
                out.close();
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            in.close();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }

// public void loadRecipes() {
//     List<Recipe> fetchedRecipes = new ArrayList<>();

//     // Perform iterative requests to fetch all recipes
//     while (true) {
//         String response = performRequest("GET", language, recipe, query);

//         // Parse the response and add fetched recipes to the list
//         List<Recipe> parsedRecipes = parseRecipesFromDatabaseResponse(response);
//         if (parsedRecipes.isEmpty()) {
//             break;
//         }

//         fetchedRecipes.addAll(parsedRecipes);
//     }

//     recipes = fetchedRecipes; // Set the fetched recipes to your recipes list
// }
//   public static void loadRecipes() {
//       try { 
//           long totalRecipes = recipeDB.countDocuments();
//           System.out.println("Total recipes: " + totalRecipes);

//           FindIterable<Document> iterDoc = recipeDB.find();
//           MongoCursor<Document> it = iterDoc.iterator();
//           while (it.hasNext()) {
//               Recipe cur = recipeDB.getRecipe(it.next());
//               recipeListUI.addRecipe(cur);
//           }
//       }
//       catch (Exception e) {
//           System.out.println("couldn't open database!");
//           e.printStackTrace();
//       }
//   }
    public void saveRecipe(Recipe r) {
        String objectID = r.getObjectID().toString();
        performRequest("PUT", objectID, r, null);
    }

}