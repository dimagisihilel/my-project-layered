package lk.ijse.pawnshop.bo.custom.impl;

import lk.ijse.pawnshop.bo.custom.PaymentDetailsBO;
import lk.ijse.pawnshop.dao.DAOFactory;
import lk.ijse.pawnshop.dao.custom.PaymentDetailsDAO;
import lk.ijse.pawnshop.dto.PaymentDetailsDto;

import java.sql.SQLException;
import java.util.List;

public class PaymentDetailsBOImpl implements PaymentDetailsBO {
    PaymentDetailsDAO paymentDetailsDAO = (PaymentDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENTDETAILS);
    @Override
    public String generateNextPaymentId() {
        return paymentDetailsDAO.generateNextPaymentId();
    }

    @Override
    public boolean savePaymentDetails(PaymentDetailsDto paymentDetailsDto) throws SQLException {
        return paymentDetailsDto != null && paymentDetailsDAO.savePaymentDetails(paymentDetailsDto);
    }

    @Override
    public boolean issueLoan(PaymentDetailsDto paymentDetailsDto) throws SQLException {
        return paymentDetailsDto != null && paymentDetailsDAO.issueLoan(paymentDetailsDto);
    }

    @Override
    public List<String> getNonPaidInstallmentIds(String paymentId) throws SQLException {
        return paymentDetailsDAO.getNonPaidInstallmentIds(paymentId);
    }

    @Override
    public double getMonthlyInstallmentAmount(String installmentId) throws SQLException {
        return paymentDetailsDAO.getMonthlyInstallmentAmount(installmentId);
    }

    @Override
    public boolean updatePaymentStatus(String paymentId, String installmentId) throws SQLException {
        return paymentDetailsDAO.updatePaymentStatus(paymentId, installmentId);
    }
}
