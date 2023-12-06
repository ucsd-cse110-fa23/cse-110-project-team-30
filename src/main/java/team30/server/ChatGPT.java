package team30.server;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class ChatGPT {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/completions";
    private static final String API_KEY = "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";
    private static final String MODEL = "text-davinci-003";
    
    public String generateRecipe(String mealType, String ingredientsRaw) throws IOException, InterruptedException, URISyntaxException {

        System.out.println("Generating recipe...");
        //Our prompt for ChatGPT with our provided mealtype and ingredients
        String prompt = "Create a " + mealType + " recipe using only " + ingredientsRaw + "and common condiments, label the recipe name, ingredients, and instructions";
        int maxTokens = 300;

        // Create a request body which you will pass into request object
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        requestBody.put("prompt", prompt);
        requestBody.put("max_tokens", maxTokens);
        requestBody.put("temperature", 1.0);

        // Create the HTTP Client
        HttpClient client = HttpClient.newHttpClient();

        // Create the request object
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(new URI(API_ENDPOINT))
        .header("Content-Type", "application/json")
        .header("Authorization", String.format("Bearer %s", API_KEY))
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();


        // Send the request and receive the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Process the response
        String responseBody = response.body();
        JSONObject responseJson = new JSONObject(responseBody);
        JSONArray choices = responseJson.getJSONArray("choices");
        String generatedText = choices.getJSONObject(0).getString("text");
        return generatedText;
   }

   public Recipe makeRecipeByChatGPTResponse(String mealType, String ingredientsRaw, String username) {
        try {
            
            // Generate recipe
            String generatedRecipe = generateRecipe(mealType, ingredientsRaw);
            System.out.println("Generated Recipe: ");
            // System.out.println(generatedRecipe);
            
            String[] lines = generatedRecipe.split("\\r?\\n|\\r");
            String recipeName = "", ingredients = "", imgurl = "";
            ArrayList<String> steps = new ArrayList<>();
            int count = 0; //0- recipeName, 1- ingredients, 2- instructions
            for (int i = 0; i < lines.length; i++) {
                System.out.println(lines[i]);
                if (!(lines[i].replaceAll("\\s", "") == "") && !(lines[i].replaceAll("\\n", "") == "") && count == 0) {
                    //recipeName (unlabelled)
                    recipeName = lines[i].toLowerCase();
                    count = 100;
                }
                if (lines[i].contains("Recipe Name: ")) {
                    //recipeName (labelled)
                    recipeName = lines[i].substring(13).toLowerCase();
                    count = 100;
                }
                
                if (lines[i].contains("Ingredients:")) {
                    count = 1;
                    continue;
                }
                else if (lines[i].contains("Instructions:")) {
                    count = 2;
                    continue;
                }
                
                if (count == 1) {
                    //ingredients
                    if (!ingredients.equals("") && !(lines[i].replaceAll("\\n", "") == ""))
                    ingredients += ", ";
                    ingredients += lines[i].toLowerCase().replaceAll("-", "");
                }
                
                if (count == 2) {
                    //steps
                    if (!(lines[i].replaceAll("\\s", "") == "") && !(lines[i].replaceAll("\\n", "") == ""))
                    steps.add(lines[i]);
                }
            }
            
            imgurl = ImageManager.generateImage(recipeName);
            
            return new Recipe(recipeName, mealType, ingredients, steps, imgurl, true, username);
        }
        catch (Exception e) {
            System.out.println("Make new recipe from ChatGPT generation failed!");
            e.printStackTrace();
            return null;
        }
    }
}
