package team30.ImageManager;

import team30.recipeList.RecipeList;
import team30.recipeList.ImageManager;


import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.objenesis.instantiator.sun.MagicInstantiator;

import javafx.beans.binding.ObjectBinding;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import team30.mock.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import javafx.application.*;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import java.io.File;

public class ImageManagerTest{

    @AfterAll
    static void cleanUp(){
        ImageManager.path = "src" + File.separator + "main" + File.separator + "java" + File.separator + "team30" + File.separator + "recipeList" + File.separator + "images";
    }

    @Test
    void testGenerator(){
        //Test Generator with a new thing
        //Make sure the url given matches the thing given.
        String pathOrigin = "src" + File.separator + "test" + File.separator + "java" + File.separator + "team30" + File.separator + "ImageManager" + File.separator + "genEx";
        String pathEnd = "src" + File.separator + "test" + File.separator + "java" + File.separator + "team30" + File.separator + "ImageManager";
        ImageManager.path = pathEnd;
        ImageManager.generateImage("image", pathOrigin);

        assertTrue(new File(pathEnd + File.separator + "image.png").exists());
    }

    @Test
    void testEnsurePathValid(){
        //Check if path exists and not regen
        ImageManager.path = "src" + File.separator + "test" + File.separator + "java" + File.separator + "team30" + File.separator + "ImageManager" + File.separator + "ensureEx";
        
        String originPath = "src" + File.separator + "test" + File.separator + "java" + File.separator + "team30" + File.separator + "ImageManager";
        assertEquals("test0.png",ImageManager.ensurePathExists("test0.png","test0",false,null));
        //Make sure it doesn't make a new file.
        assertFalse((new File(ImageManager.path + File.separator + "test0(0).png")).exists());
        assertTrue((new File(ImageManager.path + File.separator + "test0.png")).exists());
        
        //Test if path doesn't exist, should make a call
        assertEquals("image.png",ImageManager.ensurePathExists("image.png","image",false,originPath));
        assertTrue((new File(ImageManager.path + File.separator + "image.png")).exists());
        (new File(ImageManager.path + File.separator + "image.png")).delete();
        

        //Test if regen is true, even if path exists
        
        assertEquals("test1.png", ImageManager.ensurePathExists("test1.png","test1",true,originPath));
        assertTrue((new File(ImageManager.path + File.separator + "test1.png")).exists());
        assertFalse((new File(ImageManager.path + File.separator + "test1(0).png")).exists());

    }

    @Test
    void testCreateUniqueURI(){
        ImageManager.path = "src" + File.separator + "test" + File.separator + "java" + File.separator + "team30" + File.separator + "ImageManager" + File.separator + "URIGenEx";
        assertEquals("src\\test\\java\\team30\\ImageManager\\URIGenEx",ImageManager.path);
        assertEquals((new File(ImageManager.path)).exists(),true);
        //Test a unique
        assertEquals("test0.png", ImageManager.createUniqueURI("test0"));
        //Test nonunique
        assertEquals("test2(1).png",ImageManager.createUniqueURI("test2"));
        //Test a nonunique first copy
        assertEquals("test3(0).png",ImageManager.createUniqueURI("test3"));
    }
}