package team30.server;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import team30.recipeList.RecipeList;
import team30.recipeList.RecipeListUI;
import team30.recipeList.VoiceRecorderUI;

import java.io.*;
import java.net.URISyntaxException;

import javax.sound.sampled.*;
import java.util.*;

import org.json.JSONException;

public class VoiceRecorder {
    private VoiceRecorderUI voiceUI; //current app frame
    private RecipeList rl;

    private Scene recipeListScene;
    private Scene voiceScene;

    private Button startButton;
    private Button stopButton;
    private Button backButton;
    private Button continueButton;

    private AudioFormat audioFormat;
    private TargetDataLine targetDataLine;

    private boolean startedRecording;
    private boolean completedRecording;
    private boolean processingIngredients;
    private boolean completedIngredients;

    private String mealtype, ingredientsRaw;

    private Whisper audioProcessor;
    private RecordingCompletionListener completionListener;

    List<Label> labels;

    public VoiceRecorder(RecipeList rl, RecipeListUI af) {
        this.rl = rl;
        recipeListScene = rl.getScene();

        voiceUI = new VoiceRecorderUI();
        audioProcessor = new Whisper();

        startButton = voiceUI.getStartButton();
        stopButton = voiceUI.getStopButton();
        backButton = voiceUI.getBackButton();
        continueButton = voiceUI.getContinueButton();
        
        labels = voiceUI.getLabels();

        audioFormat = getAudioFormat();

        startedRecording = false;
        completedRecording = false;
        processingIngredients = false;
        completedIngredients = false;
        mealtype = "";

        voiceUI.getMiddle().getChildren().addAll(labels);
        addListeners();
        voiceScene = new Scene(voiceUI, 500, 600);
    };

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
                audioProcessor.setInputFile("src\\main\\java\\team30\\recipeList\\mealtype.wav");
                try {
                    mealtype = audioProcessor.run();
                } catch (JSONException | IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }

                System.out.println(mealtype.toLowerCase());
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
                audioProcessor.setInputFile("src\\main\\java\\team30\\recipeList\\ingredients.wav");
                try {
                    ingredientsRaw = audioProcessor.run();
                } catch (JSONException | IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }

                System.out.println(ingredientsRaw);
                voiceUI.setIngredients(ingredientsRaw);
                completedIngredients = true;
            }
        });
        backButton.setOnAction(e -> {
            voiceUI.hideLabels();
            if (startedRecording == true) {
                //cancel recording first
                stopRecording();
            }
            completedRecording = false;
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
        rl.getPrimStage().setScene(voiceScene);
        rl.getPrimStage().show();
    }

    public void closeDetailWindow() {
        rl.getPrimStage().setScene(recipeListScene);
        if (successfulRecording() && completionListener != null) {
            completionListener.onRecordingCompleted(mealtype, ingredientsRaw);
        }
        else {
            rl.getPrimStage().show();
        }
    }

    public boolean successfulRecording() {
        return completedIngredients && completedRecording; //everything's completed
    }

    public String getIngredientAudio() {
        return ingredientsRaw;
    }

    public String getMealType() {
        return mealtype;
    }

    public interface RecordingCompletionListener {
        void onRecordingCompleted(String mealType, String ingredientsRaw);
    }

    public void setCompletionListener(RecordingCompletionListener listener) {
        this.completionListener = listener;
    }
}
