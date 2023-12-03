package team30.recipeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

import java.net.URI;


public class Model {
    public String performRequest(String method, String language, Recipe recipe, String query) {
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
                out.write(language + "," + recipe);
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

    public void loadRecipes() {
        //fix to just call get on list of objectIDs in database
    }

    public void saveRecipe(Recipe r) {
        String objectID = r.getObjectID().toString();
        performRequest("PUT", objectID, r, null);
    }

}