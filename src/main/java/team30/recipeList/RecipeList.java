package team30.recipeList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.ArrayChangeListener;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import javafx.geometry.Rectangle2D;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Arrays;

import javafx.util.Pair;
import team30.account.CreateAccount;
import team30.account.Login;
import team30.recipeList.VoiceRecorder.RecordingCompletionListener;
import team30.server.RecipeDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import javafx.scene.paint.Color;

import com.mongodb.client.FindIterable;
import org.bson.Document;

class List extends VBox {

    List() {
        this.setSpacing(10); // sets spacing between tasks
        
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #f8f3c9;-fx-padding: 10;");
    }

    //updates indices on recipes in list
    public void updateTaskIndices() {
        int index = this.getChildren().size();
        for (int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof Recipe) {
                ((Recipe) this.getChildren().get(i)).setTaskIndex(index);
                index--;
            }
        }
    }

    //removes recipe from list
    void removeRecipe(Recipe recipeToRemove){
        this.getChildren().removeIf(recipe -> recipe instanceof Recipe && ((Recipe)recipe).equals(recipeToRemove));
        this.updateTaskIndices();
    }
}

class Header extends HBox {

    private Text titleText;
    private Button addButton;
    private Image recipe_Image;
    private Button logoutButton;

    Header() {
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #f8f3c9;");

        titleText = new Text("PantryPal");
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 40;");
        this.getChildren().add(titleText);
        titleText.setFill(Color.GRAY); // Set the font color
        
        this.setMargin(this.getTitleText(), new Insets(0, 150, 0, 0));

        addButton = new Button("Generate");
        setButtonStyle(addButton);
        this.setMargin(this.getAddButton(), new Insets(0, 10, 0, 0));
        logoutButton = new Button("Log Out");
        setButtonStyle(logoutButton);
        this.getChildren().addAll(addButton, logoutButton);
        this.setAlignment(Pos.CENTER_LEFT);
    }

    public Text getTitleText() {return titleText;}

    public Button getAddButton() {
        return addButton;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }

    public void setButtonStyle(Button button) {
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #a1f2c8;  -fx-font-weight: bold; -fx-font: 15 arial; -fx-background-radius: 10";
        button.setStyle(defaultButtonStyle);
        // Adding hover effect
        button.setOnMouseEntered(e -> button.setStyle("-fx-font-style: italic; -fx-background-color: #7dedb3;  -fx-font-weight: bold; -fx-font: 15 arial; -fx-background-radius: 10"));
        button.setOnMouseExited(e -> button.setStyle("-fx-font-style: italic; -fx-background-color: #a1f2c8;  -fx-font-weight: bold; -fx-font: 15 arial; -fx-background-radius: 10"));
        
        // Adding click effect
        button.setOnMousePressed(e -> button.setStyle("-fx-font-style: italic; -fx-background-color: #117e2c;  -fx-font-weight: bold; -fx-font: 15 arial; -fx-background-radius: 10"));
        button.setOnMouseReleased(e -> button.setStyle("-fx-font-style: italic; -fx-background-color: #a1f2c8;  -fx-font-weight: bold; -fx-font: 15 arial; -fx-background-radius: 10"));
    }

    public void setButtonStyle(Button button, String style, String hover_enter, String hover_exist, String click_press, String click_release) {
        button.setStyle(style);

        // Adding hover effect
        button.setOnMouseEntered(e -> button.setStyle(hover_enter));
        button.setOnMouseExited(e -> button.setStyle(hover_exist));
        
        // Adding click effect
        button.setOnMousePressed(e -> button.setStyle(click_press));
        button.setOnMouseReleased(e -> button.setStyle(click_release));
    }
}


class AppFrame extends BorderPane implements RecordingCompletionListener {

    private Header header;
    private List recipeList;
    private ScrollPane scrollPane;

    private Recipe recipe;
    private Button addButton;
    private Button logoutButton;
    private RecipeList rl;

    //unseen buttons for HTTP functions
    private Button postButton, getButton, putButton, deleteButton;
    private String query;

    private RecipeDatabase recipeDB;

    //voice recorder popup
    VoiceRecorder voiceRecorder;

    AppFrame() {
        header = new Header();
        recipeList = new List();

        scrollPane = new ScrollPane(recipeList);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        this.setTop(header);
        this.setCenter(scrollPane);

        addButton = header.getAddButton();
        logoutButton = header.getLogoutButton();

        postButton = new Button("Post");
        getButton = new Button("Get");
        putButton = new Button("Put");
        deleteButton = new Button("Delete");

        recipe = new Recipe();
        query = "";

        recipeDB = new RecipeDatabase();
        
        loadRecipes();
        addListeners();
    }

    public void getVoiceRecording() {
        System.out.println("starting voice recording...");
        voiceRecorder = new VoiceRecorder(rl, this);
        voiceRecorder.setCompletionListener(this);
        voiceRecorder.openDetailWindow();
    }

    public Button getLogoutButton() {return logoutButton;}

    public void addListeners() {
        addButton.setOnAction(e -> {
            getVoiceRecording();

            String ingredientsRaw = voiceRecorder.getIngredientAudio();
            String mealtype = voiceRecorder.getMealType();

            if (voiceRecorder.successfulRecording()) {
                onRecordingCompleted(mealtype, ingredientsRaw);
            }
        });  
    }

    public void onRecordingCompleted(String mealType, String ingredientsRaw) {
        // This method will be called when the voice recording is completed
        // generate recipe with chatGPT
        System.out.println("Recording completed!");
        System.out.println("Meal Type: " + mealType);
        System.out.println("Ingredients: " + ingredientsRaw);

        ChatGPT chatGPT = new ChatGPT();
        try {
            Recipe cur = chatGPT.makeRecipeByChatGPTResponse(mealType, ingredientsRaw, rl.getUsername());
            addRecipe(cur);

            RecipeDetail tmp = new RecipeDetail(rl, this, cur);
            tmp.setCancellable(true);
            System.out.println("OPENING NEW RECIPE...");
            tmp.openDetailWindow(cur);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public void addRecipe(Recipe cur) {
        FXCollections.reverse(recipeList.getChildren());
        recipeList.getChildren().add(cur);
        recipeList.updateTaskIndices();
        postButton.fire(); //click HTTP post button

        cur.getRecipeTitle().setOnAction(f -> {
            RecipeDetail ord = new RecipeDetail(rl, this, cur);
            ord.openDetailWindow(cur);
        });
        
        FXCollections.reverse(recipeList.getChildren());
    }

    public void loadRecipes() {
        try {
            long totalRecipes = recipeDB.countDocuments();
            System.out.println("Total recipes: " + totalRecipes);
            recipeList.getChildren().clear();

            String username = rl.getUsername();
            FindIterable<Document> iterDoc = recipeDB.find_by_user(username);
            Iterator<Document> it = iterDoc.iterator();
            while (it.hasNext()) {
                Recipe cur = recipeDB.getRecipe(it.next(), username);
                
                addRecipe(cur);
            }
        }
        catch (Exception e) {
            System.out.println("couldn't open database!");
        }
    }

    public String getRecipeName() {
        return recipe.getRecipeTitle().getText();
    }

    public String[] getRecipeDetails() {
        String[] details = new String[3];
        details[0] = recipe.getMealType();
        details[1] = recipe.getIngredients();
        details[2] = "";
        for (int i = 0; i < recipe.getSteps().size(); ++i) {
            details[2] += recipe.getSteps().get(i);
        }
        return details;
    }

    public Button getPostButton() {return postButton;}
    public Button getGetButton() {return getButton;}
    public Button getPutButton() {return putButton;}
    public Button getDeleteButton() {return deleteButton;}
    public String getQuery() {return query;}
    public RecipeDatabase getRecipeDB() {return recipeDB;}

    public void setRecipeList(RecipeList rl) {this.rl = rl;}
    public Header getHeader() {return header;}
    public List getRecipeList() {return recipeList;}
    public ScrollPane getScrollPane() {return scrollPane;}
    public Button getAddButton() {return addButton;}
    public Recipe getRecipe() {return recipe;}
}

// edited from public class Main
public class RecipeList extends Application {
    private AppFrame root;
    private Stage primStage;
    private Scene listScene;
    private Button postButton, getButton, putButton, deleteButton;
    Controller controller;
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
        root = new AppFrame();
        Model model = new Model();
        listScene = new Scene(root, 500, 600);
        login = new Login();
        loginScene = new Scene(login, 250, 300);
        createAccount = new CreateAccount();
        createAccountScene = new Scene(createAccount, 250, 300);
        
        postButton = root.getPostButton();
        getButton = root.getGetButton();
        putButton = root.getPutButton();
        deleteButton = root.getDeleteButton();
        logoutButton = root.getLogoutButton();
        
        controller = new Controller(this, model);
        
        loginButton = login.getLoginButton();
        loginCreateButton = login.getCreateButton();
        createAccountBackButton = createAccount.getBackButton();
        createAccountCreateButton = createAccount.getCreateButton();

        addLoginListeners();

        this.primStage = primaryStage;
        root.setRecipeList(this);
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
                root.loadRecipes();
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
    public void setAppFrame(AppFrame af) {root = af;}

    public void setPostButtonAction(EventHandler<ActionEvent> eventHandler) {postButton.setOnAction(eventHandler);}
    public void setGetButtonAction(EventHandler<ActionEvent> eventHandler) {getButton.setOnAction(eventHandler);}
    public void setPutButtonAction(EventHandler<ActionEvent> eventHandler) {putButton.setOnAction(eventHandler);}
    public void setDeleteButtonAction(EventHandler<ActionEvent> eventHandler) {deleteButton.setOnAction(eventHandler);}

    public String getRecipeName() {return root.getRecipeName();}
    public String[] getRecipeDetails() {return root.getRecipeDetails();}
    public String getQuery() {return root.getQuery();}
    public String getUsername() {return username;}
    public AppFrame getRoot() {return root;}

    public void showAlert(String title, String content) {
        // Alert alert = new Alert(Alert.AlertType.INFORMATION);
        // alert.setTitle(title);
        // alert.setHeaderText(null);
        // alert.setContentText(content);
        // alert.showAndWait();
    }

    public void addLoginListeners() {
        loginButton.setOnAction(e -> {
            int match = login.validUser();
            if (match == 0) {
                username = login.getUsername();
                System.out.println("Welcome user: " + username);
                generateAutoLoginFile();
                root.loadRecipes();
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