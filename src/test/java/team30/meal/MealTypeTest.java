package team30.meal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

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