package lk.ijse.pawnshop.dao.custom;

import lk.ijse.pawnshop.dto.UserDto;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    boolean addUser(UserDto user) throws SQLException;
    List<UserDto> getAllUsers() throws SQLException;
    boolean updateUser(UserDto user) throws SQLException;
    boolean deleteUser(String id) throws SQLException;
}
