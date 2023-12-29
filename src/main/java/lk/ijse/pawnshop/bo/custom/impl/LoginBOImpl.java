package lk.ijse.pawnshop.bo.custom.impl;

import lk.ijse.pawnshop.bo.custom.LoginBO;
import lk.ijse.pawnshop.dao.DAOFactory;
import lk.ijse.pawnshop.dao.custom.LoginDAO;

import java.sql.SQLException;

public class LoginBOImpl implements LoginBO {
    LoginDAO loginDAO = (LoginDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.LOGIN);
    @Override
    public boolean validateLogin(String username, String password) throws SQLException {
        return loginDAO.validateLogin(username, password);
    }
}
