package com.shopnow.orderservice.services;

import com.shopnow.orderservice.models.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> getAllOrders();

    Optional<Order> getOrderById(String id);

    Order createOrder(Order order);

    Order updateOrder(String id, Order orderDetails);

    void deleteOrder(String id);

}
