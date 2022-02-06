package com.yordanov.vendingmachine.coin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yordanov.vendingmachine.coin.dao.ICoinDao;
import com.yordanov.vendingmachine.coin.dto.AddCoinDTO;
import com.yordanov.vendingmachine.coin.dto.BalanceDTO;
import com.yordanov.vendingmachine.coin.entity.Balance;
import com.yordanov.vendingmachine.coin.enums.Coin;
import com.yordanov.vendingmachine.coin.service.ICoinService;
import com.yordanov.vendingmachine.common.exception.InvalidRequestException;
import org.springframework.stereotype.Service;

@Service
public class CoinService implements ICoinService {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final ICoinDao iCoinDao;

    public CoinService(ICoinDao iCoinDao) {
        this.iCoinDao = iCoinDao;
    }

    @Override
    public BalanceDTO getCurrentBalance() {
        Balance coin = iCoinDao.getCurrentBalance();
        return MAPPER.convertValue(coin, BalanceDTO.class);
    }

    @Override
    public BalanceDTO addCoin(AddCoinDTO addCoin) throws InvalidRequestException {
        Coin coin = Coin.getCoin(addCoin.getCoin());

        if (coin == null) {
            throw new InvalidRequestException();
        }

        return MAPPER.convertValue(iCoinDao.addCoin(coin), BalanceDTO.class);
    }

    @Override
    public BalanceDTO resetBalance() {
        Balance coin = iCoinDao.resetBalance();
        return MAPPER.convertValue(coin, BalanceDTO.class);
    }
}
