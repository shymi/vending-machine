package com.yordanov.vendingmachine.item.exception;

public class ItemNoAmount extends Exception {
    public ItemNoAmount() {
        super("No amount was set for item");
    }
}
