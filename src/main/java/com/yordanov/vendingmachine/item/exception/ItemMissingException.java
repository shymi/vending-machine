package com.yordanov.vendingmachine.item.exception;

public class ItemMissingException extends Exception {
    public ItemMissingException() {
        super("Item is missing");
    }
}
