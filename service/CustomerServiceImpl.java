package com.examly.service;

import com.examly.entity.Customer;
import com.examly.exception.EmailAlreadyRegisteredException;
import com.examly.util.DbConnectionUtil;
import java.sql.*;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public boolean createCustomer(Customer customer) throws EmailAlreadyRegisteredException {
        String checkEmailSql = "SELECT COUNT(*) FROM customer WHERE email = ?";
        String insertSql = "INSERT INTO customer (customerId, name, email, phoneNumber, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbConnectionUtil.getConnection()) {
            // Check if email exists
            PreparedStatement checkStmt = conn.prepareStatement(checkEmailSql);
            checkStmt.setString(1, customer.getEmail());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new EmailAlreadyRegisteredException("Email " + customer.getEmail() + " is already registered");
            }

            // Insert customer
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setInt(1, customer.getCustomerId());
            insertStmt.setString(2, customer.getName());
            insertStmt.setString(3, customer.getEmail());
            insertStmt.setString(4, customer.getPhoneNumber());
            insertStmt.setString(5, customer.getPassword());
            return insertStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
