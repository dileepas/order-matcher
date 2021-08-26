package com.ordermatcher.validation;

import com.ordermatcher.exception.ValidationException;

public class Validator {
    public void validate(String input) throws ValidationException {
        String[] splitStr = input.split("\\s+");
        if (!splitStr[0].equals("PRINT") && splitStr.length != 2) {
            throw new ValidationException("INVALID_INPUT", "Enter a valid command. e.g. SELL 100@10");
        }
        if (!(splitStr[0].equals("SELL") || splitStr[0].equals("BUY") || splitStr[0].equals("PRINT"))) {
            throw new ValidationException("INVALID_INPUT", "Invalid command for trade. Should be either SELL,BUY or PRINT");
        } else {
            if ((splitStr[0].startsWith("SELL") || splitStr[0].startsWith("BUY"))) {
                String[] tradeValues = splitStr[1].split("@");
                if (tradeValues.length > 2) {
                    throw new ValidationException("INVALID_INPUT", "Need valid input for trading e.g: SELL 100@10");
                } else if (!(isNumeric(tradeValues[0]) && isNumeric(tradeValues[1]))) {
                    throw new ValidationException("INVALID_INPUT", "Volume or price should be a positive number");
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