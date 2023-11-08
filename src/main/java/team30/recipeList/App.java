package team30.recipeList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        RecipeList view = new RecipeList();
        view.start(primaryStage);
        Model model = new Model();
        Controller controller = new Controller(view, model);


        AppFrame root = new AppFrame();


        primaryStage.setTitle("PantryPal");
        primaryStage.setScene(new Scene(root, 500, 600));
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}