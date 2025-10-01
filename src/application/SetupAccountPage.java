package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

import databasePart1.*;

/**
 * SetupAccountPage class handles the account setup process for new users.
 * Users provide their userName and password to register.
 */
public class SetupAccountPage {
    
    private final DatabaseHelper databaseHelper;

    public SetupAccountPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public void show(Stage primaryStage) {
        // Input fields for userName and password
        TextField userNameField = new TextField();
        userNameField.setPromptText("Enter userName");
        userNameField.setMaxWidth(250);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.setMaxWidth(250);
        
        // Label to display error messages for invalid input or registration issues
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        
        Button setupButton = new Button("Setup");
        
        setupButton.setOnAction(a -> {
            String userName = userNameField.getText();
            String password = passwordField.getText();
            
            String userNameError = UserNameRecognizer.checkForValidUserName(userName);
            String passwordError = PasswordEvaluator.evaluatePassword(password);
            
            if(!userNameError.isEmpty() || !passwordError.isEmpty()) {
                errorLabel.setText(userNameError + "\n" + passwordError);
                return;
            }
            
            try {
                if(!databaseHelper.doesUserExist(userName)) {
                    User user = new User(userName, password, "user");
                    databaseHelper.register(user);
                    
                    new WelcomeLoginPage(databaseHelper).show(primaryStage, user);
                } else {
                    errorLabel.setText("This userName is taken! Please choose another.");
                }
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
                e.printStackTrace();
            }
        });

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().addAll(userNameField, passwordField, setupButton, errorLabel);

        primaryStage.setScene(new Scene(layout, 800, 400));
        primaryStage.setTitle("Account Setup");
        primaryStage.show();
    }
}
