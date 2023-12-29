package lk.ijse.pawnshop.dao.custom;

import lk.ijse.pawnshop.dao.CrudDAO;
import lk.ijse.pawnshop.dto.EmployeeDTO;
import lk.ijse.pawnshop.entity.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDAO extends CrudDAO<Employee> {
   /* boolean addEmployee(EmployeeDTO employee) throws SQLException;
    List<EmployeeDTO> getAllEmployees() throws SQLException;
    boolean updateEmployee(EmployeeDTO employee) throws SQLException;
    boolean deleteEmployee(String empId) throws SQLException;*/
}
