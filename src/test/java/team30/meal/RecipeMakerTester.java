
import team30.meal.*;

import openai_classes.*;

public class RecipeMakerTester {

    private RecipeMaker recipeMaker;
    private MockWhisper whisper;
    private MockChatGPT chatGPT;

    @Before
    public setUp(){
        whisper = new MockWhisper("");
        chatGPT = new MockChatGPT("");
        recipeMaker = new RecipeMaker(whisper,chatGPT);
    }

    /**
     * Standard test. It provides a test for all 3 valid mealTypes.
     */
    @Test
    public testStandard(){

        File fakeFile1;
        File fakeFile2;

        whisper.setResult("lunch","curry spice");
        chatGPT.setResult("Curry");

        Recipe result;
        
        result = recipeMaker.createRecipe(fakeFile1, fakeFile2);
        assertEquals(result.getIngredients(),"curry spice");
        assertEquals(result.getInstructions(),"lunch");
        assertEquals(result.getMealType().getMealType(), "Lunch");
        assertEquals(result.getName(),"curry");

        whisper.setResult("Dinner","Beef");
        chatGPT.setResult("Steak");

        result = recipeMaker.createRecipe(fakeFile1, fakeFile2);
        assertEquals(result.getIngredients(),"Beef");
        assertEquals(result.getInstructions(),"Steak");
        assertEquals(result.getMealType().getMealType(), "Dinner");
        assertEquals(result.getName(),"Steak");

        whisper.setResult("Breakfast","Waffle Mix");
        chatGPT.setResult("Waffles");

        result = recipeMaker.createRecipe(fakeFile1, fakeFile2);
        assertEquals(result.getIngredients(),"Waffle Mix");
        assertEquals(result.getInstructions(),"Waffles");
        assertEquals(result.getMealType().getMealType(), "Breakfast");
        assertEquals(result.getName(),"Waffles");
    }

    @Test
    public void testInvalidMealType(){

        File fakeFile1;
        File fakeFile2;

        whisper.setResult(null,"Paper");
        chatGPT.setResult("Why did this work?");

        Recipe result = recipeMaker.createRecipe(fakeFile1,fakeFile2);

        assertEquals(result,null);
    }

    @Test 
    public void testEmptyResults(){
        File fakeFile1;
        File fakeFile2;

        whisper.setResult("","");
        chatGPT.setResult("Why did this work?");

        Recipe result = recipeMaker.createRecipe(fakeFile1,fakeFile2);

        assertEquals(result,null);

        whisper.setResult("Breakfast","");
        chatGPT.setResult("I can't make a recipe without ingredients!");

        result = recipeMaker.createRecipe(fakeFile1,fakeFile2);

        assertEquals(result.getIngredients(),"");
        assertEquals(result.getInstructions(),"I can't make a recipe without ingredients!");
        assertEquals(result.getMealType().getMealType(), "Breakfast");
        assertEquals(result.getName(),"I can't make a recipe without ingredients!");
    }
}
