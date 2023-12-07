package team30.recipeList;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.sun.glass.ui.Window;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.MenuButton;
import team30.server.ChatGPT;
import team30.server.RecipeDatabase;
import team30.server.VoiceRecorder;
import team30.server.VoiceRecorder.RecordingCompletionListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

// UI class for entire recipe list sreen
public class RecipeListUI extends DefaultBorderPane /*implements Observer*/ implements RecordingCompletionListener { //implements RecordingCompletionListener
    private VBox recipeListUI;
    private ScrollPane scrollPane;

    private Recipe recipe;
    private Button addButton;

    //unseen buttons for HTTP functions
    private Button postButton, getButton, putButton, deleteButton, shareButton;
    private Button chatGPTButton;
    private String query;

    //loaded when server starts
    private RecipeDatabase recipeDB; 
    private List<Recipe> recipeList;

    private String endMealType, endIngredients;
    private String recipeRaw;

    private Button logoutButton;
    private MenuButton sortButton;
    private MenuButton filterButton;

    private String username;

    //voice recorder popup
    VoiceRecorder voiceRecorder;

    private static WindowChange windowChange;

    public RecipeListUI() {
        recipeListUI = new VBox();
        recipeListUI.setSpacing(10); // sets spacing between tasks   
        recipeListUI.setPrefSize(500, 560);
        recipeListUI.setStyle("-fx-background-color: " + tanLight + ";-fx-padding: 10;");

        scrollPane = new ScrollPane(recipeListUI);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        this.setCenter(scrollPane);

        addButton = new Button("Generate");
        setButtonStyle(addButton);
        addButton.setAlignment(Pos.CENTER_RIGHT);
        header.setSpacing(20);

        sortButton = new MenuButton("Sort");
        sortButton.setMinWidth(100);
        sortButton.setStyle("-fx-font-style: italic; -fx-background-color: #a1f2c8;  -fx-font-weight: bold; -fx-font: 13 arial; -fx-background-radius: 10;");
        sortButton.setAlignment(Pos.CENTER);

        filterButton = new MenuButton("Filter");
        filterButton.setMinWidth(100);
        filterButton.setStyle("-fx-font-style: italic; -fx-background-color: #a1f2c8;  -fx-font-weight: bold; -fx-font: 13 arial; -fx-background-radius: 10;");
        filterButton.setAlignment(Pos.CENTER);

        header.getChildren().addAll(addButton);

        footer.getChildren().addAll(sortButton, filterButton);

        postButton = new Button("Post");
        getButton = new Button("Get");
        putButton = new Button("Put");
        deleteButton = new Button("Delete");
        chatGPTButton = new Button();
        shareButton = new Button("Share");
        query = "";

        recipeDB = new RecipeDatabase();

        logoutButton = new Button("Logout");
        setButtonStyle(logoutButton);
        logoutButton.setAlignment(Pos.CENTER_RIGHT);
        footer.setSpacing(20);
        footer.getChildren().add(logoutButton);

        voiceRecorder = new VoiceRecorder();
        voiceRecorder.setCompletionListener(this);

        recipeList = new ArrayList<Recipe>();

        windowChange = new WindowChange();
        windowChange.setVoiceRecorder(voiceRecorder);


        addListeners();
    }

    //updates indices on recipes in list
    public void updateTaskIndices() {
        int index = 1;
        for (int i = 0; i < recipeListUI.getChildren().size(); i++) {
            if (recipeListUI.getChildren().get(i) instanceof RecipeUI) {
                ((RecipeUI) recipeListUI.getChildren().get(i)).setTaskIndex(index);
                index++;
            }
        }
    }

    public void update() {
        recipeListUI.getChildren().clear();
        for (Recipe cur : recipeList) {
            RecipeUI recipeUI = new RecipeUI(cur);
            recipeListUI.getChildren().add(recipeUI);
            recipeUI.getRecipeTitle().setOnAction(f -> {
                recipe = cur;
                RecipeDetail ord = new RecipeDetail(cur, this);
                windowChange.openWindow(ord);
            });
        }
        this.updateTaskIndices();
    }

    public void getVoiceRecording() {
        windowChange.openWindow(voiceRecorder);
    }

    public void addListeners() {
        addButton.setOnAction(e -> {
            getVoiceRecording();

            String ingredientsRaw = voiceRecorder.getIngredientAudio();
            String mealtype = voiceRecorder.getMealType();

            if (voiceRecorder.successfulRecording()) {
                onRecordingCompleted(mealtype, ingredientsRaw);
            }
        });  

        MenuItem sortAZ = new MenuItem("A-Z");
        MenuItem sortZA = new MenuItem("Z-A");
        MenuItem newOld = new MenuItem("Newest to Oldest");
        MenuItem oldNew = new MenuItem("Oldest to Newest");
        MenuItem defaultSort = new MenuItem("Default");
        sortButton.getItems().addAll(sortAZ, sortZA, newOld, oldNew, defaultSort);
        sortAZ.setOnAction(e -> {
            sortButton.setText("A-Z");
            sortAtoZ();
        });
        sortZA.setOnAction(e -> {
            sortButton.setText("Z-A");
            sortZtoA();
        });
        newOld.setOnAction(e -> {
            sortButton.setText("Newest to Oldest");
            newToOld();
        });
        oldNew.setOnAction(e -> {
            sortButton.setText("Oldest to Newest");
            oldToNew();
        });
        defaultSort.setOnAction(e -> {
            sortButton.setText("Default");
            newToOld();
        });

        MenuItem breakfastItem = new MenuItem("Breakfast");
        MenuItem lunchItem = new MenuItem("Lunch");
        MenuItem dinnerItem = new MenuItem("Dinner");
        MenuItem defaultItem = new MenuItem("Default");
        filterButton.getItems().addAll(breakfastItem, lunchItem, dinnerItem, defaultItem);
        breakfastItem.setOnAction(e -> {
            filterButton.setText("Breakfast");
            breakfastFilter();
        });
        lunchItem.setOnAction(e -> {
            filterButton.setText("Lunch");
            lunchFilter();
        });
        dinnerItem.setOnAction(e -> {
            filterButton.setText("Dinner");
            dinnerFilter();
        });
        defaultItem.setOnAction(e -> {
            filterButton.setText("Default");
            newToOld();
        });
    }

    public void breakfastFilter() {
        ArrayList<Recipe> new_order = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            if (recipe.getMealType().toLowerCase().equals("breakfast"))
                new_order.add(recipe);
        }
        update(new_order);
    }
    public void lunchFilter() {
        ArrayList<Recipe> new_order = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            if (recipe.getMealType().toLowerCase().equals("lunch"))
                new_order.add(recipe);
        }
        update(new_order);
    }
    public void dinnerFilter() {
        ArrayList<Recipe> new_order = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            if (recipe.getMealType().toLowerCase().equals("dinner"))
                new_order.add(recipe);
        }
        update(new_order);
    }

    public void onRecordingCompleted(String mealType, String ingredientsRaw) {
        // generate recipe with chatGPT
        System.out.println("Recording completed!");
        System.out.println("Meal Type: " + mealType);
        System.out.println("Ingredients: " + ingredientsRaw);

        try {
            // Generate recipe
            endMealType = mealType;
            endIngredients = ingredientsRaw;
            chatGPTButton.fire();
            String generatedRecipe = recipeRaw;
            System.out.println("Generated Recipe: ");
            System.out.println(recipeRaw);

            String[] lines = generatedRecipe.split("\\r?\\n|\\r");
            String recipeName = "", ingredients = "", imgurl = "";
            ArrayList<String> steps = new ArrayList<>();
            int count = 0; //0- recipeName, 1- ingredients, 2- instructions
            for (int i = 0; i < lines.length; i++) {
                System.out.println(lines[i]);
                if (!(lines[i].replaceAll("\\s", "") == "") && !(lines[i].replaceAll("\\n", "") == "") && count == 0) {
                    //recipeName (unlabelled)
                    recipeName = lines[i].toLowerCase();
                    count = 100;
                }
                if (lines[i].toLowerCase().contains("recipe name: ")) {
                    //recipeName (labelled)
                    recipeName = lines[i].substring(13).toLowerCase();
                    count = 100;
                }
                else if (lines[i].toLowerCase().contains("recipe title: ")) {
                    //recipeName (labelled)
                    recipeName = lines[i].substring(14).toLowerCase();
                    count = 100;
                }
                if (lines[i].contains("Ingredients:")) {
                    count = 1;
                    continue;
                }
                else if (lines[i].contains("Instructions:")) {
                    count = 2;
                    continue;
                }
                if (count == 1) {
                    //ingredients
                    if (!ingredients.equals("") && !(lines[i].replaceAll("\\n", "") == ""))
                        ingredients += ", ";
                    ingredients += lines[i].toLowerCase().replaceAll("-", "");
                }
                if (count == 2) {
                    //steps
                    if (!(lines[i].replaceAll("\\s", "") == "") && !(lines[i].replaceAll("\\n", "") == ""))
                        steps.add(lines[i]);
                }
            }
            Recipe cur = new Recipe(recipeName, mealType, ingredients, steps, imgurl, true, username);
            addRecipe(cur);

            RecipeDetail tmp = new RecipeDetail(cur, this);
            recipe = cur;
            tmp.setCancellable(true);
            System.out.println("Opening new recipe...");
            windowChange.openWindow(tmp);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public void addRecipe(Recipe cur) {
        Collections.reverse(recipeList);
        recipeList.add(cur);
        Collections.reverse(recipeList);
        update();
    }

    public void loadRecipes() {
        try {
            recipeList.clear();
            FindIterable<Document> iterDoc = recipeDB.find_by_user(username);
            Iterator<Document> it = iterDoc.iterator();
            while (it.hasNext()) {
                Recipe cur = recipeDB.getRecipe(it.next(), username);
                addRecipe(cur);
            }
        
        }
        catch (Exception e) {
            System.out.println("load recipes: couldn't open database!");
            e.printStackTrace();
        }
    }

    public void update(List<Recipe> order) {
        recipeListUI.getChildren().clear();
        for (Recipe cur : order) {
            RecipeUI recipeUI = new RecipeUI(cur);
            recipeListUI.getChildren().add(recipeUI);
            recipeUI.getRecipeTitle().setOnAction(f -> {
                recipe = cur;
                RecipeDetail ord = new RecipeDetail(cur, this);
                windowChange.openWindow(ord);
            });
        }
        this.updateTaskIndices();
    }

    public void sortAtoZ() {
        ArrayList<Recipe> new_order = new ArrayList<>();
        for (Recipe recipe : recipeList)
            new_order.add(recipe);
        // Sort the list of recipes from A to Z
        Comparator<Recipe> compareAtoZ = new Comparator<Recipe>() {
            @Override
            public int compare(Recipe r1, Recipe r2) {
                return r1.getRecipeTitle().compareTo(r2.getRecipeTitle());
            }
        };
        Collections.sort(new_order, compareAtoZ);
        update(new_order);
    }

    public void sortZtoA() {
        ArrayList<Recipe> new_order = new ArrayList<>();
        for (Recipe recipe : recipeList)
            new_order.add(recipe);   
        // Sort the list of recipes from A to Z
        Comparator<Recipe> compareAtoZ = new Comparator<Recipe>() {
            @Override
            public int compare(Recipe r1, Recipe r2) {
                return r2.getRecipeTitle().compareTo(r1.getRecipeTitle());
            }
        };
        Collections.sort(new_order, compareAtoZ);
        update(new_order);
    }

    public void oldToNew() {
        ArrayList<Recipe> new_order = new ArrayList<>();
        for (Recipe recipe : recipeList)
            new_order.add(recipe);  
        Collections.reverse(new_order);
        update(new_order);

        // RecipeListUI old_to_new_recipes = new RecipeListUI();
        // old_to_new_recipes.getChildren().addAll(recipeListUI.getChildren());
        // recipeListUI.getChildren().clear();
        // recipeListUI.getChildren().addAll(old_to_new_recipes);
    }

    public void newToOld() {
        ArrayList<Recipe> new_order = new ArrayList<>();
        for (Recipe recipe : recipeList)
            new_order.add(recipe);  
        update(new_order);

        // RecipeListUI old_to_new_recipes = new RecipeListUI();
        // old_to_new_recipes.getChildren().addAll(recipeListUI.getChildren());
        // recipeListUI.getChildren().clear();
        // Collections.reverse(old_to_new_recipes.getChildren());
        // recipeListUI.getChildren().addAll(old_to_new_recipes);    
    }

    public ObjectId getRecipeObjectID() { return recipe.getObjectID(); }
    public Button getPostButton() {return postButton;}
    public Button getGetButton() {return getButton;}
    public Button getPutButton() {return putButton;}
    public Button getDeleteButton() {return deleteButton;}
    public Button getChatGPTButton() {return chatGPTButton;}
    public String getQuery() {return query;}
    public void setQuery(String q) {query = q;}
    public String getMealType() {return endMealType;}
    public String getIngredients() {return endIngredients;}
    public void setRecipeRaw(String s) {recipeRaw = s;}
    public RecipeDatabase getRecipeDB() {return recipeDB;}
    public VoiceRecorder getVoiceRecorder() {return voiceRecorder; }

    public HBox getHeader() {return header;}
    public List<Recipe> getRecipeList() {return recipeList;}
    public ScrollPane getScrollPane() {return scrollPane;}
    public Button getAddButton() {return addButton;}
    public Button getLogoutButton() {return logoutButton;}
    public Recipe getRecipe() {return recipe;}
    public void setUsername(String u) {username = u;}

    public Button getShareButton() {return shareButton;}
}
