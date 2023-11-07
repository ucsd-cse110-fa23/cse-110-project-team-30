package team30.meal;

public interface MealType {
    String getMealType();
}

class Breakfast implements MealType {
    static final private String MEAL_TYPE = "Breakfast";

    public String getMealType() {
        return MEAL_TYPE;
    }
}

class Lunch implements MealType {
    static final private String MEAL_TYPE = "Lunch";

    public String getMealType() {
        return MEAL_TYPE;
    }
}

class Dinner implements MealType {
    static final private String MEAL_TYPE = "Dinner";

    public String getMealType() {
        return MEAL_TYPE;
    }
}