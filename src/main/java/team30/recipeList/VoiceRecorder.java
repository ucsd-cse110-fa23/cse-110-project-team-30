package team30.recipeList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.io.*;
import java.net.URISyntaxException;

import javax.sound.sampled.*;

import org.json.JSONException;

class RecorderBorderPane extends BorderPane {
    String tanLight = "#f1eae0", tanDark = "#ede1cf";
    String pink = "#ead1dc", purple = "#d9d2e9", blue = "#cfe2f3";
    String magenta = "#a64d79", green = "#a64d79";

    private HBox header;
    private HBox footer;
    private VBox middle;

    private Text titleText;
    private Button backButton;
    private Button startButton;
    private Button stopButton;
    private Button continueButton;

    String defaultButtonStyle = "-fx-font-style: italic; -fx-padding: 10; -fx-background-insets: 5; -fx-font-weight: bold; -fx-font: 11 arial; ";
    
    RecorderBorderPane() {
        header = new HBox();
        titleText = new Text("PantryPal");
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 40; -fx-fill: " + magenta);
        header.getChildren().add(titleText);
        header.setAlignment(Pos.CENTER);

        footer = new HBox();
        backButton = new Button("Cancel");
        backButton.setStyle(defaultButtonStyle + "-fx-background-color: " + blue);
        continueButton = new Button("Continue");
        continueButton.setStyle(defaultButtonStyle + "-fx-background-color: " + purple);
        footer.getChildren().addAll(backButton, continueButton);
        footer.setAlignment(Pos.CENTER);

        middle = new VBox();
        startButton = new Button("Start Recording");
        startButton.setStyle(defaultButtonStyle + "-fx-background-color: " + pink + "; -fx-font: 14 arial;");
        stopButton = new Button("Stop Recording");
        stopButton.setStyle(defaultButtonStyle + "-fx-background-color: " + pink + "; -fx-font: 14 arial;");
        middle.getChildren().addAll(startButton, stopButton);
        middle.setAlignment(Pos.CENTER);
        
        this.setStyle("-fx-background-color: " + tanLight);
        this.setTop(header);
        this.setCenter(middle);
        this.setBottom(footer);
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


public class VoiceRecorder {
    private RecorderBorderPane voiceAF; //current app frame
    private RecipeList rl;

    private Scene recipeListScene;
    private Scene voiceScene;

    private Button startButton;
    private Button stopButton;
    private Button backButton;
    private Button continueButton;

    private AudioFormat audioFormat;
    private TargetDataLine targetDataLine;
    private Label recordingLabel;
    private Label failedMealTypeLabel, successfulMealTypeLabel, instructionsMealTypeLabel;
    private Label instructionsIngredientsLabel, successfulIngredientsLabel;

    private boolean startedRecording;
    private boolean completedRecording;
    private boolean processingIngredients;
    private boolean completedIngredients;

    private String mealtype, ingredientsRaw;
    String defaultLabelStyle = "-fx-font: 13 arial; -fx-pref-width: 175px; -fx-pref-height: 50px";

    private Whisper audioProcessor;

    public VoiceRecorder(RecipeList rl, AppFrame af) {
        this.rl = rl;
        recipeListScene = rl.getScene();

        voiceAF = new RecorderBorderPane();
        audioProcessor = new Whisper();

        startButton = voiceAF.getStartButton();
        stopButton = voiceAF.getStopButton();
        backButton = voiceAF.getBackButton();
        continueButton = voiceAF.getContinueButton();
        
        recordingLabel = new Label("Recording...");
        instructionsMealTypeLabel = new Label("Options are 'breakfast', 'lunch', and 'dinner'");
        failedMealTypeLabel = new Label("Please say 'breakfast', 'lunch', or 'dinner'!");
        successfulMealTypeLabel = new Label("You said: ");
        instructionsIngredientsLabel = new Label("Please list the ingredients you have.");
        successfulIngredientsLabel = new Label("You said: ");

        recordingLabel.setStyle(defaultLabelStyle);
        instructionsMealTypeLabel.setStyle(defaultLabelStyle);
        failedMealTypeLabel.setStyle(defaultLabelStyle);
        successfulMealTypeLabel.setStyle(defaultLabelStyle);
        instructionsIngredientsLabel.setStyle(defaultLabelStyle);
        successfulIngredientsLabel.setStyle(defaultLabelStyle);

        recordingLabel.setWrapText(true);
        instructionsMealTypeLabel.setWrapText(true);
        failedMealTypeLabel.setWrapText(true);
        successfulMealTypeLabel.setWrapText(true);
        instructionsIngredientsLabel.setWrapText(true);
        successfulIngredientsLabel.setWrapText(true);

        audioFormat = getAudioFormat();

        startedRecording = false;
        completedRecording = false;
        processingIngredients = false;
        completedIngredients = false;
        mealtype = "";

        hideLabels();
        instructionsMealTypeLabel.setVisible(true);

        voiceAF.getMiddle().getChildren().addAll(recordingLabel, instructionsMealTypeLabel, failedMealTypeLabel, successfulMealTypeLabel, instructionsIngredientsLabel, successfulIngredientsLabel);
        addListeners();
        voiceScene = new Scene(voiceAF, 500, 600);
    };

    private void hideLabels() {
        recordingLabel.setVisible(false);
        instructionsMealTypeLabel.setVisible(false);
        failedMealTypeLabel.setVisible(false);
        successfulMealTypeLabel.setVisible(false);
        instructionsIngredientsLabel.setVisible(false);
        successfulIngredientsLabel.setVisible(false);
    }

    public void addListeners() {
        startButton.setOnAction(e -> {
            hideLabels();
            startRecording();
            if (processingIngredients)
                completedIngredients = false;
            else
                completedRecording = false;
        });
        stopButton.setOnAction(e -> {
            stopRecording();
            if (startedRecording == true) {
                completedRecording = true;
            }
            //process voice recording for meal type
            if (!processingIngredients) {
                audioProcessor.setInputFile("src\\main\\java\\team30\\recipeList\\mealtype.wav");
                try {
                    mealtype = audioProcessor.run();
                } catch (JSONException | IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }

                hideLabels();
                System.out.println(mealtype.toLowerCase());
                //check mealtype validity
                if (mealtype.toLowerCase().equals("breakfast") || mealtype.toLowerCase().equals("lunch") || mealtype.toLowerCase().equals("dinner")) {
                    //valid
                    successfulMealTypeLabel.setText("You said: " + mealtype.toLowerCase());
                    successfulMealTypeLabel.setVisible(true);
                    completedRecording = true;
                }
                else {
                    failedMealTypeLabel.setVisible(true);
                    mealtype = "";
                    completedRecording = false;
                }
            }
            //process ingredients
            else {
                audioProcessor.setInputFile("src\\main\\java\\team30\\recipeList\\ingredients.wav");
                try {
                    ingredientsRaw = audioProcessor.run();
                } catch (JSONException | IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }

                System.out.println(ingredientsRaw);
                hideLabels();
                successfulIngredientsLabel.setText("You said: " + ingredientsRaw);
                successfulIngredientsLabel.setVisible(true);
                completedIngredients = true;
            }
        });
        backButton.setOnAction(e -> {
            hideLabels();
            if (startedRecording == true) {
                //cancel recording first
                stopRecording();
                completedRecording = false;
            }
            closeDetailWindow();
        });
        continueButton.setOnAction(e -> {
            if (completedRecording == false) {
                //invalid recording
                hideLabels();
                failedMealTypeLabel.setVisible(true);
            }
            else if (!processingIngredients) {
                //if meal type is valid, process ingredients
                processingIngredients = true;
                hideLabels();
                instructionsIngredientsLabel.setVisible(true);
            }
            else if (completedIngredients) {
                //go back to recipe, do ChatGPT 
                closeDetailWindow();
            }
        });
    }

    private AudioFormat getAudioFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 16;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = false;

        return new AudioFormat(
                sampleRate,
                sampleSizeInBits,
                channels,
                signed,
                bigEndian);
    }

    private void startRecording() {
        Thread t = new Thread(
            new Runnable() {
                @Override
                public void run() {
                    try {
                        // the format of the TargetDataLine
                        DataLine.Info dataLineInfo = new DataLine.Info(
                                TargetDataLine.class,
                                audioFormat);
                        // the TargetDataLine used to capture audio data from the microphone
                        targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
                        targetDataLine.open(audioFormat);
                        targetDataLine.start();
                        startedRecording = true;
                        recordingLabel.setVisible(true);

                        // the AudioInputStream that will be used to write the audio data to a file
                        AudioInputStream audioInputStream = new AudioInputStream(
                                targetDataLine);

                        // the file that will contain the audio data
                        File audioFile;
                        if (!processingIngredients)
                            audioFile = new File("src\\main\\java\\team30\\recipeList\\mealtype.wav");
                        else
                            audioFile = new File("src\\main\\java\\team30\\recipeList\\ingredients.wav");
                        AudioSystem.write(
                                audioInputStream,
                                AudioFileFormat.Type.WAVE,
                                audioFile);
                        recordingLabel.setVisible(false);
                        startedRecording = false;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        t.start();
    }

    private void stopRecording() {
        targetDataLine.stop();
        targetDataLine.close();
    }

    public void openDetailWindow() {
        rl.getPrimStage().setScene(voiceScene);
        rl.getPrimStage().show();
    }

    public void closeDetailWindow() {
        rl.getPrimStage().setScene(recipeListScene);
        rl.getPrimStage().show();
    }
    
    public void setButtonStyle(Button button) {
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #a1f2c8;  -fx-font-weight: bold; -fx-font: 15 arial; -fx-background-radius: 10";
        button.setStyle(defaultButtonStyle);
        // Adding hover effect
        button.setOnMouseEntered(e -> button.setStyle("-fx-font-style: italic; -fx-background-color: #7dedb3;  -fx-font-weight: bold; -fx-font: 15 arial; -fx-background-radius: 10"));
        button.setOnMouseExited(e -> button.setStyle("-fx-font-style: italic; -fx-background-color: #a1f2c8;  -fx-font-weight: bold; -fx-font: 15 arial; -fx-background-radius: 10"));
        
        // Adding click effect
        button.setOnMousePressed(e -> button.setStyle("-fx-font-style: italic; -fx-background-color: #117e2c;  -fx-font-weight: bold; -fx-font: 15 arial; -fx-background-radius: 10"));
        button.setOnMouseReleased(e -> button.setStyle("-fx-font-style: italic; -fx-background-color: #a1f2c8;  -fx-font-weight: bold; -fx-font: 15 arial; -fx-background-radius: 10"));
    }

    public boolean successfulRecording() {
        return completedIngredients; //everything's completed
    }

    public String getIngredientAudio() {
        return ingredientsRaw;
    }

    public String getMealType() {
        return mealtype;
    }
}
