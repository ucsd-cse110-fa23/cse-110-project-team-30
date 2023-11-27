package team30.recipeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

public class DallE {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/images/generations";
    private static final String API_KEY = "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";
    private static final String MODEL = "dall-e-2";

    /**
     * Generates an image based on the prompt given and places it in the location shown in loc
     * @param prompt - the prompt to design the image by
     * @param loc - intended file location for the image
     */
    public void generateImage(String prompt, String loc) throws IOException, InterruptedException, URISyntaxException{
        int n = 1;

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        requestBody.put("prompt", prompt);
        requestBody.put("n", n);
        requestBody.put("size", "256x256");

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest
            .newBuilder()
            .uri(URI.create(API_ENDPOINT))
            .header("Content-Type", "application/json")
            .header("Authorization", String.format("Bearer %s", API_KEY))
            .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
            .build();

        HttpResponse<String> response = client.send(
            request,
            HttpResponse.BodyHandlers.ofString()
        );

        String responseBody = response.body();

        JSONObject responseJson = new JSONObject(responseBody);

        String generatedImageURL = responseJson.getJSONArray("data").getJSONObject(0).getString("url");

        //System.out.println("DALL-E Response:"); May have use in debugging
        //System.out.println(generatedImageURL);

        try(
            InputStream in = new URI(generatedImageURL).toURL().openStream()
        )
        {
            Files.copy(in, Paths.get(loc));
        }
    }
}
