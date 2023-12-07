package team30.recipeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.net.URI;


public class Model {
    //for recipes
    public String performRequest(String method, String objectID, Recipe recipe, String query) {
        try {
            String urlString = "http://localhost:8100/recipe/";
            if (query != null) {
                urlString += "?=" + query;
            }
            System.out.println(urlString);
            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoOutput(true);

            if (method.equals("POST") || method.equals("PUT")) {
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(objectID + "," + recipe.toJSON().toString());
                out.flush();
                out.close();
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            in.close();
            return response;
        } catch (ConnectException e) {
            return "Connection error!";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }

    //for accounts
    public String performAccountRequest(String method, String accountName, String accountPassword, String query) {
        try {
            String urlString = "http://localhost:8100/account/";
            if (query != null) {
                urlString += "?=" + query;
            }
            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoOutput(true);

            if (method.equals("POST") || method.equals("PUT")) {
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(accountName + "," + accountPassword);
                out.flush();
                out.close();
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            in.close();
            return response;
        } catch (ConnectException e) {
            return "Connection error!";
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

            mealType = URLEncoder.encode(mealType, StandardCharsets.UTF_8.toString());
            ingredients = URLEncoder.encode(ingredients, StandardCharsets.UTF_8.toString());

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
    
    public String performShareRequest(String method, String objectID) {
        try {
            String urlString = "http://localhost:8100/share/";
            if (objectID != null) {
                urlString += "?=" + objectID;
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
}