package team30.recipeList;

import javafx.application.Application;
import javafx.scene.control.ComboBox;
import javafx.collections.ArrayChangeListener;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
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
    private Button save;
    private Button back;

    DetailFooter() {
        super();
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");

        edit = new Button("Edit");
        delete = new Button("Delete");
        save = new Button("Save");
        back = new Button("Back");
        this.setButtonStyle(back);
        this.setButtonStyle(edit);
        this.setButtonStyle(delete);
        this.setButtonStyle(save);

        this.getChildren().remove(this.getSave());
        this.getChildren().remove(this.getBack());
        this.getChildren().remove(this.getTitleText());
        this.setMargin(save, new Insets(0, 0, 0, 10));
        this.setMargin(delete, new Insets(0, 0, 0, 10));
        this.setMargin(back, new Insets(0, 0, 0, 10));
        this.getChildren().addAll(back, edit, delete, save);
        this.setAlignment(Pos.CENTER_RIGHT);
    }

    public Button getEdit() {return edit;}
    public Button getDelete() {return delete;}
    public Button getSave() {return save;}
    public Button getBack() {return back;}
}

class DetailRecipe extends Recipe{
    
    DetailRecipe() {

    }

    DetailRecipe (Recipe recipe) {

        ComboBox<String> mealtype = new ComboBox<>();
        mealtype.setPromptText(recipe.getMealType().getMealType());
        mealtype.getItems().addAll("Breakfast", "Lunch", "Dinner");
        HBox title_mealtype = new HBox();
        title_mealtype.getChildren().add(new TextField(recipe.getRecipeTitle().getText()));
        title_mealtype.getChildren().add(mealtype);
        this.getChildren().add(title_mealtype);
    }
}

class DetailHeader extends Header {

    DetailHeader() {
        super();
        this.getChildren().remove(this.getAddButton());
        
        this.setMargin(this.getTitleText(), new Insets(0, 210, 0, 0));

        this.setAlignment(Pos.CENTER_LEFT);
    }
}

public class RecipeDetail {
    private AppFrame originalAF;
    private RecipeList rl;

    RecipeDetail(RecipeList rl, AppFrame af) {
        this.rl = rl;
        originalAF = new AppFrame(af.getHeader(), af.getRecipeList(), af.getScrollPane(), af.getAddButton(), rl);
    };

    public void openDetailWindow(Recipe recipe) {
        AppFrame af = createDetailView(recipe);
        
        rl.getPrimStage().setScene(new Scene(af, 500, 600));
    }

    public void closeDetailWindow() {
        rl.getPrimStage().setScene(new Scene(originalAF,500, 600));
    }

    private AppFrame createDetailView(Recipe recipe) {
        AppFrame detailView = new AppFrame();
        DetailHeader dhead = new DetailHeader();
        DetailFooter dfooter = new DetailFooter(); 
        
        detailView.setTop(dhead);
        // TODO: uncomment this to test Detail Recipe
        // detailView.setCenter(new DetailRecipe(recipe));
        detailView.setBottom(dfooter);

        addListeners(dfooter.getBack(), dfooter.getSave(), dfooter.getEdit(), dfooter.getDelete());
        
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
