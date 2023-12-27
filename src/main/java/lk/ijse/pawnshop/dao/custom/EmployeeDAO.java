package lk.ijse.pawnshop.dao.custom;

import lk.ijse.pawnshop.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDAO {
    boolean addEmployee(EmployeeDTO employee) throws SQLException;
    List<EmployeeDTO> getAllEmployees() throws SQLException;
    boolean updateEmployee(EmployeeDTO employee) throws SQLException;
    boolean deleteEmployee(String empId) throws SQLException;
}
