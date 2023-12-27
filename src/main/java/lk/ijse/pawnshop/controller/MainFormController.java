package lk.ijse.pawnshop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFormController {
    public Button btnInstallmentData;
    public Button btnPaymentDetails;
    @FXML
    private Button BtnEmployee;

    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnInventory;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnUser;

    @FXML
    private AnchorPane root;

    public void initialize() throws IOException {
        initializeDashboard();
    }

    private void initializeDashboard() throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        this.root.getChildren().clear();
        this.root.getChildren().add(node);
    }

    @FXML
    void BtnEmployeeOnAction(ActionEvent event) throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/employee_form.fxml"));

        this.root.getChildren().clear();
        this.root.getChildren().add(node);
    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/viewcustomer_form.fxml"));

        this.root.getChildren().clear();
        this.root.getChildren().add(node);

    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        initializeDashboard();
    }

    @FXML
    void btnInventoryOnAction(ActionEvent event) throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/inventory_form.fxml"));

        this.root.getChildren().clear();
        this.root.getChildren().add(node);


    }

    @FXML
    void btnLogoutOnAction(ActionEvent event)throws IOException {

        Parent loginParent = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Stage loginStage = new Stage();

        Scene loginScene = new Scene(loginParent);
        loginStage.setScene(loginScene);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        loginStage.show();

    }

    @FXML
    void btnUserOnAction(ActionEvent event) throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/user_form.fxml"));

        this.root.getChildren().clear();
        this.root.getChildren().add(node);
    }


    public void btnInstallmentDataOnAction(ActionEvent actionEvent) throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/installment_form.fxml"));

        this.root.getChildren().clear();
        this.root.getChildren().add(node);

    }

}
