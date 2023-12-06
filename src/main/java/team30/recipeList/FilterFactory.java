package team30.recipeList;

import java.util.ArrayList;
import javafx.scene.*;

public class FilterFactory{
    static Filter create(String setting){
        switch(setting.toLowerCase()){
            case "breakfast":
                return new FilterMealType("Breakfast");
            case "lunch":
                return new FilterMealType("Lunch");
            case "dinner":
                return new FilterMealType("Dinner");
            case "all":
                return new FilterAll();
            default:
                throw new IllegalArgumentException();
        }
    }
}

abstract class Filter {
    abstract protected boolean checkFilter(Node e);
    public ArrayList<Node> filter(ArrayList<Node> target){
        ArrayList<Node> result = new ArrayList<>(target.size());
        for(Node e : target){
            if(checkFilter(e))
                result.add(e);
        }
        return result;
    }
}

class FilterMealType extends Filter{

    String mealType;

    FilterMealType(String mealType){
        this.mealType = mealType;
    }
    protected boolean checkFilter(Node e){
        return (e instanceof Recipe && ((Recipe)e).getMealType().equals(mealType));
    }
}

class FilterAll extends Filter{

    protected boolean checkFilter(Node e){
        return true;
    }
}