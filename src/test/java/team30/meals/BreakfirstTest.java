package team30.meals;

import team30.MealType.Breakfirst;
import team30.MealType.MealType;
import team30.MealType.Lunch;
import team30.MealType.Dinner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class BreakfirstTest {
    private MealType mt;
    
    @BeforeEach
    void setUp() {
        mt = null;
    }
    
    @Test
    void testBreakfirst() {
        mt = new Breakfirst();
        assertEquals("Breakfirst", mt.getMealType());
    }
    
    @Test
    void testLunch() {
        mt = new Lunch();
        assertEquals("Lunch", mt.getMealType());
    }

    @Test
    void testDinner() {
        mt = new Dinner();
        assertEquals("Dinner", mt.getMealType());
    }
    
}
