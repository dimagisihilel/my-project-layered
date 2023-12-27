package lk.ijse.pawnshop.dao.custom;

import lk.ijse.pawnshop.dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO {
    boolean addCustomer(CustomerDto customerDto) throws SQLException;
    List<CustomerDto> getAllCustomers() throws SQLException;
    boolean updateCustomer(CustomerDto customerDto) throws SQLException;
    boolean deleteCustomer(String customerId) throws SQLException;
    CustomerDto searchById(String customerId) throws SQLException;
}
