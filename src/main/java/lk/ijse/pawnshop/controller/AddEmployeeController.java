package lk.ijse.pawnshop.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.pawnshop.dao.custom.EmployeeDAO;
import lk.ijse.pawnshop.dto.EmployeeDTO;
import lk.ijse.pawnshop.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.pawnshop.util.Regex;
import lk.ijse.pawnshop.util.TextFields;

import java.sql.SQLException;

public class AddEmployeeController {

    public TextField txtEmpId;
    public TextField txtEmpPosition;
    public TextField txtEmpName;
    public TextField txtEmpSalary;
    public TextField txtEmpAddress;
    public Button btnSaveEmp;
    private EmployeeFormController employeeFormController;
    EmployeeDAO employeeDAO = new EmployeeDAOImpl();

    public void setParentController(EmployeeFormController employeeFormController) {
        this.employeeFormController = employeeFormController;
    }

    public void btnSaveEmpOnAction(ActionEvent actionEvent) {
        EmployeeDTO employeeDTO = collectData();
        try {
            boolean isSaved = employeeDAO.addEmployee(employeeDTO);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION, "Saved").show();
                employeeFormController.refreshTable();
            }else{
                new Alert(Alert.AlertType.ERROR, "Not Saved").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }


    }
    private EmployeeDTO collectData(){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(txtEmpId.getText());
        employeeDTO.setName(txtEmpName.getText());
        employeeDTO.setAddress(txtEmpAddress.getText());
        employeeDTO.setPosition(txtEmpPosition.getText());
        employeeDTO.setSalary(Double.parseDouble(txtEmpSalary.getText()));
        return employeeDTO;
    }

    public void txtEmployeeIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.ID,txtEmpId);
    }

    public void txtEmpPositionOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NAME,txtEmpPosition);
    }

    public void txtEmpNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NAME,txtEmpName);
    }

    public void txtEmpSalaryOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.INTEGER,txtEmpSalary);
    }

    public void txtEmpAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.ADDRESS,txtEmpAddress);
    }

    public boolean validate(){
        return Regex.isTextFieldValid(TextFields.ID,txtEmpId.getText()) &&
                Regex.isTextFieldValid(TextFields.NAME,txtEmpName.getText()) &&
                Regex.isTextFieldValid(TextFields.INTEGER,txtEmpSalary.getText()) &&
                Regex.isTextFieldValid(TextFields.NAME,txtEmpPosition.getText()) &&
                Regex.isTextFieldValid(TextFields.ADDRESS,txtEmpAddress.getText());
    }
}
