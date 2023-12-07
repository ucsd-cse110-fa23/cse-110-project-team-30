package team30.server;

import com.sun.net.httpserver.*;

import team30.recipeList.Recipe;

import java.io.*;
import java.net.*;
import java.util.*;

import org.bson.Document;
import org.bson.types.ObjectId;

public class AccountHandler implements HttpHandler {

    private AccountDatabase accountDB;

    public AccountHandler(AccountDatabase data) {
        this.accountDB = data;
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();
        try {
            if (method.equals("GET")) {
                response = handleGet(httpExchange);
            } else if (method.equals("POST")) {
                response = handlePost(httpExchange);
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
            String accName = query.substring(query.indexOf("=") + 1);
            String accPass = accountDB.getPassword(accName); // Retrieve password from username
            if (accPass != null) {
                System.out.println("get successful!");
                response = accPass;
            } else {
                response = "No data found for " + accName;
            }
        }
        return response;
    }

    private String handlePost(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        String accName = postData.substring(
                0,
                postData.indexOf(",")), accPass = postData.substring(postData.indexOf(",") + 1);

        // Store data in hashmap
        accountDB.createAccount(accName, accPass);
        String response = "Posted entry {" + accName + ", " + accPass + "}";
        System.out.println(response);
        scanner.close();

        return response;
    }
}
