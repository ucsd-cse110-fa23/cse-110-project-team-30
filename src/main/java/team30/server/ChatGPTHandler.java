package team30.server;

import com.sun.net.httpserver.*;

import java.io.*;
import java.net.*;
import org.json.JSONException;

public class ChatGPTHandler implements HttpHandler {
    private ChatGPT chatGPT;

    ChatGPTHandler(ChatGPT c) {
        chatGPT = c;
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

    private String handleGet(HttpExchange httpExchange) throws IOException, JSONException, URISyntaxException {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            String mealtype, ingredients;
            //format: ?=dinner=apples
            mealtype = query.substring(query.indexOf("=") + 1, query.indexOf("-"));
            if (query.indexOf("-") + 1 < query.length())
                ingredients = query.substring(query.indexOf("-") + 1);
            else
                ingredients = "";
            System.out.println("ChatGPT: " + mealtype + ", " + ingredients);
            String generatedRecipe;
            try {
                generatedRecipe = chatGPT.generateRecipe(mealtype, ingredients);
                response = generatedRecipe;
            } catch (InterruptedException e) {
                e.printStackTrace();
                response = "Unable to process " + mealtype + " and " + ingredients;
            }
        }
        return response;
    }

}
