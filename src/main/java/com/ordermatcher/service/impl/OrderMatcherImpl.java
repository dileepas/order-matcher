package com.ordermatcher.service.impl;

import com.ordermatcher.domain.Order;
import com.ordermatcher.domain.OrderBook;
import com.ordermatcher.service.OrderMatcher;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrderMatcherImpl implements OrderMatcher {

    private final OrderBook orderBook;

    public OrderMatcherImpl () {
        orderBook = new OrderBook();
    }

    @Override
    public void trade(Order order) {
        if (order.getType().equals("BUY")) {
            if (! match(order)) {
                orderBook.addBuyOrder(order);
            }
        }
        if (order.getType().equals("SELL")) {
            orderBook.addSellOrder(order);
            List<Order> li = orderBook.getBuyOrder();
            int i =0;
            while (i <= li.size() - 1) {
                if (match(li.get(i))) {
                    orderBook.getBuyOrder().remove(i);
                } else i++;
            }
        }
        if (order.getType().equals("PRINT")) {
            displayOrders();
        }
    }

    @Override
    public OrderBook getOrderBook() {
        return orderBook;
    }

    @Override
    public void displayOrders() {
        System.out.println("---------SELL-------------");
        orderBook.getSellOrder().forEach(System.out::println);
        System.out.println("---------BUY--------------");
        List<Order> copy = new ArrayList<>(orderBook.getBuyOrder());
        copy.sort(Comparator.comparing(Order::getPrice).reversed().thenComparing(Order::getTime));
        copy.forEach(System.out::println);
    }

    private boolean match(Order order) {
        int availableTotalVolume = orderBook.getSellOrder().stream().
                filter(i -> order.getPrice() >= i.getPrice()).mapToInt(Order::getVolume).sum();

        int i = 0;
        if (availableTotalVolume >= order.getVolume()) {
            List<Order> sellOrderList = orderBook.getSellOrder();
            while (true) {
                if (sellOrderList.get(i).getPrice() <= order.getPrice()) {
                    Order current = sellOrderList.get(i);
                    if (current.getVolume() == order.getVolume()) {
                        orderBook.getSellOrder().remove(i);
                        printTrade( current.getVolume() , current.getPrice());
                        return true;
                    } else if (order.getVolume() > current.getVolume()) {
                        orderBook.getSellOrder().remove(i);
                        order.setVolume(order.getVolume() - current.getVolume());
                        printTrade(current.getVolume() ,current.getPrice());
                        continue;
                    } else {
                        current.setVolume(current.getVolume() - order.getVolume());
                        orderBook.getSellOrder().set(i, current);
                        printTrade(order.getVolume() , current.getPrice());
                        return true;
                    }
                }
                i++;
            }
        }
        return false;
    }

    private void printTrade(int volume,int price) {
        System.out.println("TRADE " + volume + "@" + price);
    }


}

