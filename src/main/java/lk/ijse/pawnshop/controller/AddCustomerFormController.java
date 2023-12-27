package lk.ijse.pawnshop.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.pawnshop.dao.custom.CustomerDAO;
import lk.ijse.pawnshop.dto.CustomerDto;
import lk.ijse.pawnshop.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.pawnshop.util.Regex;
import lk.ijse.pawnshop.util.TextFields;

import java.sql.SQLException;

public class AddCustomerFormController {
    public TextField txtCustId;
    public TextField txtCustName;
    public TextField txtCustContactNo;
    public TextField txtCustNIC;
    public TextField txtCustAddress;
    public TextField txtCustEmail;
    public Button btnSaveCustomer;

    private ViewCustomerFormController viewCustomerFormController;

    CustomerDAO customerDAO = new CustomerDAOImpl();

    public void setParentController(ViewCustomerFormController viewCustomerFormController){
        this.viewCustomerFormController = viewCustomerFormController;
    }


    public void btnSaveCustomerOnAction(ActionEvent actionEvent) {
        if(!validate()){
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields with valid data").show();
            return;
        }
        CustomerDto customerDto = collectData();
        try {
            boolean isSaved = customerDAO.addCustomer(customerDto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION, "Saved").show();
                viewCustomerFormController.refreshTable();
            }else{
                new Alert(Alert.AlertType.ERROR, "Not Saved").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private CustomerDto collectData(){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(txtCustId.getText());
        customerDto.setName(txtCustName.getText());
        customerDto.setContactNo(txtCustContactNo.getText());
        customerDto.setNIC(txtCustNIC.getText());
        customerDto.setAddress(txtCustAddress.getText());
        customerDto.setEmail(txtCustEmail.getText());
        return customerDto;
    }


    public void txtCustomerIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.ID,txtCustId);
    }

    public void txtCustomerNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NAME,txtCustName);
    }

    public void txtCustomerContactNoOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.PHONE,txtCustContactNo);
    }

    public void txtCustomerNICOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.LANKAN_ID,txtCustNIC);
    }

    public void txtCustomerAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.ADDRESS,txtCustAddress);
    }

    public void txtCustomerEmailOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.EMAIL,txtCustEmail);
    }
    public boolean validate(){
        return Regex.isTextFieldValid(TextFields.ID,txtCustId.getText()) &&
                Regex.isTextFieldValid(TextFields.NAME,txtCustName.getText()) &&
                Regex.isTextFieldValid(TextFields.PHONE,txtCustContactNo.getText()) &&
                Regex.isTextFieldValid(TextFields.LANKAN_ID,txtCustNIC.getText()) &&
                Regex.isTextFieldValid(TextFields.ADDRESS,txtCustAddress.getText()) &&
                Regex.isTextFieldValid(TextFields.EMAIL,txtCustEmail.getText());
    }
}
