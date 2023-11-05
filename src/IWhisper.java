import java.nio.file;

/**
 * An interface for the Whisper classes.
 * It is meant to take in an audio file, which then is converted into words.
 */
public interface IWhisper {
    /**
     * Given an input audio file,
     * it converts the file into text of what is said in the file.
     * @param input - the file to convert into text
     * @throws IllegalArgumentException if the file given is not supported.
     * @return the text representation of what is said in the file.
     */
    public String getResult(File input) throws IllegalArgumentException;
}
