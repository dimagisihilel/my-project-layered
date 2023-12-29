package lk.ijse.pawnshop.bo.custom.impl;

import lk.ijse.pawnshop.bo.custom.EmployeeBO;
import lk.ijse.pawnshop.dao.DAOFactory;
import lk.ijse.pawnshop.dao.custom.EmployeeDAO;
import lk.ijse.pawnshop.dto.EmployeeDTO;
import lk.ijse.pawnshop.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public boolean addEmployee(EmployeeDTO employee) throws SQLException {
        return employeeDAO.add(new Employee(employee.getId(),employee.getName(),employee.getAddress(),employee.getPosition(),employee.getSalary()));
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() throws SQLException {
        ArrayList<EmployeeDTO> employeeDTOS = new ArrayList<>();
        ArrayList<Employee> employees = (ArrayList<Employee>) employeeDAO.getAll();
        for (Employee employee : employees) {
            employeeDTOS.add(new EmployeeDTO(employee.getId(),employee.getName(),employee.getAddress(),employee.getPosition(),employee.getSalary()));
        }
        return employeeDTOS;
    }

    @Override
    public boolean updateEmployee(EmployeeDTO employee) throws SQLException {
        return employeeDAO.update(new Employee(employee.getId(),employee.getName(),employee.getAddress(),employee.getPosition(),employee.getSalary()));
    }

    @Override
    public boolean deleteEmployee(String empId) throws SQLException {
        return employeeDAO.delete(empId);
    }
}
