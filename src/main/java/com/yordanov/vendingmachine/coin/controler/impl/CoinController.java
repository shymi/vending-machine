package com.yordanov.vendingmachine.coin.controler.impl;

import com.yordanov.vendingmachine.coin.controler.ICoinController;
import com.yordanov.vendingmachine.coin.dto.AddCoinDTO;
import com.yordanov.vendingmachine.coin.service.ICoinService;
import com.yordanov.vendingmachine.common.error.ApiError;
import com.yordanov.vendingmachine.common.exception.InvalidRequestException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class CoinController implements ICoinController {
    private final MessageSource messageSource;
    private final ICoinService coinService;

    public CoinController(ICoinService coinService, MessageSource messageSource) {
        this.coinService = coinService;
        this.messageSource = messageSource;
    }

    @Override
    public ResponseEntity getCurrentBalance() {
        return ResponseEntity.ok(coinService.getCurrentBalance());
    }

    @Override
    public ResponseEntity addCoin(AddCoinDTO addCoin) {
        try {
            return ResponseEntity.ok(coinService.addCoin(addCoin));
        } catch (InvalidRequestException e) {
            return buildApiError(HttpStatus.BAD_REQUEST, "coin.invalid");
        }
    }

    @Override
    public ResponseEntity resetBalance() {
        return ResponseEntity.ok(coinService.resetBalance());
    }

    private ResponseEntity buildApiError(final HttpStatus status, final String messageId) {
        return new ResponseEntity<>(
                new ApiError(status, messageSource.getMessage(messageId, null, Locale.getDefault())),
                status
        );
    }
}
