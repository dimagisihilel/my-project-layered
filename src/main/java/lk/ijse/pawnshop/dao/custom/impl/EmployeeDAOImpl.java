package lk.ijse.pawnshop.dao.custom.impl;

import lk.ijse.pawnshop.dao.SQLUtil;
import lk.ijse.pawnshop.dao.custom.EmployeeDAO;
import lk.ijse.pawnshop.db.DbConnection;
import lk.ijse.pawnshop.dto.EmployeeDTO;
import lk.ijse.pawnshop.entity.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public boolean add(Employee entity) throws SQLException {
       /* Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO employee (emp_id, name, address, position, salary) VALUES (?, ?, ?, ?, ?)");

            preparedStatement.setString(1, employee.getId());
            preparedStatement.setString(2, employee.getName());
            preparedStatement.setString(3, employee.getAddress());
            preparedStatement.setString(4, employee.getPosition());
            preparedStatement.setDouble(5, employee.getSalary());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeStatement(preparedStatement);
        }*/
        return SQLUtil.execute("INSERT INTO employee (emp_id, name, address, position, salary) VALUES (?,?,?,?,?)",entity.getId(),entity.getName(),entity.getAddress(),entity.getPosition(),entity.getSalary());
    }
    @Override
    public List<Employee> getAll() throws SQLException {
        /*Connection connection = DbConnection.getInstance().getConnection();
        List<EmployeeDTO> employees = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM employee");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                EmployeeDTO employee = new EmployeeDTO(
                        resultSet.getString("emp_id"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("position"),
                        resultSet.getDouble("salary"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
        }

        return employees;*/
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee");
        ArrayList<Employee> getAllEmployees = new ArrayList<>();
        while (rst.next()) {
            Employee entity = new Employee(rst.getString("emp_id"),rst.getString("name"),rst.getString("address"),rst.getString("position"),rst.getDouble("salary"));
            getAllEmployees.add(entity);
        }
        return getAllEmployees;
    }
    @Override
    public boolean update(Employee entity) throws SQLException {
        /*Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE employee SET name=?, address=?, position=?, salary=? WHERE emp_id=?");

            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getAddress());
            preparedStatement.setString(3, employee.getPosition());
            preparedStatement.setDouble(4, employee.getSalary());
            preparedStatement.setString(5, employee.getId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeStatement(preparedStatement);
        }*/
        return SQLUtil.execute("UPDATE employee SET name=?, address=?, position=?, salary=? WHERE emp_id=?",entity.getName(),entity.getAddress(),entity.getPosition(),entity.getSalary(),entity.getId());
    }
    @Override
    public boolean delete(String empId) throws SQLException {
        /*Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM employee WHERE emp_id=?");

            preparedStatement.setString(1, empId);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeStatement(preparedStatement);
        }*/
        return SQLUtil.execute("DELETE FROM employee WHERE emp_id=?",empId);
    }

   /* private static void closeStatement(PreparedStatement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }*/
}
