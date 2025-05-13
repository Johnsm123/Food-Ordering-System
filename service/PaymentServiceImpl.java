package com.examly.service;

import com.examly.entity.Payment;
import com.examly.util.DBConnectionUtil;
import java.sql.*;
public class PaymentServiceImpl implements PaymentService {
    @Override
    public  boolean processPayment(Payment payment) {
        String sql = "INSERT INTO payment (paymentId, orderId, paymentDate, paymentStatus, amountPaid) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnectionUtil.getConnection();
           PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, payment.getPaymentId());
            stmt.setInt(2, payment.getOrderId());
            stmt.setTimestamp(3, new Timestamp(payment.getPaymentDate().getTime()));
            stmt.setString(4, payment.getPaymentStatus());
            stmt.setDouble(5, payment.getAmountPaid());
            return stmt.executeUpdate() > 0;
           }catch (SQLException e){
              e.printStackTrace();
              return false;
           }
    }

} 
