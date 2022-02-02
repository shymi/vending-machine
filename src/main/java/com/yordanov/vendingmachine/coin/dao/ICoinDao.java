package com.yordanov.vendingmachine.coin.dao;

import com.yordanov.vendingmachine.coin.dto.AddCoinDTO;
import com.yordanov.vendingmachine.coin.dto.BalanceDTO;

public interface ICoinDao {
    /**
     * Retrieve current balance
     *
     * @return current balance
     */
    BalanceDTO getCurrentBalance();

    /**
     * Add coin to balance
     *
     * @param addCoin coin to be added
     *
     * @return balance after coin has been added
     */
    BalanceDTO addCoin(AddCoinDTO addCoin);

    /**
     * Reset balance
     *
     * @return balance after reset
     */
    BalanceDTO resetBalance();

    /**
     * Subtract from balance
     *
     * @param amount amount to be subtracted
     *
     * @return the balance after subtraction
     */
    BalanceDTO subtractFromBalance(float amount);
}
