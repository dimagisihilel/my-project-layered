package lk.ijse.pawnshop.dao;

import lk.ijse.pawnshop.dto.UserDto;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> {
    boolean add(T dto) throws SQLException;
    List<T> getAll() throws SQLException;
    boolean update(T dto) throws SQLException;
    boolean delete(String id) throws SQLException;

}
