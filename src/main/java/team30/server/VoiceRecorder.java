package team30.server;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import team30.recipeList.VoiceRecorderUI;
import team30.recipeList.WindowChange;

import java.io.*;
import javax.sound.sampled.*;
import java.util.*;

public class VoiceRecorder {
    private VoiceRecorderUI voiceUI; //current app frame
    private Scene voiceScene;

    private Button startButton, stopButton, backButton, continueButton;

    private AudioFormat audioFormat;
    private TargetDataLine targetDataLine;

    private boolean startedRecording, completedRecording;
    private boolean processingIngredients, completedIngredients;

    private String mealtype, ingredientsRaw;
    private RecordingCompletionListener completionListener;

    private String processedAudio; //gotten from server
    private Button processAudioButton; //HTTP 
    private String filename;

    private static WindowChange windowChange;

    List<Label> labels;

    public VoiceRecorder() {
        voiceUI = new VoiceRecorderUI();

        startButton = voiceUI.getStartButton();
        stopButton = voiceUI.getStopButton();
        backButton = voiceUI.getBackButton();
        continueButton = voiceUI.getContinueButton();

        processAudioButton = new Button();
        
        labels = voiceUI.getLabels();

        audioFormat = getAudioFormat();

        resetBools();
        mealtype = "";

        voiceUI.getMiddle().getChildren().addAll(labels);
        addListeners();
        voiceScene = new Scene(voiceUI, 500, 600);

        windowChange = new WindowChange();
        windowChange.setVoiceRecorder(this);
    };

    public Scene getScene() {return voiceScene;}

    public void addListeners() {
        startButton.setOnAction(e -> {
            startRecording();
            if (processingIngredients)
                completedIngredients = false;
            else
                completedRecording = false;
        });
        stopButton.setOnAction(e -> {
            if (startedRecording == true) {
                stopRecording();
                completedRecording = true;
            }
            //process voice recording for meal type
            if (!processingIngredients) {
                filename = "mealtype.wav";
                processAudioButton.fire(); //call server
                mealtype = processedAudio;

                if (mealtype == null) {
                    mealtype = "";
                }

                //check mealtype validity
                mealtype = mealtype.toLowerCase().replaceAll("[.]", "");
                if (mealtype.equals("breakfast") || mealtype.equals("lunch") || mealtype.equals("dinner")) {
                    voiceUI.setMealType(mealtype);
                    completedRecording = true;
                }
                else {
                    voiceUI.setFailedMealType();
                    mealtype = "";
                    completedRecording = false;
                }
            }
            //process ingredients
            else {
                filename = "ingredients.wav";
                processAudioButton.fire(); //call server
                ingredientsRaw = processedAudio;

                if (ingredientsRaw == null) {
                    mealtype = "";
                }
        
                voiceUI.setIngredients(ingredientsRaw);
                completedIngredients = true;
            }
        });
        backButton.setOnAction(e -> {
            voiceUI.setMealTypeInstructions();
            if (startedRecording == true) {
                //cancel recording first
                stopRecording();
            }
            closeDetailWindow();
        });
        continueButton.setOnAction(e -> {
            if (completedRecording == false) {
                //invalid recording
                voiceUI.setFailedMealType();
            }
            else if (!processingIngredients) {
                //if meal type is valid, process ingredients
                processingIngredients = true;
                voiceUI.setProcessingIngredients();
            }
            else if (successfulRecording()) {
                //go back to recipe, do ChatGPT 
                closeDetailWindow();
                voiceUI.setMealTypeInstructions();
            }
        });
    }

    void resetBools() {
        startedRecording = false;
        completedRecording = false;
        processingIngredients = false;
        completedIngredients = false;
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
                        voiceUI.setRecording();

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
        windowChange.openWindow(this);
    }

    public void closeDetailWindow() {
        if (successfulRecording() && completionListener != null) {
            completionListener.onRecordingCompleted(mealtype, ingredientsRaw);
        }
        else {
            windowChange.closeWindow();
        }
        resetBools();
    }

    public boolean successfulRecording() { return completedIngredients && completedRecording; }
    public String getIngredientAudio() { return ingredientsRaw; }
    public String getMealType() { return mealtype; }
    public void setAudioButtonAction(EventHandler<ActionEvent> eventHandler) {processAudioButton.setOnAction(eventHandler);}
    public String getQuery() { return filename; }
    public void setProcessedAudio(String text) { processedAudio = text; }

    public interface RecordingCompletionListener {
        void onRecordingCompleted(String mealType, String ingredientsRaw);
    }

    public void setCompletionListener(RecordingCompletionListener listener) {
        this.completionListener = listener;
    }
}
