package lk.ijse.pawnshop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.pawnshop.dao.custom.LoginDAO;
import lk.ijse.pawnshop.dto.UserDto;
import lk.ijse.pawnshop.dao.custom.impl.LoginDAOImpl;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFromController {
    @FXML
    private Button btnForgetpw;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignup;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;
    private LoginDAOImpl loginDAO = new LoginDAOImpl();

    @FXML
    void btnForgetpwOnAction(ActionEvent event) {

    }

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException, SQLException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setPassword(password);

        if (loginDAO.validateLogin(username, password)) {
            navigateToMainWindow();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Login Failed ! Try Again").show();
        }
    }

    private void navigateToMainWindow() throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/main_form.fxml"));
        Scene scene = new Scene(rootNode);

        root.getChildren().clear();
        Stage primaryStage = (Stage) root.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Main Form");

    }

    @FXML
    void btnSignupOnAction(ActionEvent event) {

    }


}
