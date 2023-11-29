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
import javafx.util.Pair;
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
import org.bson.types.ObjectId;

// edited from public class Main
// the "App" in MVC
public class RecipeList extends Application {
    private RecipeListUI root;
    private Stage primStage;
    private Scene listScene;
    private Button postButton, getButton, putButton, deleteButton;
    Controller controller;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new RecipeListUI();
        Model model = new Model();
        listScene = new Scene(root, 500, 600);
        
        postButton = root.getPostButton();
        getButton = root.getGetButton();
        putButton = root.getPutButton();
        deleteButton = root.getDeleteButton();
        
        controller = new Controller(this, model);

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

    public void showAlert(String title, String content) {
        // Alert alert = new Alert(Alert.AlertType.INFORMATION);
        // alert.setTitle(title);
        // alert.setHeaderText(null);
        // alert.setContentText(content);
        // alert.showAndWait();
    }
}