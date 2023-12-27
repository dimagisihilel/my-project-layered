package lk.ijse.pawnshop.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.pawnshop.dao.custom.UserDAO;
import lk.ijse.pawnshop.dto.UserDto;
import lk.ijse.pawnshop.dao.custom.impl.UserDAOImpl;

import java.sql.SQLException;

public class AddUserFormController {

    public TextField txtUId;
    public TextField txtUName;
    public TextField txtPassword;

    private UserFormController userFormController;
    UserDAO userDAO = new UserDAOImpl();

    public void setParentController(UserFormController userFormController) {
        this.userFormController = userFormController;
    }

    public void btnSaveUserOnAction(ActionEvent actionEvent) {
        UserDto userDto = collectData();
        try {
            boolean isSaved = userDAO.addUser(userDto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION, "Saved").show();
                userFormController.refreshTable();
            }else{
                new Alert(Alert.AlertType.ERROR, "Not Saved").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private UserDto collectData(){
        UserDto userDto = new UserDto();
        userDto.setId(txtUId.getText());
        userDto.setUsername(txtUName.getText());
        userDto.setPassword(txtPassword.getText());
        return userDto;
    }
}
