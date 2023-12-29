package lk.ijse.pawnshop.bo.custom.impl;

import lk.ijse.pawnshop.bo.SuperBO;
import lk.ijse.pawnshop.bo.custom.UserBO;
import lk.ijse.pawnshop.dao.DAOFactory;
import lk.ijse.pawnshop.dao.custom.UserDAO;
import lk.ijse.pawnshop.dto.UserDto;
import lk.ijse.pawnshop.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBoImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean addUser(UserDto user) throws SQLException {
        return userDAO.add(new User(user.getId(),user.getUsername(),user.getPassword()));
    }

    @Override
    public List<UserDto> getAllUsers() throws SQLException {
        ArrayList<UserDto> userDtos = new ArrayList<>();
        ArrayList<User> users = (ArrayList<User>) userDAO.getAll();
        for (User user : users) {
            userDtos.add(new UserDto(user.getId(),user.getUsername(),user.getPassword()));
        }
        return userDtos;
    }

    @Override
    public boolean updateUser(UserDto user) throws SQLException {
        return userDAO.update(new User(user.getId(),user.getUsername(),user.getPassword()));
    }

    @Override
    public boolean deleteUser(String id) throws SQLException {
        return userDAO.delete(id);
    }
}
