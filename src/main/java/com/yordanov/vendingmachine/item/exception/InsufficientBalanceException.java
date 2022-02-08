package com.yordanov.vendingmachine.item.exception;

public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException() {
        super("Balance is insufficient");
    }
}
