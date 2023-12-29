package lk.ijse.pawnshop.bo.custom;

import lk.ijse.pawnshop.bo.SuperBO;
import lk.ijse.pawnshop.dto.InventoryDto;

import java.sql.SQLException;

public interface InventoryBO extends SuperBO {
    String generateNextId();
    boolean saveInventory(InventoryDto inventoryDto) throws SQLException;
}
