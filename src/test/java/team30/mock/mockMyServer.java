package team30.mock;

import team30.recipeList.Recipe;
import team30.server.RecipeDatabase;
import team30.server.RecipeHandler;

import com.sun.net.httpserver.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class mockMyServer {
    
  // initialize server port and hostname
  private static final int SERVER_PORT = 8110;
  private static final String SERVER_HOSTNAME = "localhost";

  public void start() throws IOException {
    // create a thread pool to handle requests
    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);


    RecipeDatabase recipeDB = new RecipeDatabase();

    // create a server
    HttpServer server = HttpServer.create(
        new InetSocketAddress(SERVER_HOSTNAME, SERVER_PORT),
        0);

    HttpContext context = server.createContext("/recipe", new RecipeHandler(recipeDB));
    server.setExecutor(threadPoolExecutor);
    server.start();
  }
}
