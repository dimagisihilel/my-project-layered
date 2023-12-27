package lk.ijse.pawnshop.dao.custom;

import lk.ijse.pawnshop.dto.InventoryDto;

import java.sql.SQLException;

public interface InventoryDAO {
    String generateNextId();
    boolean saveInventory(InventoryDto inventoryDto) throws SQLException;
}
