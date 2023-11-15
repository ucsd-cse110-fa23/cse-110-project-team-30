package team30.mock;

import javafx.scene.layout.HBox;

public class mockHeader extends HBox{
    private String titleText;
    private mockButton addButton;

    public mockHeader() {
        titleText = "PantryPal";
        addButton = new mockButton();
    }
    public String getTitleText() {return titleText;}

    public mockButton getAddButton() {
        return addButton;
    } 
}
