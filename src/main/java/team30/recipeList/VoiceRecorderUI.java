package team30.recipeList;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

class VoiceRecorderUI extends DefaultBorderPane {
    private Button backButton;
    private Button startButton;
    private Button stopButton;
    private Button continueButton;
    
    public VoiceRecorderUI() {
        backButton = new Button("Cancel");
        backButton.setStyle(defaultButtonStyle + "; -fx-background-color: " + blue);
        continueButton = new Button("Continue");
        continueButton.setStyle(defaultButtonStyle + "; -fx-background-color: " + purple);
        footer.getChildren().addAll(backButton, continueButton);
        footer.setAlignment(Pos.CENTER);

        startButton = new Button("Start Recording");
        startButton.setStyle(defaultButtonStyle + "; -fx-background-color: " + pink + "; -fx-font: 14 arial;");
        stopButton = new Button("Stop Recording");
        stopButton.setStyle(defaultButtonStyle + "; -fx-background-color: " + pink + "; -fx-font: 14 arial;");
        middle.getChildren().addAll(startButton, stopButton);
        middle.setAlignment(Pos.CENTER);
    }

    public Button getStartButton() {
        return startButton;
    }
    public Button getStopButton() {
        return stopButton;
    }
    public Button getBackButton() {
        return backButton;
    }
    public Button getContinueButton() {
        return continueButton;
    }
    public VBox getMiddle() {
        return middle;
    }
}

