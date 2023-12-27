package lk.ijse.pawnshop.dao.custom.impl;

import lk.ijse.pawnshop.dao.SQLUtil;
import lk.ijse.pawnshop.dao.custom.InstallmentDAO;
import lk.ijse.pawnshop.db.DbConnection;
import lk.ijse.pawnshop.dto.InstallmentDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class InstallmentDAOImpl implements InstallmentDAO {
    @Override
    public boolean saveInstallmentDetails(InstallmentDto installmentDto) throws SQLException {
        /*Connection connection = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO installments VALUES(?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, installmentDto.getInstallmentId());
        ps.setString(2, installmentDto.getPaymentId());
        ps.setDouble(3, installmentDto.getAmount());
        ps.setString(4,installmentDto.getPaymentStatus());
        return ps.executeUpdate() > 0;*/
        return SQLUtil.execute("INSERT INTO installments VALUES(?,?,?,?)",installmentDto.getInstallmentId(),installmentDto.getPaymentId(),installmentDto.getAmount(),installmentDto.getPaymentStatus());

    }
    @Override
    public boolean save(List<InstallmentDto> installmentDtoList) throws SQLException {
        for (InstallmentDto installmentDto : installmentDtoList) {
            if (!saveInstallmentDetails(installmentDto)) {
                return false;
            };
        }
        return true;
    }
}
