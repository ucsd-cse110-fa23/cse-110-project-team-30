package team30.recipeList;

import javafx.scene.Scene;
import team30.server.VoiceRecorder;

public class WindowChange {
    public static RecipeList recipeListMainApp;
    private static Scene recipeListScene;

    public static VoiceRecorder voiceRecorder;
    private static Scene voiceScene;

    public void setVoiceRecorder(VoiceRecorder vr) {
        voiceRecorder = vr;
        voiceScene = vr.getScene();
    }

    public void setRecipeListMainApp(RecipeList rl) {
        recipeListMainApp = rl;
        recipeListScene = rl.getScene();
    }

    public void openWindow(VoiceRecorder vr) {
        recipeListMainApp.getPrimStage().setScene(voiceScene);
        recipeListMainApp.getPrimStage().show();
    }

    public void closeWindow(VoiceRecorder vr) {
        recipeListMainApp.getPrimStage().setScene(recipeListScene);
        recipeListMainApp.getPrimStage().show();
    }
}
