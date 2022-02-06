package com.yordanov.vendingmachine.coin.enums;

public enum Coin {
    TEN_ST("10st", (float)0.1),
    TWENTY_ST("20st", (float)0.2),
    FIFTY_ST("50st", (float)0.5),
    ONE_LV("1lv", (float)1),
    TWO_LV("2lv", (float)2);

    private String name;
    private float value;

    Coin(String name, float value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public float getValue() {
        return value;
    }

    public static Coin getCoin(String name) {
        for (Coin coin : values()) {
            if (coin.getName().equals(name)) {
                return coin;
            }
        }

        return null;
    }
}
