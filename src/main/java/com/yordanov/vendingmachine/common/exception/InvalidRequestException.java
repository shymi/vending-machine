package com.yordanov.vendingmachine.common.exception;

public class InvalidRequestException extends Exception {
    public InvalidRequestException() {
        super("Invalid request");
    }
}
