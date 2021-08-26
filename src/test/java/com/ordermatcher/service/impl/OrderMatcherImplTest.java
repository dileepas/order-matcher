package com.ordermatcher.service.impl;

import com.ordermatcher.domain.Order;
import com.ordermatcher.exception.OrderMatcherException;
import com.ordermatcher.mapper.OrderMapper;
import com.ordermatcher.service.OrderMatcher;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class OrderMatcherImplTest {

    @Test
    public void unMatchedOrdersShouldKeepInOrderList() throws OrderMatcherException {
        OrderMatcher orderMatcher = new OrderMatcherImpl();
        Order sellOrder1 = OrderMapper.getOrderFromUserInput("SELL 100@88");
        Order sellOrder2 = OrderMapper.getOrderFromUserInput("SELL 500@67");
        Order sellOrder3 = OrderMapper.getOrderFromUserInput("SELL 200@55");
        Order buyOrder1 = OrderMapper.getOrderFromUserInput("BUY 100@44");
        Order buyOrder2 = OrderMapper.getOrderFromUserInput("BUY 300@33");
        orderMatcher.trade(sellOrder1);
        orderMatcher.trade(sellOrder2);
        orderMatcher.trade(sellOrder3);
        orderMatcher.trade(buyOrder1);
        orderMatcher.trade(buyOrder2);
        assertEquals (3,orderMatcher.getOrderBook().getSellOrder().size());
        assertEquals (55,orderMatcher.getOrderBook().getSellOrder().get(0).getPrice());
        assertEquals(2,orderMatcher.getOrderBook().getBuyOrder().size());
    }

    @Test
    public void buyOrderHigherOrEqualThePrice_matchSeveralSells_bestMatch() throws OrderMatcherException{
        OrderMatcher orderMatcher = new OrderMatcherImpl();
        Order sellOrder1 = OrderMapper.getOrderFromUserInput("SELL 100@100");
        Order sellOrder2 = OrderMapper.getOrderFromUserInput("SELL 500@50");
        Order sellOrder3 = OrderMapper.getOrderFromUserInput("SELL 200@80");
        Order buyOrder1 = OrderMapper.getOrderFromUserInput("BUY 100@100");
        orderMatcher.trade(sellOrder1);
        orderMatcher.trade(sellOrder2);
        orderMatcher.trade(sellOrder3);
        orderMatcher.trade(buyOrder1);
        assertEquals(0,orderMatcher.getOrderBook().getBuyOrder().size());
        assertEquals(3,orderMatcher.getOrderBook().getSellOrder().size());
        assertEquals(400,orderMatcher.getOrderBook().getSellOrder().get(0).getVolume());
    }

    @Test
    public void buyOrderHigherOrEqualThePrice_volumeDoNotMatch() throws OrderMatcherException{
        OrderMatcher orderMatcher = new OrderMatcherImpl();
        Order sellOrder1 = OrderMapper.getOrderFromUserInput("SELL 100@200");
        Order sellOrder2 = OrderMapper.getOrderFromUserInput("SELL 500@200");
        Order sellOrder3 = OrderMapper.getOrderFromUserInput("SELL 200@80");
        Order buyOrder1 = OrderMapper.getOrderFromUserInput("BUY 300@100");
        orderMatcher.trade(sellOrder1);
        orderMatcher.trade(sellOrder2);
        orderMatcher.trade(sellOrder3);
        orderMatcher.trade(buyOrder1);
        assertEquals(1,orderMatcher.getOrderBook().getBuyOrder().size());
        assertEquals(3,orderMatcher.getOrderBook().getSellOrder().size());
    }

    @Test
    public void buyOrderHigherOrEqualThePrice_matchSeveralSellOrdersForGivenVolume() throws OrderMatcherException{
        OrderMatcher orderMatcher = new OrderMatcherImpl();
        Order sellOrder1 = OrderMapper.getOrderFromUserInput("SELL 100@200");
        Order sellOrder2 = OrderMapper.getOrderFromUserInput("SELL 500@100");
        Order sellOrder3 = OrderMapper.getOrderFromUserInput("SELL 200@80");
        Order buyOrder1 = OrderMapper.getOrderFromUserInput("BUY 300@100");
        orderMatcher.trade(sellOrder1);
        orderMatcher.trade(sellOrder2);
        orderMatcher.trade(sellOrder3);
        orderMatcher.trade(buyOrder1);
        assertEquals(0,orderMatcher.getOrderBook().getBuyOrder().size());
        assertEquals(2,orderMatcher.getOrderBook().getSellOrder().size());
        Optional<Order> val = orderMatcher.getOrderBook().getSellOrder().stream().filter(i -> i.getPrice() == 80).findFirst();
        assert (!val.isPresent());
        Optional<Order> val2 = orderMatcher.getOrderBook().getSellOrder().stream().filter(i -> i.getPrice() == 100).findFirst();
        assertEquals(400, val2.get().getVolume());
    }

    @Test
    public void firstComeFirstServerForSellingOrders() throws OrderMatcherException{
        OrderMatcher orderMatcher = new OrderMatcherImpl();
        Order sellOrder1 = OrderMapper.getOrderFromUserInput("SELL 100@200");
        Order sellOrder2 = OrderMapper.getOrderFromUserInput("SELL 500@100");
        Order sellOrder3 = OrderMapper.getOrderFromUserInput("SELL 200@100");
        Order buyOrder1 = OrderMapper.getOrderFromUserInput("BUY 300@150");
        orderMatcher.trade(sellOrder1);
        orderMatcher.trade(sellOrder2);
        orderMatcher.trade(sellOrder3);
        orderMatcher.trade(buyOrder1);
        assertEquals(0,orderMatcher.getOrderBook().getBuyOrder().size());
        assertEquals(3,orderMatcher.getOrderBook().getSellOrder().size());
        long size = orderMatcher.getOrderBook().getSellOrder().stream().filter(i -> i.getPrice() == 100).count();
        assertEquals(2,size);
        Optional<Order> val2 = orderMatcher.getOrderBook().getSellOrder().stream().filter(i -> i.getPrice() == 100).findFirst();
        assertEquals(200, val2.get().getVolume());
    }

    @Test
    public void firstComeFirstServerForBuyingOrders() throws OrderMatcherException{
        OrderMatcher orderMatcher = new OrderMatcherImpl();
        Order buyOrder1 = OrderMapper.getOrderFromUserInput("BUY 100@100");
        Order buyOrder2 = OrderMapper.getOrderFromUserInput("BUY 500@100");
        Order buyOrder3 = OrderMapper.getOrderFromUserInput("BUY 200@100");
        Order sellOrder1 = OrderMapper.getOrderFromUserInput("SELL 700@90");
        orderMatcher.trade(buyOrder1);
        orderMatcher.trade(buyOrder2);
        orderMatcher.trade(buyOrder3);
        orderMatcher.trade(sellOrder1);
        assertEquals(1,orderMatcher.getOrderBook().getBuyOrder().size());
        assertEquals(1,orderMatcher.getOrderBook().getSellOrder().size());
        Optional<Order> val = orderMatcher.getOrderBook().getBuyOrder().stream().filter(i -> i.getPrice() == 100).findFirst();
        assertEquals(200, val.get().getVolume());
        Optional<Order> val2 = orderMatcher.getOrderBook().getSellOrder().stream().filter(i -> i.getPrice() == 90).findFirst();
        assertEquals(100, val2.get().getVolume());
    }

    @Test
    public void priorityForActiveSell() throws OrderMatcherException{
        OrderMatcher orderMatcher = new OrderMatcherImpl();
        Order sellOrder1 = OrderMapper.getOrderFromUserInput("SELL 50@100");
        Order sellOrder2 = OrderMapper.getOrderFromUserInput("SELL 100@200");
        Order buyOrder1 = OrderMapper.getOrderFromUserInput("BUY 200@500");
        Order sellOrder3 = OrderMapper.getOrderFromUserInput("SELL 75@400");
        orderMatcher.trade(sellOrder1);
        orderMatcher.trade(sellOrder2);
        orderMatcher.trade(buyOrder1);
        orderMatcher.trade(sellOrder3);
        System.out.println(orderMatcher.getOrderBook().getBuyOrder());
        System.out.println(orderMatcher.getOrderBook().getSellOrder());
        assertEquals(0,orderMatcher.getOrderBook().getBuyOrder().size());
        assertEquals(1,orderMatcher.getOrderBook().getSellOrder().size());
        Optional<Order> val = orderMatcher.getOrderBook().getSellOrder().stream().findFirst();
        assertEquals(25, val.get().getVolume());
    }
}
