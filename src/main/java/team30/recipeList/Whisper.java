package team30.recipeList;

import java.io.*;
import java.net.*;
import org.json.*;

public class Whisper {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/audio/transcriptions";
    private static final String TOKEN = "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";
    private static final String MODEL = "whisper-1";
    private String file_path;

    public void setInputFile (String path) {
        file_path = path;
    }

    private static void writeParameterToOutputStream(OutputStream outputStream, String parameterName, 
        String parameterValue, String boundary) throws IOException {
        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write(
        (
            "Content-Disposition: form-data; name=\"" + parameterName + "\"\r\n\r\n"
        ).getBytes()
        );
        outputStream.write((parameterValue + "\r\n").getBytes());
    }
   
    private static void writeFileToOutputStream(OutputStream outputStream, File file, String boundary) throws IOException {
        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write(
        (
        "Content-Disposition: form-data; name=\"file\"; filename=\"" +
        file.getName() +
        "\"\r\n"
        ).getBytes()
            );
        outputStream.write(("Content-Type: audio/mpeg\r\n\r\n").getBytes());
        
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        fileInputStream.close();
    }

    private static String handleSuccessResponse(HttpURLConnection connection) throws IOException, JSONException {
        BufferedReader in = new BufferedReader(
            new InputStreamReader(connection.getInputStream())
        );
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject responseJson = new JSONObject(response.toString());
        String generatedText = responseJson.getString("text");

        return generatedText;
    }

    // Helper method to handle an error response
    private static void handleErrorResponse(HttpURLConnection connection) throws IOException, JSONException {
        BufferedReader errorReader = new BufferedReader(
            new InputStreamReader(connection.getErrorStream())
        );
        String errorLine;
        StringBuilder errorResponse = new StringBuilder();
        while ((errorLine = errorReader.readLine()) != null) {
            errorResponse.append(errorLine);
        }
        errorReader.close();
        String errorResult = errorResponse.toString();
        System.out.println("Error Result: " + errorResult);
    }

    public String run() throws JSONException, IOException, URISyntaxException {
        String transcription = "";
        File file = new File(file_path);

        // Set up HTTP connection
        URL url = new URI(API_ENDPOINT).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        
        // Set up request headers
        String boundary = "Boundary-" + System.currentTimeMillis();
        connection.setRequestProperty(
            "Content-Type",
            "multipart/form-data; boundary=" + boundary
        );
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);
        

        OutputStream outputStream = connection.getOutputStream();
        writeParameterToOutputStream(outputStream, "model", MODEL, boundary);
        writeFileToOutputStream(outputStream, file, boundary);
        outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
        outputStream.flush();
        outputStream.close();

        // Get response code
        int responseCode = connection.getResponseCode();
        
        
        // Check response code and handle response accordingly
        if (responseCode == HttpURLConnection.HTTP_OK) {
            transcription = handleSuccessResponse(connection);
        } else {
            handleErrorResponse(connection);
        }
        
        // Disconnect connection
        connection.disconnect();

        return transcription;
    }   
}

