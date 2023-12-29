package lk.ijse.pawnshop.dao.custom.impl;

import lk.ijse.pawnshop.dao.SQLUtil;
import lk.ijse.pawnshop.dao.custom.CustomerDAO;
import lk.ijse.pawnshop.db.DbConnection;
import lk.ijse.pawnshop.dto.CustomerDto;
import lk.ijse.pawnshop.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public  boolean add(CustomerDto customerDto) throws SQLException {
        /*Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO customer (customer_id, name, contactNo, NIC, address,email) VALUES (?, ?, ?, ?, ?,?)");

            preparedStatement.setString(1, customerDto.getId());
            preparedStatement.setString(2, customerDto.getName());
            preparedStatement.setString(3, customerDto.getContactNo());
            preparedStatement.setString(4, customerDto.getNIC());
            preparedStatement.setString(5, customerDto.getAddress());
            preparedStatement.setString(6, customerDto.getEmail());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            closeStatement(preparedStatement);
        }*/
        return SQLUtil.execute("INSERT INTO customer (customer_id, name, contactNo, NIC, address,email) VALUES (?,?,?,?,?,?)",customerDto.getId(),customerDto.getName(),customerDto.getContactNo(),customerDto.getNIC(),customerDto.getAddress(),customerDto.getEmail());
    }

    @Override
    public  List<CustomerDto> getAll() throws SQLException {
       /* Connection connection = DbConnection.getInstance().getConnection();
        List<CustomerDto> customers = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM customer");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                CustomerDto customer = new CustomerDto(
                        resultSet.getString("customer_id"),
                        resultSet.getString("name"),
                        resultSet.getString("contactNo"),
                        resultSet.getString("NIC"),
                        resultSet.getString("address"),
                        resultSet.getString("email"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
        }
        return customers;*/
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer");
        ArrayList<CustomerDto> getAllustomers = new ArrayList<>();
        while (rst.next()){
            CustomerDto customerDto = new CustomerDto(rst.getString("customer_id"),rst.getString("name"),rst.getString("contactNo"),rst.getString("NIC"),rst.getString("address"),rst.getString("email"));
            getAllustomers.add(customerDto);
        }
        return getAllustomers;
    }

    @Override
    public boolean update(CustomerDto customerDto) throws SQLException {
       /* Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE customer SET name = ?, contactNo = ?, NIC = ?, address = ?, email = ? WHERE customer_id = ?");

            preparedStatement.setString(1, customerDto.getName());
            preparedStatement.setString(2, customerDto.getContactNo());
            preparedStatement.setString(3, customerDto.getNIC());
            preparedStatement.setString(4, customerDto.getAddress());
            preparedStatement.setString(5, customerDto.getEmail());
            preparedStatement.setString(6, customerDto.getId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            closeStatement(preparedStatement);
        }*/
        return SQLUtil.execute("UPDATE customer SET name = ?, contactNo = ?, NIC = ?, address = ?, email = ? WHERE customer_id = ?",customerDto.getName(),customerDto.getContactNo(),customerDto.getNIC(),customerDto.getAddress(),customerDto.getEmail(),customerDto.getId());

    }

    @Override
    public  boolean delete(String customerId) throws SQLException {
        /*Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM customer WHERE customer_id = ?");

            preparedStatement.setString(1, customerId);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            closeStatement(preparedStatement);
        }*/
        return SQLUtil.execute("DELETE FROM customer WHERE customer_id = ?",customerId);
    }

   /* private static void closeStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }*/

   /* private static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }*/

@Override
    public CustomerDto searchById(String customerId) throws SQLException {
       /* CustomerDto customer = null;
        Connection connection = DbConnection.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM customer WHERE customer_id = ?")) {
            preparedStatement.setString(1, customerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    customer = new CustomerDto();
                    customer.setId(resultSet.getString("customer_id"));
                    customer.setName(resultSet.getString("name"));
                    customer.setContactNo(resultSet.getString("contactNo"));
                    customer.setNIC(resultSet.getString("NIC"));
                    customer.setAddress(resultSet.getString("address"));
                    customer.setEmail(resultSet.getString("email"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;*/
        return SQLUtil.execute("SELECT * FROM customer WHERE customer_id = ?",customerId);

    }




}

