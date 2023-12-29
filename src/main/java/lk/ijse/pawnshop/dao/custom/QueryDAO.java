package lk.ijse.pawnshop.dao.custom;

import lk.ijse.pawnshop.dao.SuperDAO;
import lk.ijse.pawnshop.dto.InstallmentDto;
import lk.ijse.pawnshop.dto.PaymentDetailsDto;

import java.sql.SQLException;
import java.util.List;

public interface QueryDAO extends SuperDAO {

    PaymentDetailsDto searchByPaymentId(String paymentId) throws SQLException;
    List<InstallmentDto> getInstallmentsByPaymentId(String paymentId) throws SQLException;
    double getGrantedLoanAmount(String paymentId);
    double getDueLoanAmount(String paymentId);
    String getCustomerEmail(String paymentId);


}
