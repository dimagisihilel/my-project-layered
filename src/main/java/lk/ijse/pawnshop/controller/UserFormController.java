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
import lk.ijse.pawnshop.bo.custom.UserBO;
import lk.ijse.pawnshop.dao.custom.UserDAO;
import lk.ijse.pawnshop.dto.UserDto;
import lk.ijse.pawnshop.dto.tm.UserTm;
import lk.ijse.pawnshop.dao.custom.impl.UserDAOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserFormController {


    public Button btnSaveUser;
    public TableView<UserTm>tblUser;
    public TableColumn<UserTm, TextField> colUId;
    public TableColumn<UserTm, TextField> colUsername;
    public TableColumn<UserTm, TextField> colpassword;
    public TableColumn<UserTm, Button> colAction1;
    public TableColumn<UserTm, Button>colAction2;
   // UserDAO userDAO = new UserDAOImpl();
    UserBO userBO = (UserBO)BOFactory.getBoFactory().getBO(BOFactory.BOType.USER);


    public void initialize() {
        setData();
        setCellValueFactory();
    }

    public void setData(){
        ArrayList<UserTm> userTms = new ArrayList<>();
        try {
            List<UserDto> allUsers = userBO.getAllUsers();
            for (UserDto user : allUsers) {
                UserTm userTm = convertDtoToTm(user);
                userTms.add(userTm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tblUser.setItems(FXCollections.observableArrayList(userTms));
    }

    private void setCellValueFactory(){
        colUId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colpassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colAction1.setCellValueFactory(new PropertyValueFactory<>("action1"));
        colAction2.setCellValueFactory(new PropertyValueFactory<>("action2"));
    }

    public void btnSaveUserOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/adduser_form.fxml"));
        Parent parent = loader.load();
        AddUserFormController controller = loader.getController();
        controller.setParentController(this);
        stage.setScene(new Scene(parent));

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(btnSaveUser.getScene().getWindow());
        stage.show();
    }

    public UserTm convertDtoToTm(UserDto userDto){
        UserTm userTm = new UserTm();
        TextField idField1 = new TextField(userDto.getId());
        idField1.setEditable(false);
        TextField usernameField1 = new TextField(userDto.getUsername());
        TextField passwordField1 = new TextField(userDto.getPassword());
        Button actionButton1_1 = new Button("Update");
        actionButton1_1.setOnAction(e->{
            try{
                userDto.setId(idField1.getText());
                userDto.setUsername(usernameField1.getText());
                userDto.setPassword(passwordField1.getText());
                boolean isUpdated = userBO.updateUser(userDto);
                if(isUpdated){
                    new Alert(Alert.AlertType.INFORMATION, "User updated successfully").show();
                    refreshTable();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
                }

            }catch (SQLException ex){
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            }
        });
        Button actionButton2_1 = new Button("Delete");
        actionButton2_1.setOnAction(e->{
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this user?", ButtonType.YES, ButtonType.NO).showAndWait();
            if(!buttonType.isPresent() || buttonType.get() != ButtonType.YES){
                return;
            }
            try {
                boolean isDeleted = userBO.deleteUser(userDto.getId());
                if(isDeleted){
                    new Alert(Alert.AlertType.INFORMATION, "User deleted successfully").show();
                    refreshTable();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
                }
            }catch (SQLException ex){
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            }
        });
        userTm.setId(idField1);
        userTm.setUsername(usernameField1);
        userTm.setPassword(passwordField1);
        userTm.setAction1(actionButton1_1);
        userTm.setAction2(actionButton2_1);
        return userTm;
    }

    public void refreshTable(){
        try {
            List<UserDto> allUsers = userBO.getAllUsers();
            ObservableList<UserTm> observableList = FXCollections.observableArrayList();
            for (UserDto userDto : allUsers) {
                observableList.add(convertDtoToTm(userDto));
            }
            tblUser.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
