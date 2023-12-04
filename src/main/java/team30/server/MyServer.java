package team30.server;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.sun.net.httpserver.*;

import team30.recipeList.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.*;

import org.bson.Document;

public class MyServer {

  // initialize server port and hostname
  private static final int SERVER_PORT = 8100;
  private static final String SERVER_HOSTNAME = "localhost";

  static RecipeDatabase recipeDB;
  static RecipeList recipeList;

  public static void main(String[] args) throws IOException {
    // create a thread pool to handle requests
    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    recipeDB = new RecipeDatabase();
    recipeList = new RecipeList();
    loadRecipes();

    // create a server
    HttpServer server = HttpServer.create(
        new InetSocketAddress(SERVER_HOSTNAME, SERVER_PORT),
        0);

    HttpContext contextRecipe = server.createContext("/recipe", new RecipeHandler(recipeDB));
    //HttpContext contextVoice = server.createContext("/voice", new VoiceHandler(recipeDB));
    server.setExecutor(threadPoolExecutor);
    server.start();

    System.out.println("Server started on port " + SERVER_PORT);
  }

  public static void loadRecipes() { //leave for account
    RecipeListUI recipeListUI = recipeList.getRecipeListUI();
    ArrayList<Recipe> rs = recipeDB.loadRecipes();
    for (Recipe cur : rs)
      recipeListUI.addRecipe(cur);
  }

}

