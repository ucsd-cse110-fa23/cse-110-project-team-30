package team30.recipeList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import team30.account.CreateAccount;
import team30.account.Login;
import org.bson.types.ObjectId;

// edited from public class Main
// the "App" in MVC
public class RecipeList extends Application {
    private Stage primStage;
    private Scene listScene;
    private Button postButton, getButton, putButton, deleteButton, shareButton;
    private Button chatGPTButton;

    private Model model; //model
    private RecipeListUI recipeListUI; //view
    private Controller controller; //controller

    private static WindowChange windowChange;
    
    private Login login;
    private CreateAccount createAccount;
    private Scene loginScene;
    private Scene createAccountScene;
    private Button loginButton;
    private Button loginCreateButton;
    private Button createAccountBackButton;
    private Button createAccountCreateButton;
    private Button logoutButton;
    private String username;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        recipeListUI = new RecipeListUI();
        model = new Model();
        listScene = new Scene(recipeListUI, 500, 600);
        Model model = new Model();
        login = new Login();
        loginScene = new Scene(login, 250, 300);
        createAccount = new CreateAccount();
        createAccountScene = new Scene(createAccount, 250, 300);

        postButton = recipeListUI.getPostButton();
        getButton = recipeListUI.getGetButton();
        putButton = recipeListUI.getPutButton();
        deleteButton = recipeListUI.getDeleteButton();
        chatGPTButton = recipeListUI.getChatGPTButton();
        shareButton = recipeListUI.getShareButton();

        controller = new Controller(this, model, login, createAccount);
        windowChange = new WindowChange();
        windowChange.setRecipeListMainApp(this);
        //model.loadRecipes();
        
        loginButton = login.getLoginButton();
        loginCreateButton = login.getCreateButton();
        createAccountBackButton = createAccount.getBackButton();
        createAccountCreateButton = createAccount.getCreateButton();
        logoutButton = recipeListUI.getLogoutButton();

        addLoginListeners();

        this.primStage = primaryStage;
        primaryStage.setTitle("PantryPal");
        // primaryStage.setScene(listScene);

        // check if autologin enable
        String filePath = "autoLogin.csv";
        Path path = Paths.get(filePath);
        if (Files.exists(path) && Files.isRegularFile(path)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                if ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    username = data[0];
                }
                recipeListUI.setUsername(username);
                recipeListUI.loadRecipes();
                System.out.println("Welcome user: " + username);
                primaryStage.setScene(listScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            primaryStage.setScene(loginScene);

        }
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
    public void setChatGPTButtonAction(EventHandler<ActionEvent> eventHandler) {chatGPTButton.setOnAction(eventHandler);}
    public void setShareButtonAction(EventHandler<ActionEvent> eventHandler) {shareButton.setOnAction(eventHandler);}


    public ObjectId getRecipeObjectID() {return recipeListUI.getRecipeObjectID();}
    public Recipe getRecipe() {return recipeListUI.getRecipe();}

    public RecipeListUI getRecipeListUI() {return recipeListUI;}
    public Controller getController() {return controller;}
    public Model getModel() {return model;}
    // public String getRecipeName() {return recipeListUI.getRecipeName();}
    // public String[] getRecipeDetails() {return recipeListUI.getRecipeDetails();}
    public String getQuery() {return recipeListUI.getQuery();}
    public Button getShareButton() {return recipeListUI.getShareButton();}
    public String getUsername() {return username;}

    public void showAlert(String title, String content) {
        // Alert alert = new Alert(Alert.AlertType.INFORMATION);
        // alert.setTitle(title);
        // alert.setHeaderText(null);
        // alert.setContentText(content);
        // alert.showAndWait();
    }

    public void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void addLoginListeners() {
        loginButton.setOnAction(e -> {
            int match = login.validUser();
            if (match == 0) {
                username = login.getUsername();
                System.out.println("Welcome user: " + username);
                recipeListUI.setUsername(username);
                generateAutoLoginFile();
                recipeListUI.loadRecipes();
                primStage.setScene(listScene); 
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
                primStage.setScene(listScene);
                this.username = username;
            }      
        });
        logoutButton.setOnAction(e -> {
            String filePath = "autoLogin.csv";
            try{
                Path path = Paths.get(filePath);
                Files.deleteIfExists(path);
            }
            catch (Exception f) {
                f.printStackTrace();
            }
            
            login.hideInvalidPrompt();
            username = null;
            primStage.setScene(loginScene);
        });
    }

    public void generateAutoLoginFile() {
        String filePath = "autoLogin.csv";
        try{
            Path path = Paths.get(filePath);
            Files.deleteIfExists(path);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (login.isAutoLogin()) {
            try {

                String[] acc_info = {login.getUsername(), login.getPassword()};
                FileWriter writer = new FileWriter(filePath);
                for (int i = 0; i < acc_info.length; i++) {
                    writer.append(acc_info[i]);
                    if (i < acc_info.length - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}