package team30.recipeList;

import javafx.event.ActionEvent;

public class Controller {
    private RecipeList view;
    private Model model;

    public Controller(RecipeList view, Model model) {
        this.view = view;
        this.model = model;
        
        this.view.setPostButtonAction(this::handlePostButton);
        this.view.setGetButtonAction(this::handleGetButton);
        this.view.setPutButtonAction(this::handlePutButton);
        this.view.setDeleteButtonAction(this::handleDeleteButton);
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