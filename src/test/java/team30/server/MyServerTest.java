package team30.server;
import team30.recipeList.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sun.net.httpserver.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.net.HttpURLConnection;
import java.net.URL;
// import javafx.event.ActionEvent;

class mockMyServer {

  // initialize server port and hostname
  private static final int SERVER_PORT = 8100;
  private static final String SERVER_HOSTNAME = "localhost";

  public void start() throws IOException {
    // create a thread pool to handle requests
    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    // create a map to store data
    Map<String, String> data = new HashMap<>();

    // create a server
    HttpServer server = HttpServer.create(
        new InetSocketAddress(SERVER_HOSTNAME, SERVER_PORT),
        0);

    HttpContext context = server.createContext("/", new RequestHandler(data));
    server.setExecutor(threadPoolExecutor);
    server.start();
  }

}

class mockController {
    private Model model;

    mockController(Model model) {
        this.model = model;
    }

    public String handlePostButton(String recipeName, String[] recipeDetails) {
        String response = model.performRequest("POST", recipeName, detailsToString(recipeDetails), null);
        return response;
    }

    public String handleGetButton(/*TODO: figure out arguments */) {
        // String query = view.getQuery();
        // String response = model.performRequest("GET", null, null, query);
        // view.showAlert("Response", response);
        return "";
    }

    public String handlePutButton(/*TODO: figure out arguments */) {
        // String language = view.getLanguage();
        // String year = view.getYear();
        // String response = model.performRequest("PUT", language, year, null);
        // view.showAlert("Response", response);
        return "";
    }

    public String handleDeleteButton/*TODO: figure out arguments */() {
        // String query = view.getQuery();
        // String response = model.performRequest("DELETE", null, null, query);
        // view.showAlert("Response", response);
        return "";
    }

    public String detailsToString(String[] details) {
        if (details.length != 3) {
            System.out.println("ERROR: recipe details doesn't have the right amount of information");
        }
        String s = "";
        s += " Meal Type: " + details[0];
        s += " Ingredient List: " + details[1];
        s += " Steps: " + details[2];
        return s;
    }
}

public class MyServerTest {
    private static final int PORT_NUMBER = 8100;
    private mockMyServer localServer;
    private mockController controller;

    @BeforeEach
    void setup() {
        localServer = new mockMyServer();
        controller = new mockController(new Model());

        try {
            localServer.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testServerRunning() {
        // Make a request to the server and check the response
        try {
            URL url = new URL("http://localhost:" + PORT_NUMBER);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testPostMethods() {
        String[] details = {"water", "eggs", "noodle"};
        String recipeName = "Noodle";
        String response = controller.handlePostButton(recipeName, details);

        String expected = "Posted entry {" + recipeName + ",  Meal Type: "+ details[0] + " Ingredient List: "+ details[1]+ " Steps: " + details[2] + "}";
        assertEquals(expected, response);
    }

    @Test
    void testPostWithEmpty() {
        String[] details = {"", "", ""};
        String recipeName = "";
        String response = controller.handlePostButton(recipeName, details);

        String expected = "Posted entry {" + recipeName + ",  Meal Type: "+ details[0] + " Ingredient List: "+ details[1]+ " Steps: " + details[2] + "}";
        assertEquals(expected, response);
    }
}
