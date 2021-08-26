package com.ordermatcher.service.impl;

import com.ordermatcher.domain.Order;
import com.ordermatcher.domain.OrderBook;
import com.ordermatcher.exception.OrderMatcherException;
import com.ordermatcher.service.OrderMatcher;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrderMatcherImpl implements OrderMatcher {

    private final OrderBook orderBook;

    public OrderMatcherImpl() {
        orderBook = new OrderBook();
    }

    @Override
    public void trade(Order order) throws OrderMatcherException {
        try {
            if (order.getType().equals("BUY")) {
                if (!match(order, null)) {
                    orderBook.addBuyOrder(order);
                }
            }
            if (order.getType().equals("SELL")) {
                List<Order> buyOrderList = orderBook.getBuyOrder();
                int i = 0;
                while (i <= buyOrderList.size() - 1) {
                    if (match(buyOrderList.get(i), order)) {
                        orderBook.getBuyOrder().remove(i);
                    } else i++;
                }
                if (order.getVolume() > 0) orderBook.addSellOrder(order);
            }
            if (order.getType().equals("PRINT")) {
                displayOrders();
            }
        } catch (Exception e) {
            throw new OrderMatcherException("PROCESSING_ERROR", "Unable to process the trade with given values", e);
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
        List<Order> buyOrderCopy = new ArrayList<>(orderBook.getBuyOrder());
        buyOrderCopy.sort(Comparator.comparing(Order::getPrice).reversed().thenComparing(Order::getTime));
        buyOrderCopy.forEach(System.out::println);
    }

    private boolean match(Order buyOrder, Order activeSell) {
        int availableTotalVolume = orderBook.getSellOrder().stream().
                filter(i -> buyOrder.getPrice() >= i.getPrice()).mapToInt(Order::getVolume).sum();

        availableTotalVolume = (activeSell != null && buyOrder.getPrice() >= activeSell.getPrice())
                ? availableTotalVolume + activeSell.getVolume()
                : availableTotalVolume;

        if (availableTotalVolume >= buyOrder.getVolume()) {
            boolean isTradeCompleted = checkBuyOrderWithActiveSell(buyOrder, activeSell);
            if (isTradeCompleted) {
                return true;
            } else {
                return checkBuyOrderWithAvailableSellOrders(buyOrder);
            }
        }
        return false;
    }

    private Boolean checkBuyOrderWithActiveSell(Order buyOrder, Order activeSell) {
        if (activeSell != null && activeSell.getPrice() <= buyOrder.getPrice()) {
            if (activeSell.getVolume() == buyOrder.getVolume()) {
                printTrade(activeSell.getVolume(), activeSell.getPrice());
                activeSell.setVolume(0);
                return true;
            } else if (buyOrder.getVolume() > activeSell.getVolume()) {
                printTrade(activeSell.getVolume(), activeSell.getPrice());
                buyOrder.setVolume(buyOrder.getVolume() - activeSell.getVolume());
                activeSell.setVolume(0);
                return false;
            } else {
                printTrade(buyOrder.getVolume(), activeSell.getPrice());
                activeSell.setVolume(activeSell.getVolume() - buyOrder.getVolume());
                return true;
            }
        }
        return false;
    }

    private Boolean checkBuyOrderWithAvailableSellOrders(Order buyOrder) {
        int i = 0;
        List<Order> sellOrderList = orderBook.getSellOrder();
        while (i <= sellOrderList.size() - 1) {
            if (sellOrderList.get(i).getPrice() <= buyOrder.getPrice()) {
                Order current = sellOrderList.get(i);
                if (current.getVolume() == buyOrder.getVolume()) {
                    orderBook.getSellOrder().remove(i);
                    printTrade(current.getVolume(), current.getPrice());
                    return true;
                } else if (buyOrder.getVolume() > current.getVolume()) {
                    orderBook.getSellOrder().remove(i);
                    buyOrder.setVolume(buyOrder.getVolume() - current.getVolume());
                    printTrade(current.getVolume(), current.getPrice());
                    continue;
                } else {
                    current.setVolume(current.getVolume() - buyOrder.getVolume());
                    orderBook.getSellOrder().set(i, current);
                    printTrade(buyOrder.getVolume(), current.getPrice());
                    return true;
                }
            }
            i++;
        }
        return false;
    }

    private void printTrade(int volume, int price) {
        System.out.println("TRADE " + volume + "@" + price);
    }


}

