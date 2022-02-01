package com.yordanov.vendingmachine.item.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {
    private Long id;
    private String name;
    private Float price;
    private Integer amount;
}
