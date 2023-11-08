package team30.recipeList;

import javafx.application.Application;
import javafx.collections.ArrayChangeListener;
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
import team30.meal.MealType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import javafx.scene.paint.Color;

class DetailFooter extends HBox {

    private Button edit;
    private Button delete;

    DetailFooter() {
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);

        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

        edit = new Button("Load");
        edit.setStyle(defaultButtonStyle);
        delete = new Button("Save");
        delete.setStyle(defaultButtonStyle);

        this.getChildren().addAll(edit, delete);
        this.setAlignment(Pos.CENTER);
    }

    public Button getEdit() {return edit;}
    public Button getDelete() {return delete;}
}

class DetailHeader extends HBox {

    private Button save;
    private Button back;

    DetailHeader() {
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);

        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

        save = new Button("Load");
        save.setStyle(defaultButtonStyle);
        back = new Button("Save");
        back.setStyle(defaultButtonStyle);

        this.getChildren().addAll(save, back);
        this.setAlignment(Pos.CENTER);
    }

    public Button getSave() {return save;}
    public Button getBack() {return back;}
}

public class OpenRecipeDetail {
    private AppFrame originalAF;
    private RecipeList rl;

    OpenRecipeDetail() { };

    public void openDetailWindow(Recipe recipe, AppFrame af, RecipeList rl) {
        originalAF = af;
        af = createDetailView();

        rl.getPrimStage().setScene(new Scene(af, 500, 600));
        // TODO: go further
    }

    public void closeDetailWindow(List ls) {}

    private AppFrame createDetailView() {
        AppFrame detailView = new AppFrame();
        ScrollPane dScrollPane = new ScrollPane(new List());
        DetailHeader dhead = new DetailHeader();
        DetailFooter dfooter = new DetailFooter(); 
        
        detailView.setTop(dhead);
        detailView.setCenter(dScrollPane);
        detailView.setBottom(dfooter);

        
        return detailView;
    }

    public AppFrame getOriginalAppFrame() {return originalAF;}
}
