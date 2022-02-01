package com.yordanov.vendingmachine.items.controller.impl;

import com.yordanov.vendingmachine.items.controller.IItemController;
import com.yordanov.vendingmachine.items.dto.CreateItemDTO;
import com.yordanov.vendingmachine.items.dto.ItemDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController implements IItemController {
    @Override
    public List<ItemDTO> getAllItems() {
        return null;
    }

    @Override
    public ItemDTO getItem(Long id) {
        return null;
    }

    @Override
    public ItemDTO addItem(CreateItemDTO item) {
        return null;
    }

    @Override
    public ItemDTO updateItem(ItemDTO item) {
        return null;
    }

    @Override
    public void deleteItem(Long id) {

    }
}
