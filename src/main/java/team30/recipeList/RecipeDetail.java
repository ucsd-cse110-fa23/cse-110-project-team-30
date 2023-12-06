package team30.recipeList;

import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

//helper UI class for info in scroll pane
class RecipeDetailUI extends VBox {
    // recipe info
    private Label recipe_name;
    private TextArea ingredients;
    private ArrayList<TextArea> steps;
    private TextField mealtype;
    private Image recipeImage;

    Recipe recipe;

    /**
     * Takes an image from the recipe's url, and if the recipe has not had an image generated for it, 
     * generate one to return. It also changes imageGenerated to true to reflect the changes.
     * @return the linked image if generatedImage is true, and a newly generated image if not.
     */
    public Image getImage(){
        String imageurl = ImageManager.ensurePathExists(recipe.getImageURL(), recipe.getRecipeTitle(), !recipe.getImageGenerated());
        recipe.setImageGenerated(true);
        return ImageManager.getImage(imageurl);
    }

    RecipeDetailUI (Recipe r) {
        this.recipe = r;
        
        this.setPrefSize(500, 800); // sets size of task
        this.setMaxHeight(VBox.USE_PREF_SIZE); 
        this.setMinHeight(VBox.USE_PREF_SIZE);
        this.setSpacing(15);

        // initial recipe info
        recipe_name = new Label(recipe.getRecipeTitle());
        ingredients = new TextArea(recipe.getIngredients());
        steps = new ArrayList<>();
        for (int i = 0; i < recipe.getSteps().size(); ++i) {
            steps.add(new TextArea(recipe.getSteps().get(i)));
        }
        mealtype = new TextField(recipe.getMealType());

        // adding first recipe_name & mealtype row
        HBox title_mealtype_HBox = new HBox();
        title_mealtype_HBox.setPrefSize(450, 60); // sets size of task
        title_mealtype_HBox.setMaxHeight(HBox.USE_PREF_SIZE); 
        title_mealtype_HBox.setMinHeight(HBox.USE_PREF_SIZE);
        title_mealtype_HBox.setSpacing(15);
        title_mealtype_HBox.setAlignment(Pos.BASELINE_CENTER);

        recipe_name.setPrefSize(315, 40);
        recipe_name.setStyle("-fx-background-color: #e5da3e; -fx-border-width: 1.5px; -fx-border-insets: 5, -fx-background-insets: 5; -fx-border-color: black; -fx-background-radius: 10; -fx-border-radius: 10;-fx-font-size: 20;-fx-alignment: CENTER");
        mealtype.setStyle("-fx-background-color: #EDDCF0; -fx-background-radius: 20;-fx-alignment: CENTER;-fx-font-size: 20");
        mealtype.setPrefSize(120, 30);
        
        title_mealtype_HBox.getChildren().add(recipe_name);
        title_mealtype_HBox.getChildren().add(mealtype);


        //Add second row: Image
        ImageView imageView = new ImageView();
        recipeImage = getImage();
        imageView.setImage(recipeImage);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(false);
        VBox image_VBox = new VBox(imageView);
        
        image_VBox.setPrefSize(200, 200); // sets size of task
        image_VBox.setMaxHeight(VBox.USE_PREF_SIZE);
        image_VBox.setMinHeight(VBox.USE_PREF_SIZE);
        image_VBox.setSpacing(15);
        image_VBox.setAlignment(Pos.CENTER);

        // adding third row: ingredients
        HBox ingredients_HBox = new HBox();
        ingredients_HBox.setPrefSize(450, 70);
        ingredients_HBox.setMaxHeight(HBox.USE_PREF_SIZE); 
        ingredients_HBox.setMinHeight(HBox.USE_PREF_SIZE);
        ingredients_HBox.setSpacing(20);
        ingredients_HBox.setAlignment(Pos.BOTTOM_LEFT);
        
        Label ingredients_title = new Label("Ingredients");
        ingredients_title.setStyle("-fx-background-color: #CDFADC; -fx-background-radius: 20; -fx-alignment: CENTER;-fx-font-size: 20");
        ingredients_title.setPrefSize(130, 40);

        ingredients.setPrefSize(300, 60);
        ingredients.setStyle("-fx-padding: 0 0 0 20;-fx-background-color: #e5da3e; -fx-border-width: 1px; -fx-border-color: black; -fx-background-radius: 10; -fx-border-radius: 10;-fx-font-size: 13;-fx-alignment: TOP_LEFT;");
        ingredients.setWrapText(true); 

        ingredients_HBox.getChildren().add(ingredients_title);
        ingredients_HBox.getChildren().add(ingredients);
        
        // adding steps
        VBox steps_VBox = new VBox();
        steps_VBox.setPrefSize(450, 460); // sets size of task
        steps_VBox.setMaxHeight(VBox.USE_PREF_SIZE); 
        steps_VBox.setMinHeight(VBox.USE_PREF_SIZE);
        steps_VBox.setSpacing(15);
        Label steps_title = new Label("Steps");
        steps_title.setStyle("-fx-background-color: #CDF7FA; -fx-background-radius: 20; -fx-alignment: CENTER;-fx-font-size: 20");
        steps_title.setPrefSize(80, 40);
        steps_VBox.getChildren().add(steps_title);
        
        int index_num = 1;
        for (int i = 0; i < steps.size(); ++i, index_num++) {
            HBox individual_step_HBox = new HBox();
            individual_step_HBox.setPrefSize(400, 30);
            individual_step_HBox.setMaxHeight(HBox.USE_PREF_SIZE); 
            individual_step_HBox.setMinHeight(HBox.USE_PREF_SIZE);
            individual_step_HBox.setSpacing(20);
            individual_step_HBox.setAlignment(Pos.CENTER);

            // index number
            Label index = new Label();
            index.setStyle("-fx-background-color: #e5da3e; -fx-background-radius: 20");
            index.setText(String.valueOf(index_num)); // create index label
            index.setPrefSize(50, 30); // set size of Index label
            index.setTextAlignment(TextAlignment.CENTER); // Set alignment of index label
            index.setAlignment(Pos.CENTER);
            index.setPadding(new Insets(0, 0, 0, 0)); // adds some padding to the task

            steps.get(i).setPrefSize(350, 30);
            steps.get(i).setStyle("-fx-padding: 0 0 0 20;-fx-background-color: #e5da3e; -fx-border-width: 1px; -fx-border-color: black; -fx-background-radius: 10; -fx-border-radius: 10;-fx-font-size: 12;-fx-alignment: TOP_LEFT;");
            steps.get(i).setWrapText(true);
            steps.get(i).setMouseTransparent(true);
            steps.get(i).setEditable(false);


            individual_step_HBox.getChildren().add(index);
            individual_step_HBox.getChildren().add(steps.get(i));

            steps_VBox.getChildren().add(individual_step_HBox);
        }

        this.getChildren().add(title_mealtype_HBox);
        //add image
        this.getChildren().add(image_VBox);
        this.getChildren().add(ingredients_HBox);
        this.getChildren().add(steps_VBox);
        this.setSpacing(10);
    }

    public Label getRecipeName() {return recipe_name;}
    public TextArea getIngredients() {return ingredients;}
    public ArrayList<TextArea> getSteps() {return steps;}
    public TextField getMealType() {return mealtype;}
}

//Recipe detail UI class
public class RecipeDetail extends DefaultBorderPane {
    RecipeListUI recipeListUI;
    private RecipeDetailUI dRecipe;
    private Recipe recipe;

    private boolean editMode;

    Scene recipeViewScene;

    //HTTP buttons
    private Button postButton, getButton, putButton, deleteButton, shareButton;
    private WindowChange windowChange;

    Button back, save, edit, delete, cancel, share;

    RecipeDetail(Recipe r, RecipeListUI rlu) {
        recipeListUI = rlu;
        recipe = r;

        ScrollPane scrollPane;

        dRecipe = new RecipeDetailUI(recipe);
        scrollPane = new ScrollPane(dRecipe);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        this.setCenter(scrollPane);

        //footer
        footer.setPrefSize(500, 40);
        edit = new Button("Edit");
        delete = new Button("Delete");
        save = new Button("Save");
        share = new Button("Share");
        back = new Button("Back");
        cancel = new Button("Cancel");
        setButtonStyle(back);
        setButtonStyle(edit);
        setButtonStyle(delete);
        setButtonStyle(share);
        setButtonStyle(save);
        setButtonStyle(cancel);
        cancel.setVisible(false);
        footer.setSpacing(10);
        footer.getChildren().addAll(back, save, edit, delete, share, cancel);

        addListeners(back, save, edit, delete, share, cancel);

        postButton = recipeListUI.getPostButton();
        getButton = recipeListUI.getGetButton();
        putButton = recipeListUI.getPutButton();
        deleteButton = recipeListUI.getDeleteButton();
        shareButton = recipeListUI.getShareButton();

        this.disableEdit();

        recipeViewScene = new Scene(this, 500, 600);

        windowChange = new WindowChange();
        windowChange.setRecipeDetail(this);
    };

    public void openDetailWindow() { windowChange.openWindow(this); }
    public void closeDetailWindow() { disableEdit(); windowChange.closeWindow(); }

    public void enableEdit() {
        editMode = true;
        edit.setText("Stop");
        dRecipe.getIngredients().setEditable(true);
        dRecipe.getMealType().setEditable(true);
        for (int i = 0; i < dRecipe.getSteps().size(); ++i) {
            dRecipe.getSteps().get(i).setEditable(true);
            dRecipe.getSteps().get(i).setMouseTransparent(false);
        }
    }

    public void disableEdit() {
        editMode = false;
        edit.setText("Edit");
        dRecipe.getIngredients().setEditable(false);
        dRecipe.getMealType().setEditable(false);
        for (int i = 0; i < dRecipe.getSteps().size(); ++i) {
            dRecipe.getSteps().get(i).setEditable(false);
            dRecipe.getSteps().get(i).setMouseTransparent(true);
        }
    }

    public void updateRecipeList() {
        recipe.setMealType(dRecipe.getMealType().getText());
        recipe.setIngredients(dRecipe.getIngredients().getText());
        recipe.getSteps().clear();
        for (int i = 0; i < dRecipe.getSteps().size(); ++i) {
            recipe.getSteps().add(dRecipe.getSteps().get(i).getText());
        }
    }

    public void setCancellable(boolean b) { cancel.setVisible(b); }

    public void editEvent(){
        if (editMode) disableEdit();
        else enableEdit();
    }

    public void deleteEvent(){
        recipeListUI.getRecipeList().remove(this.recipe);
        recipeListUI.update();
        recipeListUI.setQuery(this.recipe.getObjectID().toString());
        deleteButton.fire(); //server delete
        closeDetailWindow();
    }

    public void shareEvent() {
        //set server vars
        recipeListUI.setQuery(this.recipe.getObjectID().toString());
        shareButton.fire(); //server share
        showShareLink("http://localhost:8100/share/?=" + this.recipe.getObjectID().toString());
    }

    public void showShareLink(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Share Link");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Runs the event that happens whenever save button is pressed.
     * @param showAlert - a flag that says whether to show the alert or not.
     */
    public void saveEvent(boolean showAlert){
        disableEdit();
        updateRecipeList();
        putButton.fire(); //server put
    }
    
    public void addListeners(Button back, Button save, Button edit, Button delete, Button share, Button cancel) {
        // listener for Back
        back.setOnAction(e -> {
            setCancellable(false);
            closeDetailWindow();
        });
        // listener for save
        save.setOnAction(e -> {
            saveEvent(true);
        });
        // listener for edit
        edit.setOnAction(e -> {
            editEvent();
        });
        // listener for delete
        delete.setOnAction(e -> {
            deleteEvent();
        });
        // listener for share
        share.setOnAction(e -> {
            shareEvent();
        });
        // listener for cancel
        cancel.setOnAction(e -> {
            //don't save recipe to list
            recipeListUI.getRecipeList().remove(this.recipe);
            recipeListUI.update();
            closeDetailWindow();
        });
    }

    public Button getPostButton() {return postButton;}
    public Button getGetButton() {return getButton;}
    public Button getPutButton() {return putButton;}
    public Button getDeleteButton() {return deleteButton;}
}
