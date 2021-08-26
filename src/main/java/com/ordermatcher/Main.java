package com.ordermatcher;

import com.ordermatcher.domain.Order;
import com.ordermatcher.exception.OrderMatcherException;
import com.ordermatcher.exception.ValidationException;
import com.ordermatcher.mapper.OrderMapper;
import com.ordermatcher.service.OrderMatcher;
import com.ordermatcher.service.impl.OrderMatcherImpl;
import com.ordermatcher.validation.Validator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OrderMatcher orderMatcher = new OrderMatcherImpl();
        while (!scanner.hasNext("END")) {
            try {
                String input = scanner.nextLine();
                new Validator().validate(input);
                Order order = OrderMapper.getOrderFromUserInput(input);
                orderMatcher.trade(order);
            } catch (ValidationException e) {
                System.out.println(e.getMessage() + ":" + e.getDetailMessage());
            } catch (OrderMatcherException e) {
                System.out.println(e.getMessage() + ":" + e.getDetailMessage());
            }
        }
    }
}
