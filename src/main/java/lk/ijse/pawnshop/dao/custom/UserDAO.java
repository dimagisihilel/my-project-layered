package lk.ijse.pawnshop.dao.custom;

import lk.ijse.pawnshop.dao.CrudDAO;
import lk.ijse.pawnshop.dto.UserDto;
import lk.ijse.pawnshop.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO extends CrudDAO<User> {
   /* boolean addUser(UserDto user) throws SQLException;
    List<UserDto> getAllUsers() throws SQLException;
    boolean updateUser(UserDto user) throws SQLException;
    boolean deleteUser(String id) throws SQLException;*/
}
