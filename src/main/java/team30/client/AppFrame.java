package team30.client;

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
import javafx.scene.paint.Color;

import java.util.List;

public class AppFrame extends BorderPane {
    /* 
    private Header header;
    private List<RecipeView> recipeList;
    private RecipeView recipeView;
    private ScrollPane scrollPane;
    private Button addButton;

    private RecipeController recipeController;

    AppFrame() {
        header = new Header(recipeController);
        recipeList = new ArrayList<>();
        recipeView = new RecipeView(recipes, this);

        scrollPane = new ScrollPane(recipeList);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        this.setTop(header);
        this.setCenter(scrollPane);

        Button addButton = header.getAddButton();

        addListeners();
    }

    public void addListeners() {
        addButton.setOnAction(e -> {
            RecipeView recipe = new RecipeView("example");
            recipeList.getChildren().add(recipe);
            recipeList.updateTaskIndices();
        });
    }
    */
}
