package com.yordanov.vendingmachine.item.controller.impl;

import com.yordanov.vendingmachine.coin.dto.BalanceDTO;
import com.yordanov.vendingmachine.common.error.ApiError;
import com.yordanov.vendingmachine.common.exception.InvalidRequestException;
import com.yordanov.vendingmachine.item.controller.IItemController;
import com.yordanov.vendingmachine.item.dto.CreateItemDTO;
import com.yordanov.vendingmachine.item.dto.ItemDTO;
import com.yordanov.vendingmachine.item.dto.UpdateItemDTO;
import com.yordanov.vendingmachine.item.exception.InsufficientBalanceException;
import com.yordanov.vendingmachine.item.exception.ItemMissingException;
import com.yordanov.vendingmachine.item.exception.ItemNoAmount;
import com.yordanov.vendingmachine.item.service.IItemService;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class ItemController implements IItemController {
    private final MessageSource messageSource;
    private final IItemService itemService;

    public ItemController(IItemService itemService, MessageSource messageSource) {
        this.itemService = itemService;
        this.messageSource = messageSource;
    }

    @Override
    public ResponseEntity getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @Override
    public ResponseEntity getItem(Long id) {
        try {
            ItemDTO item = itemService.getItem(id);

            return ResponseEntity.ok(item);
        } catch (ItemMissingException e) {
            return buildApiError(HttpStatus.NOT_FOUND, "item.missing");
        }
    }

    @Override
    public ResponseEntity addItem(CreateItemDTO item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.createItem(item));
    }

    @Override
    public ResponseEntity updateItem(Long id, UpdateItemDTO item) {
        try {
            ItemDTO updateItem = itemService.updateItem(id, item);

            return ResponseEntity.ok(updateItem);
        } catch (ItemMissingException e) {
            return buildApiError(HttpStatus.NOT_FOUND, "item.missing");
        } catch (InvalidRequestException e) {
            return buildApiError(HttpStatus.BAD_REQUEST, "item.update.invalid");
        }
    }

    @Override
    public ResponseEntity deleteItem(Long id) {
        try {
            ItemDTO deleteItem = itemService.deleteItem(id);
            return ResponseEntity.ok(deleteItem);
        } catch (ItemMissingException e) {
            return buildApiError(HttpStatus.NOT_FOUND, "item.missing");
        }
    }

    @Override
    public ResponseEntity purchaseItem(Long id) {
        try {
            BalanceDTO balance = itemService.purchase(id);
            return ResponseEntity.ok(balance);
        } catch (InsufficientBalanceException e) {
            return buildApiError(HttpStatus.BAD_REQUEST, "balance.insufficient");
        } catch (ItemNoAmount e) {
            return buildApiError(HttpStatus.BAD_REQUEST, "item.no.amount");
        } catch (ItemMissingException e) {
            return buildApiError(HttpStatus.NOT_FOUND, "item.missing");
        }
    }

    private ResponseEntity buildApiError(final HttpStatus status, final String messageId) {
        return new ResponseEntity<>(
                new ApiError(status, messageSource.getMessage(messageId, null, Locale.getDefault())),
                status
        );
    }
}
