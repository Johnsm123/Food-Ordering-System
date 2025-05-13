package com.examly.service;

import com.examly.entity.MenuItem;
import com.examly.exception.RestaurantNotFoundException;
import com.examly.util.DBConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class MenuServiceImpl implements MenuService{
    @Override
    public boolean createMenuItem(MenuItem menuItem) throws RestaurantNotFoundException {
        String checkSql = "SELECT COUNT(*) FROM restaurant WHERE restaurantId = ?";
        String insertSql = "INSERT INTO menuItem (restaurantId, name, price, description, availableQuantity) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnectionUtil.getConnection()) {
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, menuItem.getRestaurantId());
            ResultSet rs = checkStmt.executeQuery();
            if(rs.next() && rs.getInt(1) > 0) {
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setInt(1, menuItem.getRestaurantId());
                insertStmt.setString(2, menuItem.getName());
                insertStmt.setDouble(3, menuItem.getPrice());
                insertStmt.setString(4, menuItem.getDescription());
                insertStmt.setInt(5, menuItem.getAvailableQuantity());
                int rowsAffected = insertStmt.executeUpdate();
                System.out.println("Menu item insert rows affected: " + rowsAffected);
                return rowsAffected > 0;
             }

             throw new RestaurantNotFoundException("Restaurant not found: " + menuItem.getRestaurantId());
           
        }catch(SQLException e) {
            System.err.println("SQL Error in createMenuItem: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public List<MenuItem> getMenuItemsByRestaurant(int restaurantId) {
        List<MenuItem> menuItems = new ArrayList<>();
        String sql = "SELECT * FROM menuItem WHERE restaurantId = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             stmt.setInt(1, restaurantId);
             ResultSet rs = stmt.executeQuery();
             while (rs.next()){
                menuItems.add ( new MenuItem(
                    rs.getInt("itemId"),
                    rs.getInt("restaurantId"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getInt("availableQuantity")
                ));
                
             }

            }catch (SQLException e){
                e.printStackTrace();

            }
            return menuItems;
    }
    
   
}
