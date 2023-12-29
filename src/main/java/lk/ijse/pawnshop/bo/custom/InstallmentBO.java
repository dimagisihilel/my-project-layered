package lk.ijse.pawnshop.bo.custom;

import lk.ijse.pawnshop.bo.SuperBO;
import lk.ijse.pawnshop.dto.InstallmentDto;

import java.sql.SQLException;
import java.util.List;

public interface InstallmentBO extends SuperBO {
    boolean saveInstallmentDetails(InstallmentDto installmentDto) throws SQLException;
    boolean save(List<InstallmentDto> installmentDtoList) throws SQLException;
}
