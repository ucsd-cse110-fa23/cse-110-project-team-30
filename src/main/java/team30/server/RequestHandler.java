package team30.server;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class RequestHandler implements HttpHandler {

    private final Map<String, String> data;

    public RequestHandler(Map<String, String> data) {
        this.data = data;
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();
        try {
            if (method.equals("GET")) {
                response = handleGet(httpExchange);
            } else if (method.equals("POST")) {
                response = handlePost(httpExchange);
            } else if (method.equals("PUT")) {
                response = handlePut(httpExchange);
            } else if (method.equals("DELETE")) {
                response = handleDelete(httpExchange);
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
            String value = query.substring(query.indexOf("=") + 1);
            String recipeDetails = data.get(value); // Retrieve data from hashmap
            if (recipeDetails != null) {
                response = recipeDetails;
                System.out.println("Queried for " + value + " and found " + recipeDetails);
            } else {
                response = "No data found for " + value;
            }
        }
        return response;
    }

    private String handlePost(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        String recipeName = postData.substring(
                0,
                postData.indexOf(",")), recipeDetails = postData.substring(postData.indexOf(",") + 1);

        // Store data in hashmap
        data.put(recipeName, recipeDetails);

        String response = "Posted entry {" + recipeName + ", " + recipeDetails + "}";
        System.out.println(response);
        scanner.close();

        return response;
    }

    private String handlePut(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        String recipeName = postData.substring(
                0,
                postData.indexOf(",")), recipeDetails = postData.substring(postData.indexOf(",") + 1);

        // Store data in hashmap

        String response;

        if (data.get(recipeName) != null) {
            response = "Updated entry {" + recipeName + ", " + recipeDetails + "}";
            data.put(recipeName, recipeDetails);
        } else {
            response = "Added entry {" + recipeName + ", " + recipeDetails + "}";
            data.put(recipeName, recipeDetails);
        }

        System.out.println(response);
        scanner.close();

        return response;
    }

    private String handleDelete(HttpExchange httpExchange) throws IOException {
        String response = "Invalid DELETE request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            String value = query.substring(query.indexOf("=") + 1);
            String recipeDetails = data.remove(value); // Retrieve data from hashmap
            if (recipeDetails != null) {
                response = "Deleted entry {" + value + ", " + recipeDetails + "}";
                System.out.println("Queried for " + value + " and found " + recipeDetails);
            } else {
                response = "No data found for " + value;
            }
        }
        return response;
    }
}
