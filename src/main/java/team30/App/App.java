package team30.App;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import team30.account.CreateAccount;
import team30.account.Login;
import team30.recipeList.Controller;
import team30.recipeList.Model;
import team30.recipeList.Recipe;
import team30.recipeList.RecipeDetail;
import team30.recipeList.RecipeList;
import team30.server.RecipeDatabase;

import java.util.ArrayList;

public class App extends Application{
    private Stage primStage;
    Controller controller;
    private RecipeDatabase recipeDB;

    private RecipeList recipelist;
    private Login login;
    private CreateAccount createAccount;
    private RecipeDetail recipedetail;

    private Button postButton, getButton, putButton, deleteButton;
    private Button recipelistGenerateButton;
    private ArrayList<Button> detailOpenButtons;
    private Button loginButton;
    private Button loginCreateButton;
    private Button createAccountBackButton;
    private Button createAccountCreateButton;
    private Button detailBackButton;
    private Button detailEditButton;
    private Button detailSaveButton;
    private Button detailDeleteButton;
    private Button detailCancelButton;
    
    private Scene recipelistScene;
    private Scene loginScene;
    private Scene createAccountScene;
    private Scene recipeDetailScene;
    
    private String username;
    private String query;

    @Override
    public void start(Stage primaryStage) throws Exception {
        init();
        
        this.primStage = primaryStage;
        
        primaryStage.setTitle("PantryPal");
        primaryStage.setScene(loginScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    public void init() {
        recipelist = new RecipeList();
        login = new Login();
        createAccount = new CreateAccount();
        Model model = new Model();
        recipeDB = new RecipeDatabase();
        
        controller = new Controller(this, model);
        
        recipelistScene = new Scene(recipelist, 500, 600);
        loginScene = new Scene(login, 250, 300);
        createAccountScene = new Scene(createAccount, 250, 300);
        
        postButton = new Button("Post");
        getButton = new Button("Get");
        putButton = new Button("Put");
        deleteButton = new Button("Delete");      
        loginButton = login.getLoginButton();
        loginCreateButton = login.getCreateButton();
        createAccountBackButton = createAccount.getBackButton();
        createAccountCreateButton = createAccount.getCreateButton();
        detailOpenButtons = new ArrayList<>();

        username = "";
        query = "";
        
        addListeners();
        
        recipelist.setRecipeList(this);
    }

    // getters
    public Scene getScene() {return recipelistScene;}
    public Stage getPrimStage() {return primStage;}
    public String getQuery() {return query;}
    public Button getPostButton() {return postButton;}
    public void setRecipeList(RecipeList recipelist) {this.recipelist = recipelist;}
    public String getRecipeName() {return recipelist.getRecipeName();}
    public String[] getRecipeDetails() {return recipelist.getRecipeDetails();}
    public RecipeDatabase getRecipeDB() {return recipeDB;}
    public ArrayList<Button> getDetailOpenButtons() {return detailOpenButtons;}
    
    // setters
    public void setPostButtonAction(EventHandler<ActionEvent> eventHandler) {postButton.setOnAction(eventHandler);}
    public void setGetButtonAction(EventHandler<ActionEvent> eventHandler) {getButton.setOnAction(eventHandler);}
    public void setPutButtonAction(EventHandler<ActionEvent> eventHandler) {putButton.setOnAction(eventHandler);}
    public void setDeleteButtonAction(EventHandler<ActionEvent> eventHandler) {deleteButton.setOnAction(eventHandler);}
    
    
    // other methods
    public void addListeners() {
        loginButton.setOnAction(e -> {
            int match = login.validUser();
            if (match == 0) {
                primStage.setScene(recipelistScene); 
            }
        });   
        loginCreateButton.setOnAction(e -> {
            primStage.setScene(createAccountScene);
        });
        createAccountBackButton.setOnAction(e -> {
            primStage.setScene(loginScene);
        });
        createAccountCreateButton.setOnAction(e -> {
            String username = createAccount.makeNewAccount();
            if (username != null) {         // != null means successfully create, then auto login
                login.setUsername(username);
                primStage.setScene(recipelistScene);
                this.username = username;
            }      
        });
        for (int i = 0; i < detailOpenButtons.size(); ++i) {
            Recipe r = recipelist.getRecipes().get(i);
            detailOpenButtons.get(i).setOnAction(e -> {
                recipedetail = new RecipeDetail(this, r);
                recipeDetailScene = recipedetail.getDetailScene();

                detailBackButton = recipedetail.getBackButton();
                detailDeleteButton = recipedetail.getDeleteButton();
                detailSaveButton = recipedetail.getSaveButton();
                detailEditButton = recipedetail.getEditButton();
                detailCancelButton = recipedetail.getCancelButton();
                
                detailBackButton.setOnAction(f -> {
                    primStage.setScene(recipelistScene);
                });
                detailDeleteButton.setOnAction(f -> {
                    
                });

                primStage.setScene(recipeDetailScene);
            });
        }
    }

    public void showAlert(String title, String content) {
        // Alert alert = new Alert(Alert.AlertType.INFORMATION);
        // alert.setTitle(title);
        // alert.setHeaderText(null);
        // alert.setContentText(content);
        // alert.showAndWait();
    }
}
