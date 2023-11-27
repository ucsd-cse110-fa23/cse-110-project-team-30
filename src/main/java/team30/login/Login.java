package team30.login;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.ArrayChangeListener;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import javafx.geometry.Rectangle2D;
import java.io.*;
import javafx.util.Pair;
import team30.server.AccountDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javafx.scene.paint.Color;

import com.mongodb.client.FindIterable;
import org.bson.Document;

class LoginCenter extends VBox{
    private Label loginTitLabel;
    private Label userNamLabel;
    private Label passwordLabel;
    private Label invalidPrompt;
    private TextField userNameTextField;
    private TextField passwordTextField;

    LoginCenter() {
        this.setWidth(400);;
        this.setHeight(250);

        userNamLabel = new Label("username: ");
        passwordLabel = new Label("password: ");
        loginTitLabel = new Label("Log In Your Account");
        loginTitLabel.setFont(new Font(20));
        invalidPrompt = new Label("Incorrect password or username not exists");
        invalidPrompt.setFont(new Font(12));;
        invalidPrompt.setVisible(false);

        userNameTextField = new TextField();
        passwordTextField = new TextField();
        
        HBox userNameHBox = new HBox();
        HBox passwodHBox = new HBox();

        userNameHBox.getChildren().add(userNamLabel);
        userNameHBox.getChildren().add(userNameTextField);
        userNameHBox.setAlignment(Pos.CENTER_LEFT);
        userNameHBox.setMaxWidth(400);
        userNameHBox.setMaxHeight(115);
        passwodHBox.getChildren().add(passwordLabel);
        passwodHBox.getChildren().add(passwordTextField);
        passwodHBox.setAlignment(Pos.CENTER_LEFT);
        passwodHBox.setMaxWidth(400);
        passwodHBox.setMaxHeight(115);

        this.getChildren().add(loginTitLabel);
        this.getChildren().addAll(userNameHBox, passwodHBox);
        this.getChildren().add(invalidPrompt);
        this.setSpacing(20);
        this.setAlignment(Pos.TOP_CENTER);
    }

    public TextField getUserNameTextField() {return userNameTextField;}
    public TextField getPasswordTextField() {return passwordTextField;}
    public void showInvalidPrompt() {invalidPrompt.setVisible(true);}
    public void setInvalidPassword() {invalidPrompt.setText("Incorrect Password");}
    public void setInvalidUsername() {invalidPrompt.setText("Username Does Not Exist");}
}

class LoginFooter extends HBox{
    private Button loginButton;
    private Button createButton;

    LoginFooter() {
        loginButton = new Button("Log In");
        createButton = new Button("Create Account");

        this.getChildren().add(loginButton);
        this.getChildren().add(createButton);
        this.setWidth(250);
        this.setHeight(150);

        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #a1f2c8;  -fx-font-weight: bold; -fx-font: 12 arial; -fx-background-radius: 10";
        loginButton.setStyle(defaultButtonStyle);
        // Adding hover effect
        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-font-style: italic; -fx-background-color: #7dedb3;  -fx-font-weight: bold; -fx-font: 12 arial; -fx-background-radius: 10"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle(defaultButtonStyle));
        
        // Adding click effect
        loginButton.setOnMousePressed(e -> loginButton.setStyle("-fx-font-style: italic; -fx-background-color: #117e2c;  -fx-font-weight: bold; -fx-font: 12 arial; -fx-background-radius: 10"));
        loginButton.setOnMouseReleased(e -> loginButton.setStyle(defaultButtonStyle));

        createButton.setStyle(defaultButtonStyle);
        // Adding hover effect
        createButton.setOnMouseEntered(e -> createButton.setStyle("-fx-font-style: italic; -fx-background-color: #7dedb3;  -fx-font-weight: bold; -fx-font: 12 arial; -fx-background-radius: 10"));
        createButton.setOnMouseExited(e -> createButton.setStyle(defaultButtonStyle));
        
        // Adding click effect
        createButton.setOnMousePressed(e -> createButton.setStyle("-fx-font-style: italic; -fx-background-color: #117e2c;  -fx-font-weight: bold; -fx-font: 12 arial; -fx-background-radius: 10"));
        createButton.setOnMouseReleased(e -> createButton.setStyle(defaultButtonStyle));


        this.setAlignment(Pos.CENTER);
        this.setSpacing(30);
    }

    public Button getLoginButton() {return loginButton;}
    public Button getCreateButton() {return createButton;}
}

public class Login extends BorderPane{
    private LoginCenter loginCenter;
    private LoginFooter loginFooter;
    private Button loginButton;
    private Button createButton;
    private AccountDatabase db;

    public Login() {
        loginCenter = new LoginCenter();
        loginFooter = new LoginFooter();
        loginButton = loginFooter.getLoginButton();
        createButton = loginFooter.getCreateButton();
        db = new AccountDatabase();

        this.setCenter(loginCenter);
        this.setBottom(loginFooter);
        this.setWidth(250);
        this.setHeight(300);
    }

    public LoginCenter getLoginCenter() {return loginCenter;}
    public LoginFooter getLoginFooter() {return loginFooter;}
    public Button getLoginButton() {return loginButton;}
    public Button getCreateButton() {return createButton;}
    
    public int validUser() {
        String username = loginCenter.getUserNameTextField().getText();
        String password = loginCenter.getPasswordTextField().getText();

        int match = db.validUser(username, password);
        if (match == 1) {
            loginCenter.setInvalidPassword();
            loginCenter.showInvalidPrompt();
            loginCenter.getPasswordTextField().setText("");
        }
        else if (match == -1) {
            loginCenter.setInvalidUsername();
            loginCenter.showInvalidPrompt();
            loginCenter.getPasswordTextField().setText("");
        }

        return match;
    }
}
