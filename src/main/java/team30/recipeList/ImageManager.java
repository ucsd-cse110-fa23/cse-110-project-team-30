package team30.recipeList;

import javafx.scene.image.Image;
import java.io.File;
import java.net.URI;

public class ImageManager {

    static IDallE dallE = new DallE();


    public static String path = "";

    //TODO: Once the Server is cleaned up, have this instead make a call to the server to generate the image.
    /**
     * Creates an image with the given name if the name is clear.
     * If not, a new path will be made to hold the image.
     * @param name - the expected name for the image
     * @return - a path in the files to the image
     */
    static String generateImage(String name){
        String imgurl = createUniqueURI(name);
        try{
            dallE.generateImage(name, combinePathAndName(imgurl));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return imgurl;
    }

    /**
     * Runs the generateImage function with the given DallE.
     * Intended for testing purposes only.
     */
    public static String generateImage(String name, String path){
        IDallE hold = dallE;
        dallE = new MockDallE(path);
        String result = generateImage(name);
        dallE = hold;
        return result;
    }

    /**
     * Meant to ensure that a given path is good for use.
     * If the path does not exist, or the regenerate flag is true,
     * a new Image will be created and a new path pointing to it added.
     * @param path - expected path of the Image
     * @param name - name to use when generating a path IF a new path must be generated
     * @param regenerate - flag that sets whether to regenerate the image or not.
     * @return the original path, if valid, and a path to a new image if not.
     */
    static String ensurePathValid(String path, String name, boolean regenerate){
        String imgurl = path;
        if(regenerate || !(new File(combinePathAndName(path)).exists())){
            imgurl = generateImage(name);
        }
        return imgurl;
    }

    /**
     * Attempts to get an image in the given path.
     * It is recommended to run ensurePathValid first to ensure that the path could be used.
     * @param path - path of the image.
     * @return image in the given path.
     */
    static Image getImage(String imagePath){
        File file = new File(combinePathAndName(imagePath));
        URI fileURI = file.toURI();
        return new Image(fileURI.toString());
    }

    /**
     * Creates a unique URI with the given name.
     * @param name - the expected name for the URI
     * @return the path of the URI as a String
     */
    public static String createUniqueURI(String name){
        String url = name + ".png";
        int counter = 0;
        while((new File(combinePathAndName(url))).exists())
            url = name + "(" + (counter++) + ")" + ".png";
        return url;
    }

    protected static String combinePathAndName(String url){
        return path + File.separator + url;
    }
}