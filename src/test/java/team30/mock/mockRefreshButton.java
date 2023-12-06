package team30.mock;

public class mockRefreshButton {
    private String text;
    public mockRefreshButton(String s) {text = s;}

    public String getText() {return text;}

    public void fire(mockRecipeDetail rl) {
        mockChatGPT chatGPT = new mockChatGPT();
        // sample input
        mockRecipe recipe = chatGPT.makeRecipeByChatGPTResponse("breakfast", "tomato and egg skillet", "test username");
        rl.setDRecipe(recipe);
        rl.setRecipe(recipe);
    }
}
