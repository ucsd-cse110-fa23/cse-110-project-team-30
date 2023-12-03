package team30.recipeList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import org.bson.types.ObjectId;

// edited from public class Main
// the "App" in MVC
public class RecipeList extends Application {
    private Stage primStage;
    private Scene listScene;
    private Button postButton, getButton, putButton, deleteButton;

    private Controller controller; 
    private Model model;
    private RecipeListUI root; //view
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new RecipeListUI();
        model = new Model();
        listScene = new Scene(root, 500, 600);
        
        postButton = root.getPostButton();
        getButton = root.getGetButton();
        putButton = root.getPutButton();
        deleteButton = root.getDeleteButton();
        
        controller = new Controller(this, model);
        model.loadRecipes();

        this.primStage = primaryStage;
        root.setRecipeList(this);
        primaryStage.setTitle("PantryPal");
        primaryStage.setScene(listScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Stage getPrimStage() {return primStage;}
    public Scene getScene() {return listScene;}
    public void setAppFrame(RecipeListUI af) {root = af;}

    public void setPostButtonAction(EventHandler<ActionEvent> eventHandler) {postButton.setOnAction(eventHandler);}
    public void setGetButtonAction(EventHandler<ActionEvent> eventHandler) {getButton.setOnAction(eventHandler);}
    public void setPutButtonAction(EventHandler<ActionEvent> eventHandler) {putButton.setOnAction(eventHandler);}
    public void setDeleteButtonAction(EventHandler<ActionEvent> eventHandler) {deleteButton.setOnAction(eventHandler);}

    public ObjectId getRecipeObjectID() {return root.getRecipeObjectID();}
    public Recipe getRecipe() {return root.getRecipe();}
    public String getQuery() {return root.getQuery();}

    public RecipeListUI getRecipeListUI() {return root;}
    public Controller getController() {return controller;}
    public Model getModel() {return model;}

    public void showAlert(String title, String content) {
        // Alert alert = new Alert(Alert.AlertType.INFORMATION);
        // alert.setTitle(title);
        // alert.setHeaderText(null);
        // alert.setContentText(content);
        // alert.showAndWait();
    }
}