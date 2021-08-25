package com.ordermatcher.validation;

import com.ordermatcher.exception.ValidationException;

public class Validator {
    public void validate(String input) throws ValidationException {
        String[] splitStr = input.split("\\s+");
        if (!splitStr[0].equalsIgnoreCase("PRINT") && splitStr.length != 2 ) {
            throw new ValidationException("INVALID_INPUT","Too many arguments");
        }
        if (!(splitStr[0].equalsIgnoreCase("SELL") || splitStr[0].equalsIgnoreCase("BUY") || splitStr[0].equalsIgnoreCase("PRINT"))) {
            throw new ValidationException("INVALID_INPUT","Invalid command for trade. Should be either SELL,BUY or PRINT");
        } else {
            if ((splitStr[0].startsWith("SELL") || splitStr[0].startsWith("BUY"))) {
                String[] tradeValues = splitStr[1].split("@");
                if (tradeValues.length > 2) {
                    throw new ValidationException("INVALID_INPUT","Need valid input for trading e.g: SELL 100@100");
                } else if (!(isNumeric(tradeValues[0]) && isNumeric(tradeValues[1]))) {
                    throw new ValidationException("INVALID_INPUT","Volume or price should be a positive number");
                }
            }
        }
    }

    private boolean isNumeric(String num) {
        if (num == null) {
            return false;
        }
        try {
            int value = Integer.parseInt(num);
            return value >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}