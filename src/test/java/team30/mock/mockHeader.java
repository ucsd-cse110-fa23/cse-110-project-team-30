package team30.mock;

import javafx.scene.layout.HBox;

public class mockHeader extends HBox{
    private String titleText;
    private mockAddButton addButton;

    public mockHeader() {
        titleText = "PantryPal";
        addButton = new mockAddButton();
    }
    public String getTitleText() {return titleText;}

    public mockAddButton getAddButton() {
        return addButton;
    } 
}
