package com.yordanov.vendingmachine.item.service.impl;

import com.yordanov.vendingmachine.item.dto.CreateItemDTO;
import com.yordanov.vendingmachine.item.dto.ItemDTO;
import com.yordanov.vendingmachine.item.service.IItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService implements IItemService {
    /** {@inheritDoc} */
    @Override
    public ItemDTO createItem(CreateItemDTO newItem) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public List<ItemDTO> getAllItems() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public ItemDTO getItem(Long itemId) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public ItemDTO updateItem(ItemDTO updateItem) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public ItemDTO deleteItem(Long itemId) {
        return null;
    }
}
