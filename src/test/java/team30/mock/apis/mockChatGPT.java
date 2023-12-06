package team30.mock.apis;

import team30.server.ChatGPT;

public class mockChatGPT extends ChatGPT {
    public String generateRecipe(String meal, String ingredients) {
        return "Recipe Name: Apples with Mustard and Honey\nIngredients:\n-3 Red Apples\n-1 Tablespoon Honey\n-2 Tablespoons Mustard\nInstructions:\n1. Preheat oven to 350 degrees F (176 degrees C)\n"
        + "2. Cut the apples into thin slices and arrange them in a 9-inch baking dish.\n3. In a small bowl, mix together the honey and mustard until combined.\n4. Drizzle the honey-mustard mixture over the apples."
        + "\n5. Bake in the preheated oven for 20 minutes or until the apples are tender.\n6. Serve warm and enjoy!";
    }
}
