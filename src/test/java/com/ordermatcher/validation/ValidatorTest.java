package com.ordermatcher.validation;
import com.ordermatcher.exception.ValidationException;
import org.junit.Test;

public class ValidatorTest {

    Validator validator = new Validator();

    @Test
    public void validSellOrder() throws Exception {
        String input = "SELL 100@100";
        validator.validate(input);
        assert (true);
    }

    @Test
    public void validBuyOrder() throws Exception {
        String input = "BUY 100@100";
        validator.validate(input);
        assert (true);
    }

    @Test
    public void validPrint() throws Exception {
        String input = "PRINT";
        validator.validate(input);
        assert (true);
    }

    @Test (expected = ValidationException.class)
    public void invalidCommand() throws Exception {
        String input = "TEST 100@100";
        validator.validate(input);
    }

    @Test (expected = ValidationException.class)
    public void invalidValueForVolume() throws Exception {
        String input = "SELL text@100";
        validator.validate(input);
    }

    @Test (expected = ValidationException.class)
    public void negativeValueForVolume() throws Exception {
        String input = "SELL -50@100";
        validator.validate(input);
    }

    @Test (expected = ValidationException.class)
    public void invalidValueForPrice() throws Exception {
        String input = "BUY 50@text";
        validator.validate(input);
    }

    @Test (expected = ValidationException.class)
    public void negativeValueForPrice() throws Exception {
        String input = "BUY 50@-200";
        validator.validate(input);
    }
}
