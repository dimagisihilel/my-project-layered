package lk.ijse.pawnshop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.pawnshop.dao.custom.EmployeeDAO;
import lk.ijse.pawnshop.dto.EmployeeDTO;
import lk.ijse.pawnshop.dto.tm.EmployeeTm;
import lk.ijse.pawnshop.dao.custom.impl.EmployeeDAOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeFormController {

    public TableView <EmployeeTm>tblEmp;

    public TableColumn<EmployeeTm, TextField> colEmpId;
    public TableColumn<EmployeeTm,TextField > colName;
    public TableColumn <EmployeeTm,TextField >colAddress;
    public TableColumn <EmployeeTm,TextField>colPosition;
    public TableColumn <EmployeeTm,TextField>colSalary;
    public TableColumn <EmployeeTm, Button>colAction1;
    public TableColumn <EmployeeTm,Button>colAction2;
    public Button btnAddEmp;
    public Button btnViewEmp;
    EmployeeDAO employeeDAO = new EmployeeDAOImpl();

    public void initialize(){
        setData();
        setCellValueFactory();
    }

    public void setData(){
        ArrayList<EmployeeTm> employeeTms = new ArrayList<>();
        try {
            List<EmployeeDTO> allEmployees = employeeDAO.getAllEmployees();
            for (EmployeeDTO employee : allEmployees) {
                EmployeeTm employeeTm = convertDtoToTm(employee);
                employeeTms.add(employeeTm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tblEmp.setItems(FXCollections.observableArrayList(employeeTms));
    }

    private void setCellValueFactory(){
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colAction1.setCellValueFactory(new PropertyValueFactory<>("action1"));
        colAction2.setCellValueFactory(new PropertyValueFactory<>("action2"));
    }

    public void btnViewEmpOnAction(ActionEvent actionEvent) {
    }
    public void btnAddEmpOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addemployee_form.fxml"));
        Parent parent = loader.load();
        AddEmployeeController controller = loader.getController();
        controller.setParentController(this);
        stage.setScene(new Scene(parent));

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(btnAddEmp.getScene().getWindow());
        stage.show();


    }
    public EmployeeTm convertDtoToTm( EmployeeDTO employeeDto){
        EmployeeTm employeeTm = new EmployeeTm();
        TextField idField1 = new TextField(employeeDto.getId());
        idField1.setEditable(false);
        TextField nameField1 = new TextField(employeeDto.getName());
        TextField addressField1 = new TextField(employeeDto.getAddress());
        TextField positionField1 = new TextField(employeeDto.getPosition());
        TextField salaryField1 = new TextField(String.valueOf(employeeDto.getSalary()));
        Button actionButton1_1 = new Button("Update");
        actionButton1_1.setOnAction(e->{
            try {
                employeeDto.setId(idField1.getText());
                employeeDto.setName(nameField1.getText());
                employeeDto.setAddress(addressField1.getText());
                employeeDto.setPosition(positionField1.getText());
                employeeDto.setSalary(Double.parseDouble(salaryField1.getText()));
                boolean isUpdated = employeeDAO.updateEmployee(employeeDto);
                if(isUpdated){
                    new Alert(Alert.AlertType.INFORMATION, "Employee updated successfully").show();
                    refreshTable();
                }else{
                    new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();

            }

        });
        Button actionButton2_1 = new Button("Delete");
        actionButton2_1.setOnAction(e->{
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this employee?", ButtonType.YES, ButtonType.NO).showAndWait();
            if(!buttonType.isPresent() || buttonType.get() != ButtonType.YES){
                return;
            }
            try {
                boolean isDeleted = employeeDAO.deleteEmployee(employeeDto.getId());
                if(isDeleted){
                    new Alert(Alert.AlertType.INFORMATION, "Employee deleted successfully").show();
                    refreshTable();
                }else{
                    new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            }
        });
        employeeTm.setId(idField1);
        employeeTm.setName(nameField1);
        employeeTm.setAddress(addressField1);
        employeeTm.setPosition(positionField1);
        employeeTm.setSalary(salaryField1);
        employeeTm.setAction1(actionButton1_1);
        employeeTm.setAction2(actionButton2_1);
        return employeeTm;
    }

    public void refreshTable(){
        try {
            List<EmployeeDTO> allEmployees = employeeDAO.getAllEmployees();
            ObservableList<EmployeeTm> observableList = FXCollections.observableArrayList();
            for (EmployeeDTO employeeDTO : allEmployees) {
                observableList.add(convertDtoToTm(employeeDTO));
            }
            tblEmp.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
