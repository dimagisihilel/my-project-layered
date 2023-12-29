package lk.ijse.pawnshop.dao.custom.impl;

import lk.ijse.pawnshop.dao.SQLUtil;
import lk.ijse.pawnshop.dao.custom.QueryDAO;
import lk.ijse.pawnshop.db.DbConnection;
import lk.ijse.pawnshop.dto.CustomerDto;
import lk.ijse.pawnshop.dto.InstallmentDto;
import lk.ijse.pawnshop.dto.InventoryDto;
import lk.ijse.pawnshop.dto.PaymentDetailsDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public PaymentDetailsDto searchByPaymentId(String paymentId) throws SQLException {

        CustomerDto customerDto = null;
        PaymentDetailsDto paymentDetailsDto = null;
        InventoryDto inventoryDto = null;
       /* Connection connection = DbConnection.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT pd.payment_id, pd.inventory_id, pd.dateGranted, pd.duedate, " +
                        "i.customer_id, i.itemType, i.caratValue, i.pricePerGram, i.weight, i.description, i.assessedValue, " +
                        "c.name AS name " +
                        "FROM paymentDetails pd " +
                        "JOIN inventory i ON pd.inventory_id = i.inventory_id " +
                        "JOIN customer c ON i.customer_id = c.customer_id " +
                        "WHERE pd.payment_id = ?")) {
            preparedStatement.setString(1, paymentId);*/

            try /*(ResultSet resultSet = preparedStatement.executeQuery())*/ {

                ResultSet resultSet  = SQLUtil.execute("SELECT pd.payment_id, pd.inventory_id, pd.dateGranted, pd.duedate, " +
                        "i.customer_id, i.itemType, i.caratValue, i.pricePerGram, i.weight, i.description, i.assessedValue, " +
                        "c.name AS name " +
                        "FROM paymentDetails pd " +
                        "JOIN inventory i ON pd.inventory_id = i.inventory_id " +
                        "JOIN customer c ON i.customer_id = c.customer_id " +
                        "WHERE pd.payment_id = ?", paymentId);

                if (resultSet.next()) {
                    paymentDetailsDto = new PaymentDetailsDto();
                    paymentDetailsDto.setCustomer_id(resultSet.getString("customer_id"));
                    paymentDetailsDto.setName(resultSet.getString("name"));
                    paymentDetailsDto.setPaymentId(resultSet.getString("payment_id"));
                    paymentDetailsDto.setInventoryId(resultSet.getString("inventory_id"));
                    paymentDetailsDto.setDateGranted(resultSet.getDate("dateGranted").toLocalDate());
                    paymentDetailsDto.setDueDate(resultSet.getDate("duedate").toLocalDate());

                    inventoryDto = new InventoryDto();
                    inventoryDto.setInventoryId(resultSet.getString("inventory_id"));
                    inventoryDto.setCustomerId(resultSet.getString("customer_id"));
                    inventoryDto.setItemType(resultSet.getString("itemType"));
                    inventoryDto.setCaratValue(resultSet.getString("caratValue"));
                    inventoryDto.setPricePerGram(resultSet.getDouble("pricePerGram"));
                    inventoryDto.setWeight(resultSet.getDouble("weight"));
                    inventoryDto.setDescription(resultSet.getString("description"));
                    inventoryDto.setAssessedValue(resultSet.getDouble("assessedValue"));

                    customerDto = new CustomerDto();
                    customerDto.setId(resultSet.getString("customer_id"));
                    customerDto.setName(resultSet.getString("name"));

                    inventoryDto.setCustomerDto(customerDto);
                    paymentDetailsDto.setInventoryDto(inventoryDto);

                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        /*} catch (SQLException e) {
            e.printStackTrace();
        }*/
        return paymentDetailsDto;
    }
    @Override
    public List<InstallmentDto> getInstallmentsByPaymentId(String paymentId) throws SQLException {
        List<InstallmentDto> installments = new ArrayList<>();
        /*Connection connection = DbConnection.getInstance().getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT i.installment_id, i.payment_id, i.amount, i.paymentStatus, pd.duedate " +
                        "FROM installments i " +
                        "JOIN paymentDetails pd ON i.payment_id = pd.payment_id " +
                        "WHERE i.payment_id = ?")) {
            preparedStatement.setString(1, paymentId);*/

            try /*(ResultSet resultSet = preparedStatement.executeQuery())*/ {


                ResultSet resultSet  = SQLUtil.execute("SELECT i.installment_id, i.payment_id, i.amount, i.paymentStatus, pd.duedate " +
                        "FROM installments i " +
                        "JOIN paymentDetails pd ON i.payment_id = pd.payment_id " +
                        "WHERE i.payment_id = ?", paymentId);

                while (resultSet.next()) {
                    InstallmentDto installmentDto = new InstallmentDto();
                    installmentDto.setInstallmentId(resultSet.getString("installment_id"));
                    installmentDto.setPaymentId(resultSet.getString("payment_id"));
                    installmentDto.setAmount(resultSet.getDouble("amount"));
                    installmentDto.setPaymentStatus(resultSet.getString("paymentStatus"));

                    LocalDate dueDate = resultSet.getDate(5).toLocalDate();

                    // System.out.println(installmentDto.getDueDate());
                    // System.out.println(resultSet.getString(5));
                    LocalDate parse = LocalDate.parse(resultSet.getString(5));
                    installmentDto.setDueDate(parse);
                    //System.out.println(parse);

                    installments.add(installmentDto);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        /*} catch (SQLException e) {
            e.printStackTrace();
        }*/
        return installments;
    }
    @Override
    public double getGrantedLoanAmount(String paymentId) {
       /* Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;*/

        try {
            /*connection = DbConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT SUM(amount) AS grantedLoanAmount " +
                            "FROM installments " +
                            "WHERE payment_id = ?");
            preparedStatement.setString(1, paymentId);
            resultSet = preparedStatement.executeQuery();*/
            ResultSet resultSet  = SQLUtil.execute("SELECT SUM(amount) AS grantedLoanAmount " +
                    "FROM installments " +
                    "WHERE payment_id = ?", paymentId);

            if (resultSet.next()) {
                return resultSet.getDouble("grantedLoanAmount");
            }

            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } /*finally {
            closeResources(resultSet, preparedStatement, null);
        }*/
    }
    @Override
    public double getDueLoanAmount(String paymentId) {
       /* Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;*/

        try {
           /* connection = DbConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT SUM(amount) AS dueLoanAmount " +
                            "FROM installments " +
                            "WHERE payment_id = ? AND paymentStatus = 'NON-PAID'");
            preparedStatement.setString(1, paymentId);
            resultSet = preparedStatement.executeQuery();*/
            ResultSet resultSet  = SQLUtil.execute("SELECT SUM(amount) AS dueLoanAmount " +
                    "FROM installments " +
                    "WHERE payment_id = ? AND paymentStatus = 'NON-PAID'", paymentId);

            if (resultSet.next()) {
                return resultSet.getDouble("dueLoanAmount");
            }

            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } /*finally {
            closeResources(resultSet, preparedStatement, connection);
        }*/
    }
    @Override
    public String getCustomerEmail(String paymentId) {
       /* Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;*/
        String customerEmail = "";

        try {
            /*connection = DbConnection.getInstance().getConnection();
            String query = "SELECT c.email " +
                    "FROM paymentDetails pd " +
                    "JOIN inventory inv ON pd.inventory_id = inv.inventory_id " +
                    "JOIN customer c ON inv.customer_id = c.customer_id " +
                    "WHERE pd.payment_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, paymentId);
            resultSet = preparedStatement.executeQuery();*/

            ResultSet resultSet  = SQLUtil.execute("SELECT c.email " +
                    "FROM paymentDetails pd " +
                    "JOIN inventory inv ON pd.inventory_id = inv.inventory_id " +
                    "JOIN customer c ON inv.customer_id = c.customer_id " +
                    "WHERE pd.payment_id = ?", paymentId);

            if (resultSet.next()) {
                customerEmail = resultSet.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } /*finally {
            closeResources(resultSet, preparedStatement, connection);
        }*/
        return customerEmail;
    }


   /* private static void closeResources(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
}
