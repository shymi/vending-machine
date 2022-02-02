package com.yordanov.vendingmachine.item.controller.impl;

import com.yordanov.vendingmachine.item.controller.IItemController;
import com.yordanov.vendingmachine.item.dto.CreateItemDTO;
import com.yordanov.vendingmachine.item.dto.ItemDTO;
import com.yordanov.vendingmachine.item.service.impl.ItemService;
import com.yordanov.vendingmachine.item.validator.ItemUpdateValidator;
import com.yordanov.vendingmachine.util.exception.error.ApiError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
        return null;
    }

    @Override
    public ResponseEntity getItem(Long id) {
        return null;
    }

    @Override
    public ResponseEntity addItem(CreateItemDTO item) {
        return null;
    }

    @Override
    public ResponseEntity updateItem(ItemDTO item) {
        if (!ItemUpdateValidator.isValid(item)) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, messageSource.getMessage("item.update.invalid", null, Locale.getDefault())), HttpStatus.BAD_REQUEST);
        }

        ItemDTO updateItem = itemService.updateItem(item);
        return ResponseEntity.ok(new HttpEntity<>(updateItem));
    }

    @Override
    public void deleteItem(Long id) {

    }
}
