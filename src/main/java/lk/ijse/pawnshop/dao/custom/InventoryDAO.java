package lk.ijse.pawnshop.dao.custom;

import lk.ijse.pawnshop.dao.SuperDAO;
import lk.ijse.pawnshop.dto.InventoryDto;

import java.sql.SQLException;

public interface InventoryDAO extends SuperDAO {
    String generateNextId();
    boolean saveInventory(InventoryDto inventoryDto) throws SQLException;
}
