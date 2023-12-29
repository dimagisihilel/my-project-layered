package lk.ijse.pawnshop.bo.custom;

import lk.ijse.pawnshop.bo.SuperBO;

import java.sql.SQLException;

public interface LoginBO extends SuperBO {
    boolean validateLogin(String username, String password) throws SQLException;
}
