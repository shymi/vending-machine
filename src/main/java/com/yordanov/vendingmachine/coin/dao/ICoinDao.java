package com.yordanov.vendingmachine.coin.dao;

import com.yordanov.vendingmachine.coin.entity.Balance;
import com.yordanov.vendingmachine.coin.enums.Coin;
import com.yordanov.vendingmachine.item.exception.InsufficientBalanceException;

public interface ICoinDao {
    /**
     * Retrieve current balance
     *
     * @return current balance
     */
    Balance getCurrentBalance();

    /**
     * Add coin to balance
     *
     * @param addCoin coin to be added
     *
     * @return balance after coin has been added
     */
    Balance addCoin(Coin addCoin);

    /**
     * Reset balance
     *
     * @return balance after reset
     */
    Balance resetBalance();

    /**
     * Subtract from balance
     *
     * @param amount amount to be subtracted
     *
     * @return the balance after subtraction
     */
    Balance subtractFromBalance(float amount) throws InsufficientBalanceException;
}
