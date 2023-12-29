package lk.ijse.pawnshop.bo.custom.impl;

import lk.ijse.pawnshop.bo.custom.InventoryBO;
import lk.ijse.pawnshop.dao.DAOFactory;
import lk.ijse.pawnshop.dao.custom.InventoryDAO;
import lk.ijse.pawnshop.dto.InventoryDto;

import java.sql.SQLException;

public class InventoryBOImpl implements InventoryBO {
    InventoryDAO inventoryDAO = (InventoryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.INVENTORY);
    @Override
    public String generateNextId() {
        return inventoryDAO.generateNextId();
    }

    @Override
    public boolean saveInventory(InventoryDto inventoryDto) throws SQLException {
        return inventoryDAO.saveInventory(inventoryDto);
    }
}
