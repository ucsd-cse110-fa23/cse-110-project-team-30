package team30.recipeList;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

import javafx.event.ActionEvent;
import team30.server.RecipeDatabase;

public class Controller {
    private RecipeList view;
    private Model model;

    private RecipeListUI recipeListUI; //part of view
    
    private RecipeDatabase recipeDB;

    public Controller(RecipeList view, Model model) {
        this.view = view;
        this.recipeListUI = view.getRecipeListUI();
        this.model = model;

        recipeDB = new RecipeDatabase();
        
        this.view.setPostButtonAction(this::handlePostButton);
        this.view.setGetButtonAction(this::handleGetButton);
        this.view.setPutButtonAction(this::handlePutButton);
        this.view.setDeleteButtonAction(this::handleDeleteButton);
    }

    public void loadRecipes() {
        try {
            long totalRecipes = recipeDB.countDocuments();
            System.out.println("Total recipes: " + totalRecipes);

            FindIterable<Document> iterDoc = recipeDB.find();
            MongoCursor<Document> it = iterDoc.iterator();
            while (it.hasNext()) {
                Recipe cur = recipeDB.getRecipe(it.next());
                recipeListUI.addRecipe(cur);
            }
        }
        catch (Exception e) {
            System.out.println("couldn't open database!");
        }
    }

    public void saveRecipe(Recipe r) {
        try {
            recipeDB.upsertRecipe(r);
        }
        catch (Exception e) {
            System.out.println("couldn't upsert to database!");
            e.printStackTrace();
        }
    }

    private void handlePostButton(ActionEvent event) {
        String objectID = view.getRecipeObjectID().toString();
        Recipe recipe = view.getRecipe();
        String response = model.performRequest("POST", objectID, recipe, null);
        view.showAlert("Response", response);
        System.out.println("POST");
    }

    private void handleGetButton(ActionEvent event) {
        String objectID = view.getQuery();
        String response = model.performRequest("GET", null, null, objectID);
        view.showAlert("Response", response);
        System.out.println("GET");
    }

    private void handlePutButton(ActionEvent event) {
        String objectID = view.getRecipeObjectID().toString();
        Recipe recipe = view.getRecipe();
        String response = model.performRequest("PUT", objectID, recipe, null);
        view.showAlert("Response", response);
        System.out.println("PUT");
    }

    private void handleDeleteButton(ActionEvent event) {
        String objectID = view.getQuery();
        String response = model.performRequest("DELETE", null, null, objectID);
        view.showAlert("Response", response);
        System.out.println("DELETE");
    }
}