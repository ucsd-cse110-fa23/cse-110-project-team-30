package team30.account;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.text.*;

import team30.server.AccountDatabase;

class CreateAccountCenter extends VBox{
    private Label CreateAccountTitLabel;
    private Label userNamLabel;
    private Label passwordLabel;
    private Label invalidPrompt;
    private TextField userNameTextField;
    private TextField passwordTextField;

    CreateAccountCenter() {
        this.setWidth(250);;
        this.setHeight(250);

        userNamLabel = new Label("Enter your username");
        passwordLabel = new Label("Enter your password");
        CreateAccountTitLabel = new Label("Create Your Account");
        CreateAccountTitLabel.setFont(new Font(20));
        invalidPrompt = new Label("Username Already Exist!");
        invalidPrompt.setFont(new Font(12));;
        invalidPrompt.setVisible(false);

        userNameTextField = new TextField();
        passwordTextField = new TextField();
        userNameTextField.setMaxWidth(200);
        passwordTextField.setMaxWidth(200);

        this.getChildren().add(CreateAccountTitLabel);
        this.getChildren().add(userNamLabel);
        this.getChildren().add(userNameTextField);
        this.getChildren().add(passwordLabel);
        this.getChildren().add(passwordTextField);
        this.getChildren().add(invalidPrompt);
        this.setSpacing(20);
        this.setAlignment(Pos.TOP_CENTER);
    }

    public TextField getUserNameTextField() {return userNameTextField;}
    public TextField getPasswordTextField() {return passwordTextField;}
    public void showInvalidPrompt() {invalidPrompt.setVisible(true);}
}

class CreateAccountFooter extends HBox{
    private Button createButton;
    private Button backButton;

    CreateAccountFooter() {
        createButton = new Button("Create & login");
        backButton = new Button("back");

        this.getChildren().add(backButton);
        this.getChildren().add(createButton);
        this.setWidth(250);
        this.setHeight(150);

        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #a1f2c8;  -fx-font-weight: bold; -fx-font: 12 arial; -fx-background-radius: 10";
        createButton.setStyle(defaultButtonStyle);
        // Adding hover effect
        createButton.setOnMouseEntered(e -> createButton.setStyle("-fx-font-style: italic; -fx-background-color: #7dedb3;  -fx-font-weight: bold; -fx-font: 12 arial; -fx-background-radius: 10"));
        createButton.setOnMouseExited(e -> createButton.setStyle(defaultButtonStyle));
        
        // Adding click effect
        createButton.setOnMousePressed(e -> createButton.setStyle("-fx-font-style: italic; -fx-background-color: #117e2c;  -fx-font-weight: bold; -fx-font: 12 arial; -fx-background-radius: 10"));
        createButton.setOnMouseReleased(e -> createButton.setStyle(defaultButtonStyle));

        backButton.setStyle(defaultButtonStyle);
        // Adding hover effect
        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-font-style: italic; -fx-background-color: #7dedb3;  -fx-font-weight: bold; -fx-font: 12 arial; -fx-background-radius: 10"));
        backButton.setOnMouseExited(e -> backButton.setStyle(defaultButtonStyle));
        
        // Adding click effect
        backButton.setOnMousePressed(e -> backButton.setStyle("-fx-font-style: italic; -fx-background-color: #117e2c;  -fx-font-weight: bold; -fx-font: 12 arial; -fx-background-radius: 10"));
        backButton.setOnMouseReleased(e -> backButton.setStyle(defaultButtonStyle));

        this.setAlignment(Pos.CENTER);
        this.setSpacing(30);
    }

    public Button getCreateButton() {return createButton;}
    public Button getBackButton() {return backButton;}
}

public class CreateAccount extends BorderPane {
    private CreateAccountCenter CreateAccountCenter;
    private CreateAccountFooter CreateAccountFooter;
    private Button createButton;
    private Button backButton;
    
    private String accName, accPass, tmpAccPass;
    private Button postButton, getButton;

    public CreateAccount() {
        CreateAccountCenter = new CreateAccountCenter();
        CreateAccountFooter = new CreateAccountFooter();
        createButton = CreateAccountFooter.getCreateButton();
        backButton = CreateAccountFooter.getBackButton();

        this.setCenter(CreateAccountCenter);
        this.setBottom(CreateAccountFooter);
        this.setWidth(250);
        this.setHeight(300);

        postButton = new Button();
        getButton = new Button();
    }

    public CreateAccountCenter CreateAccountCenter() {return CreateAccountCenter;}
    public CreateAccountFooter CreateAccountFooter() {return CreateAccountFooter;}
    public Button getCreateButton() {return createButton;}
    public Button getBackButton() {return backButton;}
    
    public String makeNewAccount() { 
        String username = CreateAccountCenter.getUserNameTextField().getText();
        String password = CreateAccountCenter.getPasswordTextField().getText();

        boolean exist = false;
        accName = username;
        tmpAccPass = null;
        getButton.fire();
        if (tmpAccPass != null)
            exist = true;

        if (exist) {
            CreateAccountCenter.showInvalidPrompt();
            CreateAccountCenter.getPasswordTextField().setText("");
            return null;
        }
        else {
            accName = username;
            accPass = password;
            postButton.fire();
            return username;
        }
    }


    public String getAccName() {return accName;}
    public String getAccPass() {return accPass;}
    public Button getPostButton() {return postButton;}
    public void setTmpAccPass(String pass) {tmpAccPass = pass;}
    public void setPostButtonAction(EventHandler<ActionEvent> eventHandler) {postButton.setOnAction(eventHandler);}
    public void setGetButtonAction(EventHandler<ActionEvent> eventHandler) {getButton.setOnAction(eventHandler);}
}
