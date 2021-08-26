package com.ordermatcher.service;

import com.ordermatcher.domain.Order;
import com.ordermatcher.domain.OrderBook;
import com.ordermatcher.exception.OrderMatcherException;

public interface OrderMatcher {
    void trade(Order order) throws OrderMatcherException;

    void displayOrders();

    OrderBook getOrderBook();
}
