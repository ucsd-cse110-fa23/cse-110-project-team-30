package team30.recipeList;

import javafx.scene.Scene;
import team30.server.VoiceRecorder;

public class WindowChange {
    public static RecipeList recipeListMainApp;
    private static Scene recipeListScene;

    public static VoiceRecorder voiceRecorder;
    private static Scene voiceScene;

    public static RecipeDetail recipeDetail;
    private static Scene recipeDetailScene;

    public void setVoiceRecorder(VoiceRecorder vr) {
        voiceRecorder = vr;
        voiceScene = vr.getScene();
    }
    public void setRecipeListMainApp(RecipeList rl) {
        recipeListMainApp = rl;
        recipeListScene = rl.getScene();
    }
    public void setRecipeDetail(RecipeDetail rd) {
        recipeDetail = rd;
        recipeDetailScene = rd.getScene();
    }

    public void openWindow(VoiceRecorder vr) {
        recipeListMainApp.getPrimStage().setScene(voiceScene);
        recipeListMainApp.getPrimStage().show();
    }
    public void openWindow(RecipeDetail rd) {
        recipeListMainApp.getPrimStage().setScene(recipeDetailScene);
        recipeListMainApp.getPrimStage().show();
    }

    public void closeWindow() {
        recipeListMainApp.getPrimStage().setScene(recipeListScene);
        recipeListMainApp.getPrimStage().show();
    }
}
