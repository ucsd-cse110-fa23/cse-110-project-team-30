package team30.recipeList;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class VoiceRecorderUI extends DefaultBorderPane {
    private Button backButton;
    private Button startButton;
    private Button stopButton;
    private Button continueButton;
    
    private String defaultLabelStyle = "-fx-font: 13 arial; -fx-pref-width: 175px; -fx-pref-height: 50px";
    
    private Label recordingLabel;
    private Label failedMealTypeLabel, successfulMealTypeLabel, instructionsMealTypeLabel;
    private Label instructionsIngredientsLabel, successfulIngredientsLabel;
    private List<Label> labels;
    
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

        recordingLabel = new Label("Recording...");
        instructionsMealTypeLabel = new Label("Please select your mealtype 'breakfast', 'lunch', or 'dinner'");
        failedMealTypeLabel = new Label("Please say 'breakfast', 'lunch', or 'dinner'!");
        successfulMealTypeLabel = new Label("You said: ");
        instructionsIngredientsLabel = new Label("Please list the ingredients you have.");
        successfulIngredientsLabel = new Label("You said: ");

        labels = new ArrayList<>();
        labels.add(recordingLabel);
        labels.add(instructionsMealTypeLabel);
        labels.add(failedMealTypeLabel);
        labels.add(successfulMealTypeLabel);
        labels.add(instructionsIngredientsLabel);
        labels.add(successfulIngredientsLabel);
        setLabelStyle(defaultLabelStyle);

        setMealTypeInstructions();
    }

    
    private void setLabelStyle(String labelStyle) { 
        for (Label l : labels) { 
            l.setStyle(labelStyle); 
            l.setWrapText(true); 
        } 
    }
    public void hideLabels() {
        for (Label l : labels) {
            l.setVisible(false);
        }
    }

    public void setMealType(String mealtype) {
        hideLabels();
        successfulMealTypeLabel.setText("You said: " + mealtype);
        successfulMealTypeLabel.setVisible(true);
    }
    public void setRecording() {
        hideLabels();
        recordingLabel.setVisible(true);
    }
    public void setIngredients(String ingredientsRaw) {
        hideLabels();
        successfulIngredientsLabel.setText("You said: " + ingredientsRaw);
        successfulIngredientsLabel.setVisible(true);
    }
    public void setMealTypeInstructions() {
        hideLabels();
        instructionsMealTypeLabel.setVisible(true);
    }
    public void setFailedMealType() {
        hideLabels();
        failedMealTypeLabel.setVisible(true);
    }
    public void setProcessingIngredients() {
        hideLabels();
        instructionsIngredientsLabel.setVisible(true);
    }

    public Button getStartButton() { return startButton; }
    public Button getStopButton() { return stopButton; }
    public Button getBackButton() { return backButton; }
    public Button getContinueButton() { return continueButton; }
    public VBox getMiddle() { return middle; }
    public List<Label> getLabels() { return labels; }

}

