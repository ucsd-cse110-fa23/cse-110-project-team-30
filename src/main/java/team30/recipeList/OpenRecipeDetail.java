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

class DetailFooter extends DetailHeader {

    private Button edit;
    private Button delete;

    DetailFooter() {
        super();
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");

        edit = new Button("Edit");
        delete = new Button("Delete");
        this.setButtonStyle(edit);
        this.setButtonStyle(delete);

        this.getChildren().remove(this.getSave());
        this.getChildren().remove(this.getBack());
        this.getChildren().remove(this.getTitleText());
        this.setMargin(edit, new Insets(0, 30, 0, 0));
        this.getChildren().addAll(edit, delete);
        this.setAlignment(Pos.CENTER);
    }

    public Button getEdit() {return edit;}
    public Button getDelete() {return delete;}
}

class DetailRecipe {
    private VBox details;
    
    DetailRecipe(Recipe recipe) {

    }
}

class DetailHeader extends Header {
    private Button save;
    private Button back;

    DetailHeader() {
        super();
        this.getChildren().remove(this.getAddButton());
        
        this.setMargin(this.getTitleText(), new Insets(0, 150, 0, 0));

        this.setSpacing(10);
        save = new Button("Save");
        back = new Button("Back");
        this.setButtonStyle(save);
        this.setButtonStyle(back);

        this.getChildren().addAll(save, back);
        this.setAlignment(Pos.CENTER);
    }

    public Button getSave() {return save;}
    public Button getBack() {return back;}
}

public class OpenRecipeDetail {
    private AppFrame originalAF;
    private RecipeList rl;

    OpenRecipeDetail(RecipeList rl) {this.rl = rl;};

    public void openDetailWindow(Recipe recipe, AppFrame af, RecipeList rl) {
        originalAF = new AppFrame(af.getHeader(), af.getRecipeList(), af.getScrollPane(), af.getAddButton(), rl);
        af = createDetailView();
        
        rl.getPrimStage().setScene(new Scene(af, 500, 600));
        rl.getPrimStage().show();
    }

    public void closeDetailWindow() {
        rl.getPrimStage().setScene(new Scene(originalAF,500, 600));
        rl.getPrimStage().show();
    }

    private AppFrame createDetailView() {
        AppFrame detailView = new AppFrame();
        DetailHeader dhead = new DetailHeader();
        DetailFooter dfooter = new DetailFooter(); 
        
        detailView.setTop(dhead);
        // detailView.setCenter(new Recipe(null));
        detailView.setBottom(dfooter);

        addListeners(dhead.getBack(), dhead.getSave(), dfooter.getEdit(), dfooter.getDelete());
        
        return detailView;
    }

    public AppFrame getOriginalAppFrame() {return originalAF;}

    public void addListeners(Button back, Button save, Button edit, Button delete) {
        // listener for Back
        back.setOnAction(e -> {
            closeDetailWindow();
        });

        // TODO: listener for save

        // TODO: listener for edit

        // TODO: listener for delete
    }
}
