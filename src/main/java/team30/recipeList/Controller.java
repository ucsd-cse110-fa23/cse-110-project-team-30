package team30.recipeList;

import javafx.event.ActionEvent;
import team30.account.CreateAccount;
import team30.account.Login;
import team30.server.VoiceRecorder;

public class Controller {
    private RecipeList view;
    private Model model;

    private RecipeListUI recipeListUI; //part of view
    private VoiceRecorder voiceRecorder;
    private Login login;
    private CreateAccount createAcc;

    public Controller(RecipeList view, Model model, Login login, CreateAccount ca) {
        this.view = view;
        this.recipeListUI = view.getRecipeListUI();
        this.model = model;
        this.login = login;
        this.createAcc = ca;
        
        this.view.setPostButtonAction(this::handleRecipePostButton);
        this.view.setGetButtonAction(this::handleRecipeGetButton);
        this.view.setPutButtonAction(this::handleRecipePutButton);
        this.view.setDeleteButtonAction(this::handleRecipeDeleteButton);
        this.view.setChatGPTButtonAction(this::handleChatGPTButton);
        this.view.setShareButtonAction(this::handleShareButton);

        this.createAcc.setPostButtonAction(this::handleAccPostButton);
        this.createAcc.setGetButtonAction(this::handleRegGetButton);
        this.login.setGetButtonAction(this::handleAccGetButton);

        voiceRecorder = recipeListUI.getVoiceRecorder();
        voiceRecorder.setAudioButtonAction(this::handleVoiceButton);
    }   

    private void handleAccPostButton(ActionEvent event) {
        String accName = createAcc.getAccName();
        String accPass = createAcc.getAccPass();
        String response = model.performAccountRequest("POST", accName, accPass, null);
        view.showAlert("Response", response);
        if (response.equals("Connection error!"))
            view.showError("Connection error", "server down, please try again later");
        System.out.println("POST ACC");
    }
    private void handleRegGetButton(ActionEvent event) {
        String accName = createAcc.getAccName();
        String response = model.performAccountRequest("GET", null, null, accName);
        createAcc.setTmpAccPass(response); //update login object
        view.showAlert("Response", response);
        if (response.equals("Connection error!"))
            view.showError("Connection error", "server down, please try again later");
        System.out.println("GET REG");
    }
    private void handleAccGetButton(ActionEvent event) {
        String accName = login.getAccName();
        String response = model.performAccountRequest("GET", null, null, accName);
        login.setTmpAccPass(response); //update login object
        view.showAlert("Response", response);
        if (response.equals("Connection error!"))
            view.showError("Connection error", "server down, please try again later");
        System.out.println("GET ACC");
    }

    private void handleRecipePostButton(ActionEvent event) {
        String objectID = view.getRecipeObjectID().toString();
        Recipe recipe = view.getRecipe();
        String response = model.performRequest("POST", objectID, recipe, null);
        view.showAlert("Response", response);
        if (response.equals("Connection error!"))
            view.showError("Connection error", "server down, please try again later");
        System.out.println("POST");
    }

    private void handleRecipeGetButton(ActionEvent event) {
        String objectID = view.getQuery();
        String response = model.performRequest("GET", null, null, objectID);
        view.showAlert("Response", response);
        if (response.equals("Connection error!"))
            view.showError("Connection error", "server down, please try again later");
        System.out.println("GET");
    }

    private void handleRecipePutButton(ActionEvent event) {
        String objectID = view.getRecipeObjectID().toString();
        Recipe recipe = view.getRecipe();
        String response = model.performRequest("PUT", objectID, recipe, null);
        view.showAlert("Response", response);
        if (response.equals("Connection error!"))
            view.showError("Connection error", "server down, please try again later");
        System.out.println("PUT");
    }

    private void handleRecipeDeleteButton(ActionEvent event) {
        String objectID = view.getQuery();
        String response = model.performRequest("DELETE", null, null, objectID);
        view.showAlert("Response", response);
        if (response.equals("Connection error!"))
            view.showError("Connection error", "server down, please try again later");
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
        if (response.equals("Connection error!"))
            view.showError("Connection error", "server down, please try again later");
        voiceRecorder.setProcessedAudio(response);
    }

    private void handleChatGPTButton(ActionEvent event) {
        String mealtype = view.getRecipeListUI().getMealType();
        String ingredients = view.getRecipeListUI().getIngredients();
        String response = model.performChatGPTRequest("GET", mealtype, ingredients);
        // view.showAlert("Response", response);
        if (response.equals("Connection error!"))
            view.showError("Connection error", "server down, please try again later");
        System.out.println(response);
        view.getRecipeListUI().setRecipeRaw(response);
    }

    private void handleShareButton(ActionEvent event) {
        String objectID = view.getQuery();
        String response = model.performShareRequest("GET", objectID);
        view.showAlert("Response", response);
        if (response.equals("Connection error!"))
            view.showError("Connection error", "server down, please try again later");
        System.out.println("GET");
    }
}