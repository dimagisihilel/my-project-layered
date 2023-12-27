package lk.ijse.pawnshop.dao.custom;

import java.sql.SQLException;

public interface LoginDAO {
    boolean validateLogin(String username, String password) throws SQLException;
}
