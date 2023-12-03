//package team30.recipeList;

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

import java.io.File;

public class ImageManagerTest{

    @Test
    void testGenerator(){
        //Test Generator with a new thing
        //Make sure the url given matches the thing given.
    }

    @Test
    void testEnsurePathValid(){
        //Check if path exists and not regen

        //Test if path doesn't exist, should make a call

        //Test if path doesn't exist
    }

    @Test
    void testImageGetter(){
        //Given a valid path, it should be valid
    }

    @Test
    void testURIGenerator(){
        ImageManager.path = "images1";
        //For each, add a file unless not
        //Test a unique
        //Test nonunique
        //Test nonunique second.
        //Test new
        //Test final
        //Delete everything
    }
}