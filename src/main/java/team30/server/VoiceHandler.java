package team30.server;
import com.sun.net.httpserver.*;

import team30.recipeList.Recipe;

import java.io.*;
import java.net.*;
import java.util.*;

import org.bson.types.ObjectId;

public class VoiceHandler implements HttpHandler {
    private Whisper audioProcessor;

    VoiceHandler(Whisper w) {
        audioProcessor = w;
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();
        try {
            if (method.equals("GET")) {
                response = handleGet(httpExchange);
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
        //gets voice file
        //returns processed voice
        if (query != null) {
            String filename = query.substring(query.indexOf("=") + 1);
            filename = "src\\main\\java\\team30\\recipeList\\ + " + filename;
            String spokentext = "";
            // Recipe recipe = recipeDB.getRecipe(new ObjectId(objectID)); // Retrieve data from hashmap
            // if (recipe != null) {
            //     response = detailsToString(recipe.getRecipeDetails());
            //     System.out.println("get successful!");
            //     System.out.println("Queried for " + filename + " and found " + spokentext);
            // } else {
            //     response = "No data found for " + filename;
            // }
        }
        return response;
    }

}
