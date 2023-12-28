package lk.ijse.pawnshop.dao.custom;

import lk.ijse.pawnshop.dto.PaymentDetailsDto;

import java.sql.SQLException;

public interface PaymentDetailsDAO {
    boolean savePaymentDetails(PaymentDetailsDto paymentDetailsDto) throws SQLException;
}
