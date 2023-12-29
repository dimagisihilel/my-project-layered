package lk.ijse.pawnshop.bo.custom.impl;

import lk.ijse.pawnshop.bo.custom.InstallmentBO;
import lk.ijse.pawnshop.dao.DAOFactory;
import lk.ijse.pawnshop.dao.custom.InstallmentDAO;
import lk.ijse.pawnshop.dto.InstallmentDto;

import java.sql.SQLException;
import java.util.List;

public class InstallmentBOImpl implements InstallmentBO {
    InstallmentDAO installmentDAO = (InstallmentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.INSTALLMENT);
    @Override
    public boolean saveInstallmentDetails(InstallmentDto installmentDto) throws SQLException {
        return installmentDAO.saveInstallmentDetails(installmentDto);
    }

    @Override
    public boolean save(List<InstallmentDto> installmentDtoList) throws SQLException {
        return installmentDAO.save(installmentDtoList);
    }
}
