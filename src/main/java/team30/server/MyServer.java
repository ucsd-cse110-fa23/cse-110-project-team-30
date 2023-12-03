package team30.server;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.sun.net.httpserver.*;

import team30.recipeList.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

import org.bson.Document;

public class MyServer {

  // initialize server port and hostname
  private static final int SERVER_PORT = 8100;
  private static final String SERVER_HOSTNAME = "localhost";

  static RecipeDatabase recipeDB;
  private RecipeListUI recipeListUI;

  public static void main(String[] args) throws IOException {
    // create a thread pool to handle requests
    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    recipeDB = new RecipeDatabase();

    // create a server
    HttpServer server = HttpServer.create(
        new InetSocketAddress(SERVER_HOSTNAME, SERVER_PORT),
        0);

    HttpContext context = server.createContext("/recipe", new RecipeHandler(recipeDB));
    server.setExecutor(threadPoolExecutor);
    server.start();

    System.out.println("Server started on port " + SERVER_PORT);
  }

  public void loadRecipes() {
      try { 
          long totalRecipes = recipeDB.countDocuments();
          System.out.println("Total recipes: " + totalRecipes);

          FindIterable<Document> iterDoc = recipeDB.find();
          MongoCursor<Document> it = iterDoc.iterator();
          while (it.hasNext()) {
              Recipe cur = recipeDB.getRecipe(it.next());
              recipeListUI.addRecipe(cur);
          }
      }
      catch (Exception e) {
          System.out.println("couldn't open database!");
      }
  }

  public void saveRecipe(Recipe r) {
      try {
          recipeDB.upsertRecipe(r);
      }
      catch (Exception e) {
          System.out.println("couldn't upsert to database!");
          e.printStackTrace();
      }
  }

}

