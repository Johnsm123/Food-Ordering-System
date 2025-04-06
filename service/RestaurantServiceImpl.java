package com.examly.service;

import com.examly.entity.Restaurant;
import com.examly.util.DbConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantServiceImpl implements RestaurantService {
    @Override
    public boolean createRestaurant(Restaurant restaurant) {
        String sql = "INSERT INTO restaurant (restaurantId, name, address, cuisineType, contactNumber) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DbConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, restaurant.getRestaurantId());
            stmt.setString(2, restaurant.getName());
            stmt.setString(3, restaurant.getAddress());
            stmt.setString(4, restaurant.getCuisineType());
            stmt.setString(5, restaurant.getContactNumber());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurant";
        try (Connection conn = DbConnectionUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Restaurant restaurant = new Restaurant(
                    rs.getInt("restaurantId"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("cuisineType"),
                    rs.getString("contactNumber")
                );
                restaurants.add(restaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurants;
    }
}
