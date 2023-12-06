package team30.recipeList;

import static com.mongodb.client.model.Filters.in;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.util.concurrent.TimeUnit;


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

    //TODO: for multi-device voice
    // public String performVoiceRequest(String method, String fileName, File file, String query) {
    //     try {
    //         String urlString = "http://localhost:8100/voice/";
    //         if (query != null) {
    //             urlString += "?=" + query;
    //         }
    //         URL url = new URI(urlString).toURL();
    //         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    //         conn.setRequestMethod(method);
    //         conn.setDoOutput(true);

    //         BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    //         String response = in.readLine();
    //         in.close();
    //         return response;
    //     } catch (Exception ex) {
    //         ex.printStackTrace();
    //         return "Error: " + ex.getMessage();
    //     }
    // }

    public String performVoiceRequest(String method, String query) {
        try {
            String urlString = "http://localhost:8100/voice/";
            if (query != null) {
                urlString += "?=" + query;
            }
            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoOutput(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            in.close();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }

    //for chatgpt
    public String performChatGPTRequest(String method, String mealType, String ingredients) {
        try {
            String urlString = "http://localhost:8100/chatGPT/";
            if (mealType == null) mealType = "";
            if (ingredients == null) ingredients = "";
            urlString += "?=" + mealType + "-" + ingredients;

            System.out.println("URL: " + urlString);

            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoOutput(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    response.append(line);
                    response.append("\n");
                }
            }
            in.close();
            return response.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }
}