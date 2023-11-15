package team30.mock;

import java.util.ArrayList;

public class mockList{
    private mockButton button;
    private ArrayList<mockRecipe> list;

    public ArrayList<mockRecipe> getChildren() {return list;}
    public mockList() {
        button = new mockButton();
        list = new ArrayList<>();
    }

    public void updateTaskIndices() {
        int index = 1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof mockRecipe) {
                ((mockRecipe)list.get(i)).setIndex(Integer.toString(index));
                index++;
            }
        }
    }

    public mockButton getButton() {return button;} 

    void removeRecipe(mockRecipe recipeToRemove){
        this.getChildren().removeIf(recipe -> recipe instanceof mockRecipe && ((mockRecipe)recipe).equals(recipeToRemove));
        this.updateTaskIndices();
    }
}
