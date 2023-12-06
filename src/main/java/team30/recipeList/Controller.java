package team30.recipeList;

import javafx.event.ActionEvent;
import team30.server.ChatGPT;
import team30.server.VoiceRecorder;
import java.io.File;

public class Controller {
    private RecipeList view;
    private Model model;

    private RecipeListUI recipeListUI; //part of view
    private VoiceRecorder voiceRecorder;

    public Controller(RecipeList view, Model model) {
        this.view = view;
        this.recipeListUI = view.getRecipeListUI();
        this.model = model;
        
        this.view.setPostButtonAction(this::handleRecipePostButton);
        this.view.setGetButtonAction(this::handleRecipeGetButton);
        this.view.setPutButtonAction(this::handleRecipePutButton);
        this.view.setDeleteButtonAction(this::handleRecipeDeleteButton);
        this.view.setChatGPTButtonAction(this::handleChatGPTButton);

        voiceRecorder = recipeListUI.getVoiceRecorder();
        voiceRecorder.setAudioButtonAction(this::handleVoiceButton);
    }   

    private void handleRecipePostButton(ActionEvent event) {
        String objectID = view.getRecipeObjectID().toString();
        Recipe recipe = view.getRecipe();
        String response = model.performRequest("POST", objectID, recipe, null);
        view.showAlert("Response", response);
        System.out.println("POST");
    }

    private void handleRecipeGetButton(ActionEvent event) {
        String objectID = view.getQuery();
        String response = model.performRequest("GET", null, null, objectID);
        view.showAlert("Response", response);
        System.out.println("GET");
    }

    private void handleRecipePutButton(ActionEvent event) {
        String objectID = view.getRecipeObjectID().toString();
        Recipe recipe = view.getRecipe();
        String response = model.performRequest("PUT", objectID, recipe, null);
        view.showAlert("Response", response);
        System.out.println("PUT");
    }

    private void handleRecipeDeleteButton(ActionEvent event) {
        String objectID = view.getQuery();
        String response = model.performRequest("DELETE", null, null, objectID);
        view.showAlert("Response", response);
        System.out.println("DELETE");
    }

    //TODO: multi-device voice
    // private void handleVoicePostButton(ActionEvent event) {
    //     //send file through post
    //     String fileName = voiceRecorder.getQuery();
    //     File file = voiceRecorder.getFile();
    //     String response = model.performVoiceRequest("POST", fileName, file, null);
    //     view.showAlert("Response", response);
    //     voiceRecorder.setProcessedAudio(response);
    // }

    // private void handleVoiceGetButton(ActionEvent event) {
    //     //process file through get
    //     String fileName = voiceRecorder.getQuery();
    //     String response = model.performVoiceRequest("GET", null, null, fileName);
    //     view.showAlert("Response", response);
    //     voiceRecorder.setProcessedAudio(response);
    // }

    private void handleVoiceButton(ActionEvent event) {
        //process file through get
        String fileName = voiceRecorder.getQuery();
        String response = model.performVoiceRequest("GET", fileName);
        view.showAlert("Response", response);
        voiceRecorder.setProcessedAudio(response);
    }

    private void handleChatGPTButton(ActionEvent event) {
        String mealtype = view.getRecipeListUI().getMealType();
        String ingredients = view.getRecipeListUI().getIngredients();
        String response = model.performChatGPTRequest("GET", mealtype, ingredients);
        // view.showAlert("Response", response);
        System.out.println("///");
        System.out.println(response);
        view.getRecipeListUI().setRecipeRaw(response);
    }
}