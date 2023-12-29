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
import lk.ijse.pawnshop.bo.BOFactory;
import lk.ijse.pawnshop.bo.custom.CustomerBO;
import lk.ijse.pawnshop.dao.custom.CustomerDAO;
import lk.ijse.pawnshop.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.pawnshop.db.DbConnection;
import lk.ijse.pawnshop.dto.CustomerDto;
import lk.ijse.pawnshop.dto.tm.CustomerTm;
import lk.ijse.pawnshop.entity.Customer;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ViewCustomerFormController {
    public TableView <CustomerTm> tblCustomer;
    public TableColumn<CustomerTm, TextField> colCusId;
    public TableColumn<CustomerTm, TextField> colCusName;
    public TableColumn<CustomerTm, TextField> colCusContactNo;
    public TableColumn<CustomerTm, TextField> colCusNIC;
    public TableColumn<CustomerTm, TextField> colCusAddress;
    public TableColumn<CustomerTm, TextField> colCusEmail;
    public TableColumn<CustomerTm, TextField> colAction1;
    public TableColumn<CustomerTm, TextField> colAction2;
    public Button btnAddCustomer;
    public Button btnViewCustomer;
    public Button btnPrintCus;
    //CustomerDAO customerDAO = new CustomerDAOImpl();
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.CUSTOMER);

    public void initialize(){
        setData();
        setCellValueFactory();
    }

    private void setData(){
        ArrayList<CustomerTm> customerTms = new ArrayList<>();
        try {
            List<CustomerDto> allCustomers = customerBO.getAllCustomers();
            for (CustomerDto customer : allCustomers) {
                CustomerTm customerTm = convertDtoToTm(customer);
                customerTms.add(customerTm);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        tblCustomer.setItems(FXCollections.observableArrayList(customerTms));

    }

   private void setCellValueFactory(){
       colCusId.setCellValueFactory(new PropertyValueFactory<>("id"));
       colCusName.setCellValueFactory(new PropertyValueFactory<>("name"));
       colCusContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
       colCusNIC.setCellValueFactory(new PropertyValueFactory<>("NIC"));
       colCusAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
       colCusEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
       colAction1.setCellValueFactory(new PropertyValueFactory<>("action1"));
       colAction2.setCellValueFactory(new PropertyValueFactory<>("action2"));
   }
   public void btnAddCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addcustomer_form.fxml"));
        Parent parent = loader.load();
        AddCustomerFormController controller = loader.getController();
        controller.setParentController(this);
        stage.setScene(new Scene(parent));

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(btnAddCustomer.getScene().getWindow());
        stage.show();


   }
    public void btnViewCustomerOnAction(ActionEvent actionEvent) {

    }
    public CustomerTm convertDtoToTm(CustomerDto customerDto){
        CustomerTm customerTm = new CustomerTm();
        TextField idFeild1 = new TextField(customerDto.getId());
        idFeild1.setEditable(false);
        TextField nameField1 = new TextField(customerDto.getName());
        TextField contactNoField1 = new TextField(customerDto.getContactNo());
        TextField NICField1 = new TextField(customerDto.getNIC());
        TextField addressField1 = new TextField(customerDto.getAddress());
        TextField emailField1 = new TextField(customerDto.getEmail());
        Button actionButton1_1 = new Button("Update");
        actionButton1_1.setOnAction(e->{
            try{
                customerDto.setId(idFeild1.getText());
                customerDto.setName(nameField1.getText());
                customerDto.setContactNo(contactNoField1.getText());
                customerDto.setNIC(NICField1.getText());
                customerDto.setAddress(addressField1.getText());
                customerDto.setEmail(emailField1.getText());

                boolean isUpdated = customerBO.updateCustomer(customerDto);
                if(isUpdated){
                    new Alert(Alert.AlertType.INFORMATION, "Customer updated successfully").show();
                    refreshTable();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
                }

            }catch (SQLException ex){
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            }
        });

        Button actionButton1_2 = new Button("Delete");
        actionButton1_2.setOnAction(e->{
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this customer?", ButtonType.YES, ButtonType.NO).showAndWait();
            if (!buttonType.isPresent() || buttonType.get() != ButtonType.YES) {
                return;
            }
            try{
                boolean isDeleted = customerBO.deleteCustomer(customerDto.getId());
                if(isDeleted){
                    new Alert(Alert.AlertType.INFORMATION, "Customer deleted successfully").show();
                    refreshTable();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
                }
            }catch (SQLException ex){
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            }
        });
        customerTm.setId(idFeild1);
        customerTm.setName(nameField1);
        customerTm.setContactNo(contactNoField1);
        customerTm.setNIC(NICField1);
        customerTm.setAddress(addressField1);
        customerTm.setEmail(emailField1);
        customerTm.setAction1(actionButton1_1);
        customerTm.setAction2(actionButton1_2);
        return customerTm;

    }

    public void refreshTable(){
        try{
            List<CustomerDto> allCustomers = customerBO.getAllCustomers();
            ObservableList<CustomerTm> observableList = FXCollections.observableArrayList();
            for (CustomerDto customerDto : allCustomers) {
                observableList.add(convertDtoToTm(customerDto));
            }
            tblCustomer.setItems(observableList);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void btnPrintCusOnAction(ActionEvent actionEvent) throws SQLException, JRException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/report/PawnShopCustomer.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport, //compiled report
                        null,
                        DbConnection.getInstance().getConnection() //database connection
                );

        JasperViewer.viewReport(jasperPrint, false);
    }
}
