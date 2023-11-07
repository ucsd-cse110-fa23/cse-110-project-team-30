package openai_classes;

import team30.meal.MealType;

import java.io.File;

/**
 * A mock version of IChatGPT classes.
 * Provides an already set text when given information to convert into a recipe.
 */
public class MockChatGPT implements IChatGPT {
    String result;

    MockChatGPT(String result){
        this.result = result;
    }

    void setResult(String result){
        this.result = result;
    }
    
    /**
     * Given a type of meal and ingredients, simply returns the value of result.
     * @param type - type of the meal to create
     * @param ingredients - type of ingredients to add
     * @return the value of result
     */
    public String generateMeal(MealType type, String ingredients){
        return this.result;
    }
}