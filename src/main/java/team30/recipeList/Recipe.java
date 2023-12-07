package team30.recipeList;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.json.JSONObject;

//Recipe data-only class
public class Recipe {
    
    String tanLight = "#f1eae0", tanDark = "#ede1cf";
    String pink = "#ead1dc", purple = "#d9d2e9", blue = "#cfe2f3";
    String magenta = "#a64d79", green = "#a64d79";
    
    private String recipe_title;
    private String meal_type;
    private String ingredients;
    private ArrayList<String> steps;
    private String imageurl;

    private boolean imageGenerated;
    private String username;
    private ObjectId objectID; //mongodb id

    public Recipe() {
        steps = new ArrayList<>();
        objectID = new ObjectId();
    }

    public Recipe(String recipe_name, String mealType, String ingredients, ArrayList<String> steps, String imageurl, boolean imageGenerated, String username) {
        this();

        this.recipe_title = recipe_name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.meal_type = mealType;
        this.imageurl = imageurl;
        this.imageGenerated = imageGenerated;
        this.username = username;
    }

    public void setObjectID(ObjectId id) { this.objectID = id; }
    public ObjectId getObjectID() { return this.objectID; }
    public String getRecipeTitle() { return this.recipe_title; }
    public String getMealType() { return this.meal_type; }
    public void setMealType(String meal_type) { this.meal_type = meal_type; }
    public String getIngredients() { return this.ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }
    public ArrayList<String> getSteps() { return this.steps; }
    public String getImageURL() { return this.imageurl; }
    public boolean equals(Recipe other) { return this.getObjectID().equals(other.getObjectID()); }
    public void setSteps(ArrayList<String> ss) { 
        steps = new ArrayList<>();
        for (String s : ss) {
            steps.add(s);
        } 
    }

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;} 
    public boolean getImageGenerated() {return this.imageGenerated;}
    public void setImageGenerated(boolean imageGenerated) {this.imageGenerated = imageGenerated;}
    public void setRecipeName(String name) {this.recipe_title = name;}
    public void setImageURL(String url) {this.imageurl = url;}

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("name", recipe_title);
        json.put("username", username);
        json.put("meal_type", meal_type);
        json.put("ingredients", ingredients);
        json.put("steps", stepsToString(steps));
        json.put("imageurl", imageurl);
        return json;
    }

    public void setFromJSON(JSONObject json) {
        recipe_title = json.getString("name");
        username = json.getString("username");
        meal_type = json.getString("meal_type");
        ingredients = json.getString("ingredients");
        ArrayList<String> ss = stepsFromString(json.getString("steps"));
        steps = new ArrayList<>();
        for (String s : ss)
            steps.add(s);
        imageurl = json.getString("imageurl");
    }

    //convert steps from arraylist form to string form
    String stepsToString(ArrayList<String> steps) {
        String s = "";
        for (String step : steps) {
            if (s != "") 
                s += ";;"; //separate steps with ;;
            s += step;
        }
        return s;
    }

    //convert steps from string form to arraylist form
    ArrayList<String> stepsFromString(String steps) {
        ArrayList<String> al = new ArrayList<>();
        String[] stepsArray = steps.split(";;");
        for (int i = 0; i < stepsArray.length; i++) {
            al.add(stepsArray[i]);
        }
        return al;
    }

    // public ArrayList<String> getRecipeDetails() {
    //     ArrayList<String> details = new ArrayList<>();
    //     details.add(this.getRecipeTitle()); //0 - name
    //     details.add(this.getUsername()); //1 - username
    //     details.add(this.getMealType()); //2 - meal type
    //     details.add(this.getIngredients()); //3 - ingredients
    //     details.add(this.getImageURL()); //4 - imageurl
    //     for (int i = 0; i < this.getSteps().size(); ++i) { //5 - inf: recipe steps
    //         details.add(this.getSteps().get(i));
    //     }
    //     System.out.println(details.size());
    //     return details;
    // }

    // public void setRecipeDetails(ArrayList<String> details) {
    //     if (details.size() < 6) {
    //         System.out.println("ERROR: recipe details doesn't have the right amount of information: " + details.size());
    //         return;
    //     }
    //     recipe_title = details.get(0);
    //     username = details.get(1);
    //     meal_type = details.get(2);
    //     ingredients = details.get(3);
    //     imageurl = details.get(4);
    //     steps = new ArrayList<>();
    //     for (int i = 5; i < details.size(); i++) {
    //         steps.add(details.get(i));
    //     }
    // }
    
    // public String detailsToString(ArrayList<String> details) {
    //     if (details.size() < 6) {
    //         System.out.println("ERROR: recipe details doesn't have the right amount of information");
    //     }
    //     String s = "";
    //     s += details.get(0) + ";;"; //name
    //     s += details.get(1) + ";;"; //username
    //     s += details.get(2) + ";;"; //meal type
    //     s += details.get(3) + ";;"; //ingredient list
    //     s += details.get(4) + ";;"; //image url
    //     for (int i = 5; i < details.size(); i++) { //steps
    //         s += details.get(i) + ";; ";
    //     }
    //     return s;
    // }

    // public ArrayList<String> detailsToArray(String detailsString) {
    //     ArrayList<String> details = new ArrayList<>();
    //     String[] detailsArray = detailsString.split(";;");
    //     for (int i = 0; i < detailsArray.length; i++) {
    //         details.add(detailsArray[i]);
    //     }
    //     return details;
    // }
} 