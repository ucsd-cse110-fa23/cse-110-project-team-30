package team30.server;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
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
            String filename = query.substring(query.indexOf("=") + 1);
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

}
