package team30.recipeList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.util.ArrayList;

import org.bson.types.ObjectId;

// edited from public class Main
// the "App" in MVC
public class RecipeList extends Application {
    private Stage primStage;
    private Scene listScene;
    private Button postButton, getButton, putButton, deleteButton;

    private Model model; //model
    private RecipeListUI recipeListUI; //view
    private Controller controller; //controller

    private ArrayList<Recipe> recipes;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        recipeListUI = new RecipeListUI();
        model = new Model();
        listScene = new Scene(recipeListUI, 500, 600);
        
        postButton = recipeListUI.getPostButton();
        getButton = recipeListUI.getGetButton();
        putButton = recipeListUI.getPutButton();
        deleteButton = recipeListUI.getDeleteButton();

        recipes = new ArrayList<>();
        
        controller = new Controller(this, model);
        //model.loadRecipes();

        this.primStage = primaryStage;
        recipeListUI.setRecipeList(this);
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
    public void setAppFrame(RecipeListUI af) {recipeListUI = af;}

    public void setPostButtonAction(EventHandler<ActionEvent> eventHandler) {postButton.setOnAction(eventHandler);}
    public void setGetButtonAction(EventHandler<ActionEvent> eventHandler) {getButton.setOnAction(eventHandler);}
    public void setPutButtonAction(EventHandler<ActionEvent> eventHandler) {putButton.setOnAction(eventHandler);}
    public void setDeleteButtonAction(EventHandler<ActionEvent> eventHandler) {deleteButton.setOnAction(eventHandler);}

    public ObjectId getRecipeObjectID() {return recipeListUI.getRecipeObjectID();}
    public Recipe getRecipe() {return recipeListUI.getRecipe();}
    public String getQuery() {return recipeListUI.getQuery();}

    public RecipeListUI getRecipeListUI() {return recipeListUI;}
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