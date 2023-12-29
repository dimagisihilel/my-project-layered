package lk.ijse.pawnshop.bo.custom.impl;

import lk.ijse.pawnshop.bo.custom.CustomerBO;
import lk.ijse.pawnshop.dao.DAOFactory;
import lk.ijse.pawnshop.dao.custom.CustomerDAO;
import lk.ijse.pawnshop.dto.CustomerDto;
import lk.ijse.pawnshop.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean addCustomer(CustomerDto customerDto) throws SQLException {
        return customerDAO.add(new Customer(customerDto.getId(), customerDto.getName(), customerDto.getContactNo(), customerDto.getNIC(), customerDto.getAddress(), customerDto.getEmail()));
    }

    @Override
    public List<CustomerDto> getAllCustomers() throws SQLException {
        ArrayList<CustomerDto> customerDtos = new ArrayList<>();
        ArrayList<Customer> customers = (ArrayList<Customer>) customerDAO.getAll();
        for (Customer customer : customers) {
            customerDtos.add(new CustomerDto(customer.getId(), customer.getName(), customer.getContactNo(), customer.getNIC(), customer.getAddress(), customer.getEmail()));
        }
        return customerDtos;
    }

    @Override
    public boolean updateCustomer(CustomerDto customerDto) throws SQLException {
        return customerDAO.update(new Customer(customerDto.getId(), customerDto.getName(), customerDto.getContactNo(), customerDto.getNIC(), customerDto.getAddress(), customerDto.getEmail()));
    }

    @Override
    public boolean deleteCustomer(String customerId) throws SQLException {
        return customerDAO.delete(customerId);
    }

    @Override
    public CustomerDto searchById(String customerId) throws SQLException {
        return customerDAO.searchById(customerId);
    }
}
