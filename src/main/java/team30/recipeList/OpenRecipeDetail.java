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

class Footer extends HBox {

    private Button edit;
    private Button delete;

    Footer() {
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

public class OpenRecipeDetail {
    private Button save;
    private Button back;
    private Button edit;
    private Button delete;
    private AppFrame originalAF;

    OpenRecipeDetail(Button save, Button back, Button edit, Button delete) {
        this.save = save;
        this.back = back;
        this.edit = edit;
        this.delete = delete;
    };

    public void openDetailWindow(Recipe recipe, AppFrame af) {
        originalAF = af;
        af = createDetailView();
    }

    public void closeDetailWindow(List ls) {}

    private AppFrame createDetailView() {
        AppFrame detailView = new AppFrame();
        Header detailHeader = detailView.getHeader();
        List detaRecipList = detailView.getRecipeList();
        ScrollPane detaiScrollPane = detailView.getScrollPane();
        
        // remove the original "generate new Recipe Button"
        detailHeader.getChildren().remove(detailHeader.getAddButton());



        return detailView;
    }

    public Button getSaveButton() {return save;}
    public Button getBackButton() {return back;}
    public Button getEditButton() {return edit;}
    public Button getDeleteButton() {return delete;}
    public AppFrame getOriginalAppFrame() {return originalAF;}
}
