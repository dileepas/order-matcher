package com.ordermatcher.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrderBook {

    private List<Order> sellOrder = new ArrayList<>();
    private List<Order> buyOrder = new ArrayList<>();

    public void addSellOrder(Order order) {
        sellOrder.add(order);
        sellOrder.sort(Comparator.comparing(Order::getPrice).thenComparing(Order::getTime));
    }

    public void addBuyOrder(Order order) {
        buyOrder.add(order);
        buyOrder.sort(Comparator.comparing(Order::getTime));
    }

    public List<Order> getSellOrder() {
        return sellOrder;
    }

    public List<Order> getBuyOrder() {
        return buyOrder;
    }
}