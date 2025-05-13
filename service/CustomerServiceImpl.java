package com.examly.service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.examly.util.DBConnectionUtil;
import com.examly.entity.Customer;
import com.examly.exception.EmailAlreadyRegisteredException;
public class CustomerServiceImpl implements CustomerService{
    @Override
    public boolean createCustomer(Customer customer) throws EmailAlreadyRegisteredException{
        String checkEmailsql = "SELECT COUNT(*) FROM customer WHERE email = ?";
        String insertSql = "INSERT INTO customer (name, email, phoneNumber, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnectionUtil.getConnection();
            PreparedStatement checkStmt = conn.prepareStatement(checkEmailsql);
            PreparedStatement insertStmt = conn.prepareStatement(insertSql)){
            checkStmt.setString(1, customer.getEmail());
            ResultSet rs = checkStmt.executeQuery();
            if(rs.next() && rs.getInt(1)>0){
               
                throw new EmailAlreadyRegisteredException("Email" + customer.getEmail() + " is already registered");
            }

            insertStmt.setString(1, customer.getName());
            insertStmt.setString(2, customer.getEmail());
            insertStmt.setString(3, customer.getPhoneNumber());
            insertStmt.setString(4, customer.getPassword());
            int rowAffected = insertStmt.executeUpdate();
            System.out.println("Customer insert rows affected: " + rowAffected);
            return rowAffected > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

}
