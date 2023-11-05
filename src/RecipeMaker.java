import team30.meal.*;
import openai_classes.*;

import java.io.File;

/**
 * This class handles the creation of Recipes.
 * Given a recipe generator through IChatGPT,
 *  and a voice file decipherer through IWhisper,
 * it gets gets information on the type of recipe
 *  and generates a recipe through that.
 */
public class RecipeMaker {
    IWhisper whisper;
    IChatGPT chatGPT;
}
