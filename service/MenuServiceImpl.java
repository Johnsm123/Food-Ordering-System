package com.examly.service;

import com.examly.entity.MenuItem;
import com.examly.exception.RestaurantNotFoundException;
import com.examly.util.DbConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuServiceImpl implements MenuService {
    @Override
    public boolean createMenuItem(MenuItem menuItem) throws RestaurantNotFoundException {
        String checkSql = "SELECT COUNT(*) FROM restaurant WHERE restaurantId = ?";
        String insertSql = "INSERT INTO menu_item (itemId, restaurantId, name, price, description, availableQuantity) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnectionUtil.getConnection()) {
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, menuItem.getRestaurantId());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new RestaurantNotFoundException("Restaurant with ID " + menuItem.getRestaurantId() + " not found");
            }

            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setInt(1, menuItem.getItemId());
            insertStmt.setInt(2, menuItem.getRestaurantId());
            insertStmt.setString(3, menuItem.getName());
            insertStmt.setDouble(4, menuItem.getPrice());
            insertStmt.setString(5, menuItem.getDescription());
            insertStmt.setInt(6, menuItem.getAvailableQuantity());
            return insertStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<MenuItem> getMenuItemsByRestaurant(int restaurantId) {
        List<MenuItem> menuItems = new ArrayList<>();
        String sql = "SELECT * FROM menu_item WHERE restaurantId = ?";
        try (Connection conn = DbConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, restaurantId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MenuItem item = new MenuItem(
                    rs.getInt("itemId"),
                    rs.getInt("restaurantId"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getInt("availableQuantity")
                );
                menuItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuItems;
    }
}
