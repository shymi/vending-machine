package com.yordanov.vendingmachine.coin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Balance {
    private float balance = 0;

    public Balance subtract(float value) {
        this.balance = this.balance - value;
        return this;
    }
}
