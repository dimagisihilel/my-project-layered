package lk.ijse.pawnshop.dao.custom.impl;

import lk.ijse.pawnshop.dao.SQLUtil;
import lk.ijse.pawnshop.dao.custom.InstallmentDAO;
import lk.ijse.pawnshop.dao.custom.InventoryDAO;
import lk.ijse.pawnshop.dao.custom.PaymentDetailsDAO;
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

public class PaymentDetailsDAOImpl implements PaymentDetailsDAO {
    static InstallmentDAO installmentDAO = new InstallmentDAOImpl();
    static InventoryDAO inventoryDAO = new InventoryDAOImpl();

    @Override
    public String generateNextPaymentId() {
        String prefix = "P-";
        String nextPaymentId = null;

        try {
            /*Connection connection = DbConnection.getInstance().getConnection();
            String sql = "SELECT MAX(CAST(SUBSTRING(payment_id, 3) AS SIGNED)) AS maxId FROM paymentDetails";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {*/
            ResultSet resultSet = SQLUtil.execute("SELECT MAX(CAST(SUBSTRING(payment_id, 3) AS SIGNED)) AS maxId FROM paymentDetails");

                if (resultSet.next()) {
                    int maxId = resultSet.getInt("maxId");
                    int nextId = maxId + 1;

                    nextPaymentId = prefix + String.format("%05d", nextId);
                }
            //}
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nextPaymentId;
    }
    @Override
    public boolean issueLoan(PaymentDetailsDto paymentDetailsDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try{
            boolean isItemAdded = inventoryDAO.saveInventory(paymentDetailsDto.getInventoryDto());
            if(isItemAdded){
                boolean isPaymentDetailsAdded = savePaymentDetails(paymentDetailsDto);
                if(isPaymentDetailsAdded){
                    boolean isAllAdded = installmentDAO.save(paymentDetailsDto.getInstallmentDtoList());
                    if(isAllAdded){
                        connection.commit();
                        return true;
                    }
                }
            }
         connection.rollback();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        finally {
            connection.setAutoCommit(true);
        }
        return false;

    }
    @Override
    public boolean savePaymentDetails(PaymentDetailsDto paymentDetailsDto) throws SQLException {
        /*Connection connection = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO paymentDetails VALUES(?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, paymentDetailsDto.getPaymentId());
        ps.setString(2, paymentDetailsDto.getInventoryId());
        ps.setString(3, paymentDetailsDto.getDateGranted().toString());
        ps.setString(4, paymentDetailsDto.getDueDate().toString());
        return ps.executeUpdate() > 0;*/
        return SQLUtil.execute("INSERT INTO paymentDetails VALUES(?,?,?,?)",paymentDetailsDto.getPaymentId(),paymentDetailsDto.getInventoryId(),paymentDetailsDto.getDateGranted().toString(),paymentDetailsDto.getDueDate().toString());

    }

    /*public static PaymentDetailsDto searchByPaymentId(String paymentId) throws SQLException {

        CustomerDto customerDto = null;
        PaymentDetailsDto paymentDetailsDto = null;
        InventoryDto inventoryDto = null;
        Connection connection = DbConnection.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT pd.payment_id, pd.inventory_id, pd.dateGranted, pd.duedate, " +
                        "i.customer_id, i.itemType, i.caratValue, i.pricePerGram, i.weight, i.description, i.assessedValue, " +
                        "c.name AS name " +
                        "FROM paymentDetails pd " +
                        "JOIN inventory i ON pd.inventory_id = i.inventory_id " +
                        "JOIN customer c ON i.customer_id = c.customer_id " +
                        "WHERE pd.payment_id = ?")) {
            preparedStatement.setString(1, paymentId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
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

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paymentDetailsDto;
    }*/

    /*public static List<InstallmentDto> getInstallmentsByPaymentId(String paymentId) throws SQLException {
        List<InstallmentDto> installments = new ArrayList<>();
        Connection connection = DbConnection.getInstance().getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT i.installment_id, i.payment_id, i.amount, i.paymentStatus, pd.duedate " +
                        "FROM installments i " +
                        "JOIN paymentDetails pd ON i.payment_id = pd.payment_id " +
                        "WHERE i.payment_id = ?")) {
            preparedStatement.setString(1, paymentId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return installments;
    }*/
    @Override
    public List<String> getNonPaidInstallmentIds(String paymentId) throws SQLException {
        List<String> nonPaidInstallmentIds = new ArrayList<>();
        /*Connection connection = DbConnection.getInstance().getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT installment_id FROM installments WHERE payment_id = ? AND paymentStatus = 'NON-PAID'")) {
            preparedStatement.setString(1, paymentId);*/

            try /*(ResultSet resultSet = preparedStatement.executeQuery())*/ {
            ResultSet resultSet = SQLUtil.execute("SELECT installment_id FROM installments WHERE payment_id = ? AND paymentStatus = 'NON-PAID'", paymentId);
                while (resultSet.next()) {
                    String installmentId = resultSet.getString("installment_id");
                    nonPaidInstallmentIds.add(installmentId);
                }
           // }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nonPaidInstallmentIds;
    }
    @Override
    public double getMonthlyInstallmentAmount(String installmentId) throws SQLException {
        double monthlyInstallmentAmount = 0.0;
/*        Connection connection = DbConnection.getInstance().getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT amount FROM installments WHERE installment_id = ?")) {
            preparedStatement.setString(1, installmentId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {*/
            ResultSet resultSet = SQLUtil.execute("SELECT amount FROM installments WHERE installment_id = ?", installmentId);
                if (resultSet.next()) {
                    monthlyInstallmentAmount = resultSet.getDouble("amount");
                }
           // }
        /*} catch (SQLException e) {
            e.printStackTrace();
        }*/

        return monthlyInstallmentAmount;
    }
    @Override
    public boolean updatePaymentStatus(String paymentId, String installmentId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE installments SET paymentStatus = 'PAID' WHERE payment_id = ? AND installment_id = ?")) {
            preparedStatement.setString(1, paymentId);
            preparedStatement.setString(2, installmentId);

            int rowsUpdated = preparedStatement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        /*try{
            int rowsUpdated =  SQLUtil.execute("UPDATE installments SET paymentStatus = 'PAID' WHERE payment_id = ? AND installment_id = ?", paymentId, installmentId);
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }*/
    }

   /* public static double getGrantedLoanAmount(String paymentId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DbConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT SUM(amount) AS grantedLoanAmount " +
                            "FROM installments " +
                            "WHERE payment_id = ?");
            preparedStatement.setString(1, paymentId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("grantedLoanAmount");
            }

            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            closeResources(resultSet, preparedStatement, null);
        }
    }*/

    /*public static double getDueLoanAmount(String paymentId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DbConnection.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT SUM(amount) AS dueLoanAmount " +
                            "FROM installments " +
                            "WHERE payment_id = ? AND paymentStatus = 'NON-PAID'");
            preparedStatement.setString(1, paymentId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("dueLoanAmount");
            }

            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
    }*/

    /*public static String getCustomerEmail(String paymentId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String customerEmail = "";

        try {
            connection = DbConnection.getInstance().getConnection();
            String query = "SELECT c.email " +
                    "FROM paymentDetails pd " +
                    "JOIN inventory inv ON pd.inventory_id = inv.inventory_id " +
                    "JOIN customer c ON inv.customer_id = c.customer_id " +
                    "WHERE pd.payment_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, paymentId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                customerEmail = resultSet.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }

        return customerEmail;
    }*/


   /* private static void closeResources(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
}
