package com.examly.service;

import com.examly.entity.Order;
import com.examly.entity.OrderItem;
import java.util.List;

public interface OrderService {
    boolean createOrder(Order order, List<OrderItem> orderedItems);
    List<Order> getOrdersByCustomer(int customerId);
}
