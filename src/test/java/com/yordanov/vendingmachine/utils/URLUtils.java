package com.yordanov.vendingmachine.utils;

public class URLUtils {
    public static String getBaseUrl(int port) {
        return  "http://localhost:" + port + "/api/v1";
    }
}
