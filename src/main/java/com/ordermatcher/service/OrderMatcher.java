package com.ordermatcher.service;

import com.ordermatcher.domain.Order;
import com.ordermatcher.domain.OrderBook;

public interface OrderMatcher {
    void trade(Order order);

    void displayOrders();

    OrderBook getOrderBook();
}
