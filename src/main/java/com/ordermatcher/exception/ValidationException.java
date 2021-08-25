package com.ordermatcher.exception;

public class ValidationException extends Exception {
    private String detailMessage;

    public ValidationException(String messageCode, String detailMessage) {
        super(messageCode);
        this.detailMessage = detailMessage;
    }

}
