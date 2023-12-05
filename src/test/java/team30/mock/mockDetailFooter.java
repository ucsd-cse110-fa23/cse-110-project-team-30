package team30.mock;

public class mockDetailFooter {
    private mockEditButton edit;
    private mockDeleteButton delete;
    private mockSaveButton save;
    private mockBackButton back;
    private mockRefreshButton refresh;

    public mockDetailFooter() {
        edit = new mockEditButton("Edit");
        delete = new mockDeleteButton("Delete");
        save = new mockSaveButton("Save");
        back = new mockBackButton("Back");
        refresh = new mockRefreshButton("Refresh");
    }

    public mockEditButton getEdit() {return edit;}
    public mockDeleteButton getDelete() {return delete;}
    public mockSaveButton getSave() {return save;}
    public mockBackButton getBack() {return back;} 
    public mockRefreshButton getRefresh() {return refresh;}
}
