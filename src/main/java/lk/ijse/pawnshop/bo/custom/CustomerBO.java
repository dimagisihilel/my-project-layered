package lk.ijse.pawnshop.bo.custom;

import lk.ijse.pawnshop.bo.SuperBO;
import lk.ijse.pawnshop.dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
     boolean addCustomer(CustomerDto customerDto) throws SQLException;
    List<CustomerDto> getAllCustomers() throws SQLException;
    boolean updateCustomer(CustomerDto customerDto) throws SQLException;
    boolean deleteCustomer(String customerId) throws SQLException;
    CustomerDto searchById(String customerId) throws SQLException;
}
