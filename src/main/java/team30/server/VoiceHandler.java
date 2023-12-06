package team30.server;

import com.sun.net.httpserver.*;

import team30.recipeList.Recipe;

import java.io.*;
import java.net.*;

import org.bson.types.ObjectId;
import org.json.JSONException;

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
            } //if (method.equals("POST")) {
            //     response = handlePost(httpExchange);
            // } 
            else {
                throw new Exception("Not Valid Request Method " + method);
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
            String filename = query.substring(query.indexOf("=") + 1);
            //TODO: pass in file directly
            //rn: passing in filename (only works for local)
            String filepath = "src\\main\\java\\team30\\recipeList\\" + filename;
            System.out.println(filepath);
            audioProcessor.setInputFile(filepath);
            String spokentext = "";
            try {
                spokentext = audioProcessor.run();
                response = spokentext;
            } catch (JSONException | IOException | URISyntaxException e1) {
                response = "Unable to process " + filename;
            }
        }
        return response;
    }

    //TODO: with filename, post file
    // private String handlePost(HttpExchange httpExchange) throws IOException, JSONException, URISyntaxException {
    //     return "";
    // }

}
