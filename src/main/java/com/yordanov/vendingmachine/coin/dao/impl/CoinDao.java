package com.yordanov.vendingmachine.coin.dao.impl;

import com.yordanov.vendingmachine.coin.dao.ICoinDao;
import com.yordanov.vendingmachine.coin.entity.Balance;
import com.yordanov.vendingmachine.coin.enums.Coin;
import com.yordanov.vendingmachine.item.exception.InsufficientBalanceException;
import org.springframework.stereotype.Repository;

@Repository
public class CoinDao implements ICoinDao {
    private static final Balance BALANCE = new Balance(0);

    @Override
    public Balance getCurrentBalance() {
        return BALANCE;
    }

    @Override
    public Balance addCoin(Coin addCoin) {
        BALANCE.setBalance(BALANCE.getBalance() + addCoin.getValue());
        return BALANCE;
    }

    @Override
    public Balance resetBalance() {
        BALANCE.setBalance((float)0);
        return BALANCE;
    }

    @Override
    public Balance subtractFromBalance(float amount) throws InsufficientBalanceException {
        if (BALANCE.getBalance() < amount) {
            throw new InsufficientBalanceException();
        }

        return BALANCE.subtract(amount);
    }
}
