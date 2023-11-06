package team30.recipeList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

class Recipe extends HBox {
    private Label index;
    private Button recipe_name;
}

class AppFrame extends BorderPane {

    private Header header;
    private Footer footer;
    private RecipeList recipeList;

    private ScrollPane scrollPane;

    private Button newRecipe;

    private Button loadButton;
    private Button saveButton;
    private Button sortButton;

    AppFrame() {
        header = new Header();
        contactList = new ContactList();
        footer = new Footer();

        scrollPane = new ScrollPane(contactList);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        this.setTop(header);
        this.setCenter(scrollPane);
        this.setBottom(footer);

        addButton = footer.getAddButton();
        clearButton = footer.getClearButton();
        loadButton = footer.getLoadButton();
        saveButton = footer.getSaveButton();
        sortButton = footer.getSortButton();

        addListeners();
    }

    public void addListeners() {
        addButton.setOnAction(e -> {
            Contact contact = new Contact();
            contactList.getChildren().add(contact);
            Button picButton = contact.getPicButton();
            picButton.setOnAction(e1 -> {
                contact.uploadPic();
            });
            Button selectButton = contact.getSelectButton();
            selectButton.setOnAction(e2 -> {
                contact.toggleSelect();
            });
            contactList.updateTaskIndices();
        });
        clearButton.setOnAction(e -> {
            contactList.removeSelectedContacts();
        });

        loadButton.setOnAction(e -> {
            contactList.loadContacts();
        });

        saveButton.setOnAction(e -> {
            contactList.saveContacts();
        });

        sortButton.setOnAction(e -> {
            contactList.sortNames();
        });
    }
}

// edited from public class Main
public class RecipeList extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AppFrame root = new AppFrame();

        primaryStage.setTitle("PantryPay");
        primaryStage.setScene(new Scene(root, 500, 600));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}