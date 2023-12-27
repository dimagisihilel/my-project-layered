package lk.ijse.pawnshop.dao.custom;

import lk.ijse.pawnshop.dto.InstallmentDto;

import java.sql.SQLException;
import java.util.List;

public interface InstallmentDAO {
    boolean saveInstallmentDetails(InstallmentDto installmentDto) throws SQLException;
    boolean save(List<InstallmentDto> installmentDtoList) throws SQLException;
}
