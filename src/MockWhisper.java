import java.io.File;

/**
 * A mock version of IWhisper classes.
 * Provides an already set text when given an audio file to decipher.
 */
public class MockWhisper implements IWhisper {
    String result;

    MockWhisper(String result){
        this.result = result;
    }

    /**
     * Given an audio file to decipher into a text, return a given result.
     * @param input - audio file to decipher into a text
     * @return the value of result
     */
    public String getResults(File input) throws IllegalArgumentException{
        return result;
    }
}
