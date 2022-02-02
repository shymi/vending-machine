package com.yordanov.vendingmachine.item.controller.impl;

import com.yordanov.vendingmachine.item.controller.IItemController;
import com.yordanov.vendingmachine.item.dto.CreateItemDTO;
import com.yordanov.vendingmachine.item.dto.ItemDTO;
import com.yordanov.vendingmachine.item.dto.UpdateItemDTO;
import com.yordanov.vendingmachine.item.service.impl.ItemService;
import com.yordanov.vendingmachine.item.validator.ItemUpdateValidator;
import com.yordanov.vendingmachine.util.exception.error.ApiError;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class ItemController implements IItemController {
    private final MessageSource messageSource;
    private final ItemService itemService;

    public ItemController(ItemService itemService, MessageSource messageSource) {
        this.itemService = itemService;
        this.messageSource = messageSource;
    }

    @Override
    public ResponseEntity getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @Override
    public ResponseEntity getItem(Long id) {
        ItemDTO item = itemService.getItem(id);

        if (item == null) {
            return new ResponseEntity<>(
                    buildApiError(HttpStatus.NOT_FOUND, "item.missing"),
                    HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(item);
    }

    @Override
    public ResponseEntity addItem(CreateItemDTO item) {
        return ResponseEntity.ok(itemService.createItem(item));
    }

    @Override
    public ResponseEntity updateItem(Long id, UpdateItemDTO item) {
        if (!ItemUpdateValidator.isValid(item)) {
            return new ResponseEntity<>(
                    buildApiError(HttpStatus.BAD_REQUEST, "item.update.invalid"),
                    HttpStatus.BAD_REQUEST);
        }

        ItemDTO updateItem = itemService.updateItem(id, item);
        if (updateItem == null) {
            return new ResponseEntity<>(
                    buildApiError(HttpStatus.NOT_FOUND, "item.missing"),
                    HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(updateItem);
    }

    @Override
    public ResponseEntity deleteItem(Long id) {
        ItemDTO deleteItem = itemService.deleteItem(id);

        if (deleteItem == null) {
            return new ResponseEntity<>(
                    buildApiError(HttpStatus.NOT_FOUND, "item.missing"),
                    HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(deleteItem);
    }

    private ApiError buildApiError(final HttpStatus status, final String messageId) {
        return new ApiError(status, messageSource.getMessage(messageId, null, Locale.getDefault()));
    }
}
