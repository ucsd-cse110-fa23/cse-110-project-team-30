package team30.recipeList;

import javafx.scene.image.Image;

/**
 * A basic interface for an ImageManager.
 * The manager is expected to handle the creation and maintenance of images.
 */
public abstract class IImageManager {
    /**
     * Creates an image with the given name if the name is clear.
     * If not, a new path will be made to hold the image.
     * @param name - the expected name for the image
     * @return - a path in the files to the image
     */
    public abstract String generateImage(String name);

    /**
     * Meant to ensure that a given path is good for use.
     * If the path does not exist, or the regenerate flag is true,
     * a new Image will be created and a new path pointing to it added.
     * @param path - expected path of the Image
     * @param name - name to use when generating a path IF a new path must be generated
     * @param regenerate - flag that sets whether to regenerate the image or not.
     * @return the original path, if valid, and a path to a new image if not.
     */
    public abstract String ensurePathValid(String path, String name, boolean regenerate);
    /**
     * Attempts to get an image in the given path.
     * It is recommended to run ensurePathValid first to ensure that the path does have an image.
     * @param path - path of the image.
     * @return image in the given path.
     */
    public abstract Image getImage(String path);

    /**
     * Creates a unique URI for the 
     * @param name - the expected name for the URI
     * @return the path of the URI as a String
     */
    public abstract String createUniqueURI(String name);
}

class ImageManager extends IImageManager{

    DallE dallE = new DallE();


    public static final String path = "";

    //TODO: Once the Server is cleaned up, have this instead make a call to the server to generate the image.
    @Override
    public String generateImage(String name){
        String imgurl = createUniqueURI(name);
        try{
            dallE.generateImage(name, imgurl);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String ensurePathValid(String path, String name, boolean regenerate){
        return null;
    }

    @Override 
    public Image getImage(String path){
        return null;
    }

    @Override
    public String createUniqueURI(String name){
        return null;
    }
}