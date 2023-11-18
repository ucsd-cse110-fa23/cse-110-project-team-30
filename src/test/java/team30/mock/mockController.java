package team30.mock;

import team30.recipeList.Model;

public class mockController {
   private Model model;

    public mockController(Model model) {
        this.model = model;
    }

    public String handlePostButton(String recipeName, String[] recipeDetails) {
        String response = model.performRequest("POST", recipeName, detailsToString(recipeDetails), null);
        return response;
    }

    public String handleGetButton(/*TODO: figure out arguments */) {
        // String query = view.getQuery();
        // String response = model.performRequest("GET", null, null, query);
        // view.showAlert("Response", response);
        return "";
    }

    public String handlePutButton(/*TODO: figure out arguments */) {
        // String language = view.getLanguage();
        // String year = view.getYear();
        // String response = model.performRequest("PUT", language, year, null);
        // view.showAlert("Response", response);
        return "";
    }

    public String handleDeleteButton/*TODO: figure out arguments */() {
        // String query = view.getQuery();
        // String response = model.performRequest("DELETE", null, null, query);
        // view.showAlert("Response", response);
        return "";
    }

    public String detailsToString(String[] details) {
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
