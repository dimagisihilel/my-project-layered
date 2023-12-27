package lk.ijse.pawnshop.dao.custom.impl;

import lk.ijse.pawnshop.dao.SQLUtil;
import lk.ijse.pawnshop.dao.custom.InventoryDAO;
import lk.ijse.pawnshop.db.DbConnection;
import lk.ijse.pawnshop.dto.InventoryDto;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryDAOImpl implements InventoryDAO {
    @Override
    public String generateNextId() {
        String prefix = "INV-";
        String nextId = "";

        try {
           /* Connection connection = DbConnection.getInstance().getConnection();
            String query = "SELECT MAX(CAST(SUBSTRING(inventory_id, 5) AS SIGNED)) + 1 AS nextId FROM inventory";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {*/
                ResultSet resultSet = SQLUtil.execute("SELECT MAX(CAST(SUBSTRING(inventory_id, 5) AS SIGNED)) + 1 AS nextId FROM inventory");
                if (resultSet.next()) {
                    int numericPart = resultSet.getInt("nextId");
                    nextId = prefix + String.format("%05d", numericPart);
                } else {
                    // If the table is empty, start with 1
                    nextId = prefix + "00001";
                }
          //  }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nextId;
    }
    @Override
    public boolean saveInventory(InventoryDto inventoryDto) throws SQLException {
       /* Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO inventory (inventory_id, customer_id, itemType, caratValue, " +
                            "pricePerGram, weight, description, assessedValue) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, inventoryDto.getInventoryId());
            preparedStatement.setString(2, inventoryDto.getCustomerId());
            preparedStatement.setString(3, inventoryDto.getItemType());
            preparedStatement.setString(4, inventoryDto.getCaratValue());
            preparedStatement.setDouble(5, inventoryDto.getPricePerGram());
            preparedStatement.setDouble(6, inventoryDto.getWeight());
            preparedStatement.setString(7, inventoryDto.getDescription());
            preparedStatement.setDouble(8, inventoryDto.getAssessedValue());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeStatement(preparedStatement);
        }*/
        return SQLUtil.execute("INSERT INTO inventory (inventory_id, customer_id, itemType, caratValue, " +
                "pricePerGram, weight, description, assessedValue) VALUES (?,?,?,?,?,?,?,?)",
                inventoryDto.getInventoryId(), inventoryDto.getCustomerId(), inventoryDto.getItemType(),
                inventoryDto.getCaratValue(), inventoryDto.getPricePerGram(), inventoryDto.getWeight(),
                inventoryDto.getDescription(), inventoryDto.getAssessedValue());
    }

   /* private static void closeStatement(PreparedStatement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }*/
}
