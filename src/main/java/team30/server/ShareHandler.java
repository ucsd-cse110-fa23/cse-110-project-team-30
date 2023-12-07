package team30.server;

import com.sun.net.httpserver.*;

import team30.recipeList.ImageManager;
import team30.recipeList.Recipe;

import java.io.*;
import java.net.*;
import java.util.*;

import org.bson.types.ObjectId;

public class ShareHandler implements HttpHandler {

    RecipeDatabase recipeDB;
    ImageManager im;

    ShareHandler(RecipeDatabase rd) {
        recipeDB = rd;
        im = new ImageManager();
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
        if (query != null) {
            //example:
            //http://localhost:8100/share?={objectID}
            String objectID = query.substring(query.indexOf("=") + 1);
            //search through database to get recipe
            Recipe r = recipeDB.getRecipe(new ObjectId(objectID));
            //example data
            getImage(r);
            
            StringBuilder htmlBuilder = new StringBuilder();
            htmlBuilder
            .append("<html>")
            .append("<body>")
            .append("<h1>")
            .append(r.getRecipeTitle()) //recipe name
            .append("</h1>")
            .append("<br></br>") //line break
            .append("<h2>")
            .append(r.getMealType()) //meal type
            .append("<br></br>") //line break
            .append(r.getIngredients()) //ingredients
            .append("<br></br>") //line break
            .append(r.getSteps().toString()) //steps as string
            .append("<img src=\"src/main/java/team30/recipeList/images/" + r.getImageURL() + "\">") //image
            .append("</h2>")
            .append("</body>")
            .append("</html>");

            System.out.println("src\\main\\java\\team30\\recipeList\\images\\" + r.getImageURL());


            // encode HTML content
            response = htmlBuilder.toString();
        }
        return response;
    }

    public void getImage(Recipe recipe){
        ImageManager.ensurePathExists(recipe.getImageURL(), recipe.getRecipeTitle(), !recipe.getImageGenerated());
        recipe.setImageGenerated(true);
    }
}