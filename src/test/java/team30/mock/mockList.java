package team30.mock;

import java.util.ArrayList;

public class mockList{
    private mockAddButton add;
    private ArrayList<mockRecipe> list;

    public ArrayList<mockRecipe> getChildren() {return list;}
    public mockList() {
        add = new mockAddButton();
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

    public mockAddButton getAddButton() {return add;} 

    void removeRecipe(mockRecipe recipeToRemove){
        this.getChildren().removeIf(recipe -> recipe instanceof mockRecipe && ((mockRecipe)recipe).equals(recipeToRemove));
        this.updateTaskIndices();
    }
}
