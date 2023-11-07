package team30.meal;

import team30.meal.MealType;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MealTypeTest {
    private MealType mt;

    @BeforeEach
    void setUp() {
        mt = null;
    }

    @Test
    void testBreakfirst() {
        mt = new Breakfast();
        assertEquals("Breakfast", mt.getMealType());
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