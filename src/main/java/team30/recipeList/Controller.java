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
        String recipeName = view.getRecipeName();
        String[] recipeDetails = view.getRecipeDetails();
        String response = model.performRequest("POST", recipeName, detailsToString(recipeDetails), null);
        view.showAlert("Response", response);
        //view.showAlert("Response", "POST");
        System.out.println("POST");
    }

    private void handleGetButton(ActionEvent event) {
        // String query = view.getQuery();
        // String response = model.performRequest("GET", null, null, query);
        // view.showAlert("Response", response);
        view.showAlert("Response", "GET");
        System.out.println("GET");
    }

    private void handlePutButton(ActionEvent event) {
        // String language = view.getLanguage();
        // String year = view.getYear();
        // String response = model.performRequest("PUT", language, year, null);
        // view.showAlert("Response", response);
        view.showAlert("Response", "PUT");
        System.out.println("PUT");
    }

    private void handleDeleteButton(ActionEvent event) {
        // String query = view.getQuery();
        // String response = model.performRequest("DELETE", null, null, query);
        // view.showAlert("Response", response);
        view.showAlert("Response", "DELETE");
        System.out.println("DELETE");
    }

    private String detailsToString(String[] details) {
        if (details.length != 3) {
            System.out.println("ERROR: recipe details doesn't have the right amount of information");
        }
        String s = "";
        s += " Meal Type: " + details[0];
        s += " Ingredient List: " + details[1];
        s += " Steps: " + details[2];
        return s;
    }
}