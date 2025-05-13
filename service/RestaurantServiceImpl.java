package com.examly.service;

import com.examly.entity.Restaurant;
import com.examly.util.DBConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class RestaurantServiceImpl implements RestaurantService{
    @Override
    public boolean createRestaurant(Restaurant restaurant){
        String sql = "INSERT INTO restaurant (name, address, cuisineType, contactNumber) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnectionUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, restaurant.getName());
            stmt.setString(2, restaurant.getAddress());
            stmt.setString(3, restaurant.getCuisineType());
            stmt.setString(4, restaurant.getContactNumber());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Restaurant insert rows affected: " + rowsAffected);
            return rowsAffected > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<Restaurant>  getAllRestaurants() {
       List<Restaurant> restaurants = new ArrayList<>();
       String sql = "SELECT * FROM restaurant";
       try (Connection conn = DBConnectionUtil.getConnection();
          Statement stmt = conn.createStatement();
          ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()) {
                Restaurant restaurant = new Restaurant (
                    rs.getInt("restaurantId"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("cuisineType"),
                    rs.getString("contactNumber")
                );
                restaurants.add(restaurant);
            }
          }catch(SQLException e){
            e.printStackTrace();
          }
          return restaurants;
    }

  
}
