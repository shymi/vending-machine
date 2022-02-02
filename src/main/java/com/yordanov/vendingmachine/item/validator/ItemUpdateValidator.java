package com.yordanov.vendingmachine.item.validator;

import com.yordanov.vendingmachine.item.dto.ItemDTO;

import java.util.Objects;

public class ItemUpdateValidator {
    public static boolean isValid(ItemDTO value) {
        return !((Objects.isNull(value.getName()) || value.getName().isBlank())
                && Objects.isNull(value.getPrice())
                && Objects.isNull(value.getAmount()));
    }

    private ItemUpdateValidator() {}
}