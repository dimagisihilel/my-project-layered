package lk.ijse.pawnshop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.pawnshop.dao.custom.impl.PaymentDetailsDAOImpl;
import lk.ijse.pawnshop.dto.InstallmentDto;
import lk.ijse.pawnshop.dto.PaymentDetailsDto;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class InstallmentFormController {

    public AnchorPane root;
    public TextField txtPaymentId;
    public TextField txtCustomerId;
    public TextField txtCustomerName;
    public TableView<InstallmentDto>tblInstallmentDetails;
    public TableColumn<InstallmentDto,String>colInsallmentId;
    public TableColumn<InstallmentDto, LocalDate> colDueDate;
    public TableColumn<InstallmentDto,Double> colMonthlyInstallment;
    public Button btnPay;
    public TableColumn <InstallmentDto,String>colPaymentStatus;

    public void initialize() {setCellValueFactory();
    }

    public  void txtPaymentIdOnAction(ActionEvent actionEvent) {
        String paymentId = txtPaymentId.getText();

        if (!paymentId.isEmpty()) {
            try {
                PaymentDetailsDto paymentDetails = PaymentDetailsDAOImpl.searchByPaymentId(paymentId);

                if (paymentDetails != null) {
                    txtCustomerId.setText(paymentDetails.getCustomer_id());
                    txtCustomerName.setText(paymentDetails.getName());

                   List<InstallmentDto> installmentDetails = PaymentDetailsDAOImpl.getInstallmentsByPaymentId(paymentId);
                   populateInstallmentDetails(installmentDetails); //methana modify kala
                } else {
                    clearFields();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            clearFields();
        }
    }

    public void setCellValueFactory(){
        colInsallmentId.setCellValueFactory(new PropertyValueFactory<>("installmentId"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colMonthlyInstallment.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPaymentStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));

    }


    private void populateInstallmentDetails(List<InstallmentDto> installmentDetails) {
        ObservableList<InstallmentDto> observableList = FXCollections.observableArrayList(installmentDetails);

        PaymentDetailsDto paymentDetails = null;
        LocalDate paymentDate = null;

        try {
            paymentDetails = PaymentDetailsDAOImpl.searchByPaymentId(txtPaymentId.getText());
            paymentDate = paymentDetails.getDateGranted();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (paymentDetails != null && paymentDate != null) {
            for (int i = 0; i < observableList.size(); i++) {
                InstallmentDto installmentDto = observableList.get(i);
                LocalDate dueDate = paymentDate.plusMonths(i + 1);
                installmentDto.setDueDate(dueDate);
            }
        }
        tblInstallmentDetails.setItems(observableList);
        // setCellValueFactory();
    }




    private void clearFields() {
        txtCustomerId.clear();
        txtCustomerName.clear();
    }
    public void btnPayOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/makepayment_form.fxml"));
        Parent parent = loader.load();
        MakePaymentFormController controller = loader.getController();
        controller.setParentController(this);
        stage.setScene(new Scene(parent));

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(btnPay.getScene().getWindow());
        stage.show();

    }
}
