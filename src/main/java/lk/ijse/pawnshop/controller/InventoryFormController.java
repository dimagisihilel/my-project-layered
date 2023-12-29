package lk.ijse.pawnshop.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import lk.ijse.pawnshop.dao.custom.CustomerDAO;
import lk.ijse.pawnshop.dao.custom.InventoryDAO;
import lk.ijse.pawnshop.dao.custom.PaymentDetailsDAO;
import lk.ijse.pawnshop.dao.custom.impl.InventoryDAOImpl;
import lk.ijse.pawnshop.dao.custom.impl.PaymentDetailsDAOImpl;
import lk.ijse.pawnshop.dto.CustomerDto;
import lk.ijse.pawnshop.dto.InstallmentDto;
import lk.ijse.pawnshop.dto.InventoryDto;
import lk.ijse.pawnshop.dto.PaymentDetailsDto;
import lk.ijse.pawnshop.dto.cm.CaratValuesCm;
import lk.ijse.pawnshop.dao.custom.impl.CustomerDAOImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class InventoryFormController {
    public TextField txtCustomerName;
    public TextField txtPaymentId;
    public ComboBox<CustomerDto>cmbCustomerId;
    @FXML
    private AnchorPane root;
    @FXML
    private ComboBox<CaratValuesCm> cmbCaratvalue;

    @FXML
    private ComboBox<String> cmbItemType;


    @FXML
    private TextField txtAssessedValue;

    @FXML
    private DatePicker txtDateGranted;

    @FXML
    private TextField txtDescription;

    @FXML
    private DatePicker txtDueDate;

    @FXML
    private TextField txtGrantingAmount;

    @FXML
    private TextField txtInventoryId;

    @FXML
    private TextField txtMonthlyInstallment;

    @FXML
    private TextField txtNoOfInstallments;

    @FXML
    private TextField txtPricePerGram;

    @FXML
    private TextField txtRemainingAmount;

    @FXML
    private TextField txtTotalInterest;

    @FXML
    private TextField txtWeight;

    CustomerDAO customerDAO = new CustomerDAOImpl();
    InventoryDAO inventoryDAO = new InventoryDAOImpl();
    PaymentDetailsDAO paymentDetailsDAO = new PaymentDetailsDAOImpl();

    public void initialize(){
        String id = inventoryDAO.generateNextId();
        txtInventoryId.setText(id);
        setDataForItemType();
        setDataForCaratValues();
        setConverterForCaratValue();
        String pId = paymentDetailsDAO.generateNextPaymentId();
        txtPaymentId.setText(pId);
        setCustomerComboBox();
        cmbCustomerId.setConverter(new StringConverter<CustomerDto>() {

            @Override
            public String toString(CustomerDto customerDto) {
                if (customerDto == null) {
                    return "";
                }
                return customerDto.getId();
            }

            @Override
            public CustomerDto fromString(String s) {
                return null;
            }
        });

    }
    public void setCustomerComboBox(){
        try {
            List<CustomerDto> customers = customerDAO.getAll();
            ObservableList<CustomerDto> observableList = FXCollections.observableArrayList(customers);
            cmbCustomerId.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setDataForItemType(){
        String [] types = {"Rings","Earrings","Bracelet","Necklace","Chain","Bangles","Pendents","Other"};
        ObservableList<String> strings = FXCollections.observableArrayList(types);
        cmbItemType.setItems(strings);
    }

    public void setDataForCaratValues(){
        CaratValuesCm ob1 = new CaratValuesCm();
        ob1.setCaratValue(24);
        ob1.setPricePerGram(22000);

        CaratValuesCm ob2 = new CaratValuesCm();
        ob2.setCaratValue(22);
        ob2.setPricePerGram(20000);

        CaratValuesCm ob3 = new CaratValuesCm();
        ob3.setCaratValue(21);
        ob3.setPricePerGram(18000);

        CaratValuesCm [] values = {ob1,ob2,ob3};
        ObservableList<CaratValuesCm> objects = FXCollections.observableArrayList(values);
        cmbCaratvalue.setItems(objects);

    }

    public void setConverterForCaratValue(){
        cmbCaratvalue.setConverter(new StringConverter<CaratValuesCm>() {
            @Override
            public String toString(CaratValuesCm caratValuesCm) {
                return caratValuesCm == null ? "" : caratValuesCm.getCaratValue()+" K";
            }

            @Override
            public CaratValuesCm fromString(String s) {
                return null;
            }
        });
    }
    @FXML
    void btnSaveInventoryOnAction(ActionEvent event) {
       /* InventoryDto inventoryDto = collectData();

        try {
            boolean isSaved = InventoryDAOImpl.saveInventory(inventoryDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Saved").show();
                // refresh or something
            } else {
                new Alert(Alert.AlertType.ERROR, "Not Saved").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }*/

        InventoryDto item = collectData();


        PaymentDetailsDto ob = new PaymentDetailsDto();
        ob.setPaymentId(txtPaymentId.getText());
        ob.setInventoryId(item.getInventoryId());
        ob.setDateGranted(txtDateGranted.getValue());
        ob.setDueDate(txtDueDate.getValue());

        int noOfInstallments = Integer.parseInt(txtNoOfInstallments.getText());

        List<InstallmentDto> installments = new ArrayList<>();

        double m_val = calculate();

        for (int i = 1; i <= noOfInstallments; i++) {
            InstallmentDto installmentDto = new InstallmentDto();
            //installmentDto.setInstallmentId("INST-" + i);
            installmentDto.setInstallmentId(String.format("INST-%02d", i));
            installmentDto.setPaymentId(ob.getPaymentId());
            installmentDto.setPaymentStatus("NON-PAID");
            installmentDto.setAmount(m_val);
            installments.add(installmentDto);
        }
        ob.setInventoryDto(item);
        ob.setInstallmentDtoList(installments);

        try {
            boolean isIssued = paymentDetailsDAO.issueLoan(ob);
            if(isIssued){
                new Alert(Alert.AlertType.INFORMATION,"Issued").show();
                    clearFields();
            }else{
                new Alert(Alert.AlertType.ERROR,"Not Issued").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private InventoryDto collectData() {
        InventoryDto inventoryDto = new InventoryDto();

        inventoryDto.setInventoryId(txtInventoryId.getText());
        inventoryDto.setCustomerId(cmbCustomerId.getSelectionModel().getSelectedItem().getId());
        inventoryDto.setItemType(cmbItemType.getValue());
        inventoryDto.setCaratValue(String.valueOf(cmbCaratvalue.getValue().getCaratValue()));
        inventoryDto.setPricePerGram(parseCurrencyValue(txtPricePerGram.getText()));
        inventoryDto.setWeight(parseWeightValue(txtWeight.getText()));
        inventoryDto.setDescription(txtDescription.getText());
        inventoryDto.setAssessedValue(parseCurrencyValue(txtAssessedValue.getText()));

        return inventoryDto;
    }
    private double parseWeightValue(String weightValue) {
        return Double.parseDouble(weightValue);
    }

    @FXML
    void cmbCaratvalueOnAction(ActionEvent event) {
        CaratValuesCm selectedItem = cmbCaratvalue.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            return;
        }
        txtPricePerGram.setText("Rs. " + selectedItem.getPricePerGram());
    }

    @FXML
    void cmbItemTypeOnAction(ActionEvent event) {

    }

    @FXML
    void txtTotalInterestOnAction(ActionEvent event) {
        calculateMonthlyInstallment();
    }

    private void calculateMonthlyInstallment() {
        calculate();

    }

    private double calculate(){
        try {
            double totalInterestRate = Double.parseDouble(txtTotalInterest.getText());
            double grantedAmount = parseCurrencyValue(txtGrantingAmount.getText());
            int noOfInstallments = Integer.parseInt(txtNoOfInstallments.getText());

            double totalInterest = (totalInterestRate / 100) * grantedAmount;
            double totalPayableAmount = grantedAmount + totalInterest;
            double monthlyInstallment = totalPayableAmount / noOfInstallments;

            txtMonthlyInstallment.setText("Rs. " + monthlyInstallment);
            return monthlyInstallment;
        } catch (NumberFormatException e) {
            txtMonthlyInstallment.setText("Invalid Input");
        }
        return -1;
    }

    @FXML
    void txtWeightOnAction(ActionEvent event) {
        calculateAssessedValue();
    }
    private void calculateAssessedValue() {
        CaratValuesCm selectedCarat = cmbCaratvalue.getSelectionModel().getSelectedItem();

        if (selectedCarat == null) {
            return;
        }

        try {
            double weight = Double.parseDouble(txtWeight.getText());
            double pricePerGram = selectedCarat.getPricePerGram();

            double assessedValue = weight * pricePerGram;
            txtAssessedValue.setText("Rs. " + assessedValue);
        } catch (NumberFormatException e) {
            txtAssessedValue.setText("Invalid Input");
        }
    }

   /* public void txtCustomerIdOnAction(ActionEvent actionEvent) {
        try {
            CustomerDto customer = CustomerDAOImpl.searchById(txtCustomerId.getText());
            txtCustomerName.setText(customer.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    public void txtGrantingAmountOnAction(ActionEvent actionEvent) {
        calculateRemainingAmount();
    }

    private void calculateRemainingAmount() {
        try {
            double assessedValue = parseCurrencyValue(txtAssessedValue.getText());
            double grantingAmount = parseCurrencyValue(txtGrantingAmount.getText());

            if (grantingAmount <= assessedValue) {
                double remainingAmount = assessedValue - grantingAmount;
                txtRemainingAmount.setText("Rs. " + remainingAmount);
            } else {
                txtRemainingAmount.setText("Invalid Granting Amount");
            }
        } catch (NumberFormatException e) {
            txtRemainingAmount.setText("Invalid Input");
        }
    }

    private double parseCurrencyValue(String currencyValue) {
        // Helper method to parse currency values like "Rs. 12345" to a double
        return Double.parseDouble(currencyValue.replace("Rs. ", ""));
    }

    public void txtNoOfInstallmentsOnAction(ActionEvent actionEvent) {
        calculateDueDate();
    }

    private void calculateDueDate() {
        try {
            int noOfInstallments = Integer.parseInt(txtNoOfInstallments.getText());

            if (noOfInstallments > 0) {
                LocalDate grantedDate = txtDateGranted.getValue();
                if (grantedDate != null) {
                    LocalDate dueDate = grantedDate.plusMonths(noOfInstallments);
                    txtDueDate.setValue(dueDate);
                } else {
                    txtDueDate.setValue(null);
                }
            } else {
                txtDueDate.setValue(null);
            }
        } catch (NumberFormatException e) {
            txtDueDate.setValue(null);
        }
    }
    void SaveInstallments() {
        int noOfInstallments = Integer.parseInt(txtNoOfInstallments.getText());

        List<InstallmentDto> installments = new ArrayList<>();

        for (int i = 1; i <= noOfInstallments; i++) {
            InstallmentDto installmentDto = new InstallmentDto();
            installmentDto.setInstallmentId("INST-" + i);
            installments.add(installmentDto);
        }
    }

    public void cmbCustomerIdOnAction(ActionEvent actionEvent) {
        CustomerDto selectedItem = cmbCustomerId.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            return;

        }
        txtCustomerName.setText(selectedItem.getName());
    }
    private void clearFields(){
        //txtPaymentId.setText("");
       // txtInventoryId.setText("");
        cmbCustomerId.getSelectionModel().clearSelection();
        txtCustomerName.setText("");
        cmbItemType.getSelectionModel().clearSelection();
        cmbCaratvalue.getSelectionModel().clearSelection();
        txtPricePerGram.setText("");
        txtWeight.setText("");
        txtDescription.setText("");
        txtAssessedValue.setText("");
        txtGrantingAmount.setText("");
        txtRemainingAmount.setText("");
        txtDateGranted.setValue(null);
        txtNoOfInstallments.setText("");
        txtDueDate.setValue(null);
        txtTotalInterest.setText("");
        txtMonthlyInstallment.setText("");
    }
}
