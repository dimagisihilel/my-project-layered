package lk.ijse.pawnshop.dao.custom;

import lk.ijse.pawnshop.dao.SuperDAO;
import lk.ijse.pawnshop.dto.PaymentDetailsDto;

import java.sql.SQLException;
import java.util.List;

public interface PaymentDetailsDAO extends SuperDAO {

    String generateNextPaymentId();
    boolean savePaymentDetails(PaymentDetailsDto paymentDetailsDto) throws SQLException;
    boolean issueLoan(PaymentDetailsDto paymentDetailsDto) throws SQLException;
    List<String> getNonPaidInstallmentIds(String paymentId) throws SQLException;
    double getMonthlyInstallmentAmount(String installmentId) throws SQLException;
    boolean updatePaymentStatus(String paymentId, String installmentId) throws SQLException;
}
