package com.examly.service;

import com.examly.entity.Order;
import com.examly.entity.OrderItem;
import com.examly.util.DBConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class OrderServiceImpl implements OrderService{
    @Override
    public boolean createOrder(Order order, List <OrderItem> orderedItems){
        String orderSql = "INSERT INTO `order` (orderId, customerId, restaurantId, orderStatus, totalPrice, deliveryAddress)VALUES (?, ?, ?, ?, ?,?)";
        String orderItemSql = "INSERT INTO orderItem (orderId, itemId, quantity) VALUES (?, ?, ?)";
        try (Connection conn = DBConnectionUtil.getConnection()){
            conn.setAutoCommit(false);

            PreparedStatement orderStmt = conn.prepareStatement(orderSql,Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, order.getOrderId());
            orderStmt.setInt(2, order.getCustomerId());
            orderStmt.setInt(3, order.getRestaurantId());
            orderStmt.setString(4, order.getOrderStatus());
            orderStmt.setDouble(5, order.getTotalPrice());
            orderStmt.setString(6, order.getDeliveryAddress());
            int orderRows = orderStmt.executeUpdate();

            ResultSet rs = orderStmt.getGeneratedKeys();
            int orderId = 0;
            if(rs.next()){
                orderId = rs.getInt(1);
            }
            
            PreparedStatement itemStmt = conn.prepareStatement(orderItemSql);
            int itemRows = 0;
            for(OrderItem item : orderedItems) {
                itemStmt.setInt(1, item.getOrderId());
                itemStmt.setInt(2, item.getItemId());
                itemStmt.setInt(3, item.getQuantity());
                itemRows += itemStmt.executeUpdate();
                
            }

            System.out.println("Order insert rows affected: "+ orderRows);
            System.out.println("Order item insert rows affected: " + itemRows);
            conn.commit();
            return orderRows > 0 && itemRows == orderedItems.size();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public List<Order> getOrdersByCustomer(int customerId) {
        List<Order> orders = new ArrayList<>();
        String sql = customerId == 0 ? "SELECT * FROM `order`" :"SELECT * FROM `order` WHERE customerId = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
                if(customerId != 0){
                    stmt.setInt(1, customerId);
                }
            
             ResultSet rs = stmt.executeQuery();
             while (rs.next()){
                Order order = new Order(
                    rs.getInt("orderId"),
                    rs.getInt("customerId"),
                    rs.getInt("restaurantId"),
                    rs.getString("orderStatus"),
                    rs.getDouble("totalPrice"),
                    rs.getString("deliveryAddress")
                );
                orders.add(order);
            } 
            }catch(SQLException e){
                e.printStackTrace();
                
            }
              return orders;
    }
    @Override
    public Order getOrderById(int orderId) {
        String sql = "SELECT * from `order` where orderId = ?";
        try(Connection conn = DBConnectionUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                return new Order(
                    rs.getInt("orderId"),
                    rs.getInt("customerId"),
                    rs.getInt("restaurantId"),
                    rs.getString("orderStatus"),
                    rs.getDouble("totalPrice"),
                    rs.getString("deliveryAddress")
                );
            }catch(SQLException e){
                e.printStackTrace();
            }
            return null;
        }
    }
    
}
