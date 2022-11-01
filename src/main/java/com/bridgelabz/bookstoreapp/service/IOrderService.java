package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.OrderDTO;
import com.bridgelabz.bookstoreapp.model.Order;

import java.util.List;

public interface IOrderService {
    String placeOrder(String token,int cartId, OrderDTO orderDTO);

    List<Order> getAllOrdersByUserId(String token);

    String cancelOrder(String token, int orderId);

    List<Order> getAllOrders();
}
