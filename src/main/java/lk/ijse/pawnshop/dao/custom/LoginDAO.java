package lk.ijse.pawnshop.dao.custom;

import lk.ijse.pawnshop.dao.SuperDAO;

import java.sql.SQLException;

public interface LoginDAO extends SuperDAO {
    boolean validateLogin(String username, String password) throws SQLException;
}
