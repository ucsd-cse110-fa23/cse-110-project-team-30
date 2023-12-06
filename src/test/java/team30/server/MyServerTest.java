package team30.server;
import team30.recipeList.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import team30.mock.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.HttpURLConnection;
import java.net.URL;
// import javafx.event.ActionEvent;


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

    // @Test
    // void testPostMethods() {
    //     String[] details = {"water", "eggs", "noodle"};
    //     String recipeName = "Noodle";
    //     String response = controller.handlePostButton(recipeName, details);

    //     String expected = "Posted entry {" + recipeName + ",  Meal Type: "+ details[0] + " Ingredient List: "+ details[1]+ " Steps: " + details[2] + "}";
    //     assertEquals(expected, response);
    // }

    // @Test
    // void testPostWithEmpty() {
    //     String[] details = {"", "", ""};
    //     String recipeName = "";
    //     String response = controller.handlePostButton(recipeName, details);

    //     String expected = "Posted entry {" + recipeName + ",  Meal Type: "+ details[0] + " Ingredient List: "+ details[1]+ " Steps: " + details[2] + "}";
    //     assertEquals(expected, response);
    // }
}
