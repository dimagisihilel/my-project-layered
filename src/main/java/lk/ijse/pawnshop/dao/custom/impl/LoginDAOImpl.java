package lk.ijse.pawnshop.dao.custom.impl;

import lk.ijse.pawnshop.dao.SQLUtil;
import lk.ijse.pawnshop.dao.custom.LoginDAO;
import lk.ijse.pawnshop.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAOImpl {
    //@Override
    public boolean validateLogin(String username, String password) throws SQLException {
       Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM user WHERE username= ? AND password = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, username);
            pstm.setString(2, password);

            try (ResultSet resultSet = pstm.executeQuery()) {
                return resultSet.next();
            }
        }
        //return SQLUtil.execute("SELECT * FROM user WHERE username= ? AND password = ?", username, password);

    }
}

