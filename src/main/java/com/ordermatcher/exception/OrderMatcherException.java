package com.ordermatcher.exception;

public class OrderMatcherException extends Exception {
    private String detailMessage;

    public OrderMatcherException(String messageCode, String detailMessage, Throwable throwable) {
        super(messageCode, throwable);
        this.detailMessage = detailMessage;
    }

    public String getDetailMessage() {
        return detailMessage;
    }
}
