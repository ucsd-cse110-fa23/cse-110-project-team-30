package team30.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        
        RecipeView view = new RecipeView();
        RecipeModel model = new RecipeModel();
        RecipeController controller = new RecipeController(view, model);

        Scene scene = new Scene(view.getGrid(), 400, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("PantryPal");
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}