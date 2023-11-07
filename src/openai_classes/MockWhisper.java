package openai_classes;

import java.io.File;

/**
 * A mock version of IWhisper classes.
 * Provides an already set text when given an audio file to decipher.
 */
public class MockWhisper implements IWhisper {
    String[] result = new String[];
    int counter;

    /**
     * A constructor for MockWhisper.
     * Given two String result1 and result2, initializes an object with those elements.
     * @param result1 - the first value to add to result.
     * @param result2 - the second value to add to result.
     */
    MockWhisper(String result1, String result2){
        this.result[0] = result1;
        this.result[1] = result2;
        counter = 0;
    }

    /**
     * Given two strings, replace the result with these strings.
     * Also makes it so string1 is pulled out first again.
     * @param string1 - first value to change result to
     * @param string2 - second value to change result to
     */
    void setResult(String string1, String string2){
        this.result[0] = string1;
        this.result[1] = string2;
        counter = 0;
    }

    /**
     * Given an audio file to decipher into a text, return a given result.
     * @param input - audio file to decipher into a text
     * @return the value of result
     */
    public String extractSpeechFromFile(File input) throws IllegalArgumentException{
        return result[counter++];
    }
}
