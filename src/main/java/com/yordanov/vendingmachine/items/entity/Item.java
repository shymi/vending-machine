package com.yordanov.vendingmachine.items.entity;

import lombok.Data;

@Data
public class Item {
    private Long id;
    private String name;
    private Float price;
}
