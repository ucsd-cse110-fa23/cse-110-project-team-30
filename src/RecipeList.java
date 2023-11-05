import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

//borrowed from Contact Management mini project
public class RecipeList extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        AppFrame root = new AppFrame();

        primaryStage.setTitle("Recipe List");
        primaryStage.setScene(new Scene(root, 500, 600));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
