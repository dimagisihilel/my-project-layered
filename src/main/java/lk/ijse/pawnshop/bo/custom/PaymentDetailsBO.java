package lk.ijse.pawnshop.bo.custom;

import lk.ijse.pawnshop.bo.SuperBO;
import lk.ijse.pawnshop.dto.PaymentDetailsDto;

import java.sql.SQLException;
import java.util.List;

public interface PaymentDetailsBO extends SuperBO {
    String generateNextPaymentId();
    boolean savePaymentDetails(PaymentDetailsDto paymentDetailsDto) throws SQLException;
    boolean issueLoan(PaymentDetailsDto paymentDetailsDto) throws SQLException;
    List<String> getNonPaidInstallmentIds(String paymentId) throws SQLException;
    double getMonthlyInstallmentAmount(String installmentId) throws SQLException;
    boolean updatePaymentStatus(String paymentId, String installmentId) throws SQLException;
}
