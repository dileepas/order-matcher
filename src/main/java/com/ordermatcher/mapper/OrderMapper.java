package com.ordermatcher.mapper;

import com.ordermatcher.domain.Order;
import java.sql.Timestamp;
import java.util.Date;

public class OrderMapper {
    public static Order getOrderFromUserInput(String input){
        String[] splitStr = input.split("\\s+");
        if ((splitStr[0].equals("SELL") || splitStr[0].equals("BUY"))) {
            String volume = input.split("\\s+")[1].split("@")[0];
            String price = input.split("\\s+")[1].split("@")[1];
            return new Order(splitStr[0], Integer.parseInt(volume),Integer.parseInt(price),getTime());
        } else if (splitStr[0].equals("PRINT")) {
            return new Order(splitStr[0], 0,0,getTime());
        }
        return null;
    }

    private static Timestamp getTime() {
        return new Timestamp(new Date().getTime());
    }
}
