package com.yordanov.vendingmachine.item.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yordanov.vendingmachine.item.dao.impl.ItemDAO;
import com.yordanov.vendingmachine.item.dto.CreateItemDTO;
import com.yordanov.vendingmachine.item.dto.ItemDTO;
import com.yordanov.vendingmachine.item.dto.UpdateItemDTO;
import com.yordanov.vendingmachine.item.entity.Item;
import com.yordanov.vendingmachine.item.service.IItemService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService implements IItemService {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final ItemDAO itemDAO;

    public ItemService(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    /** {@inheritDoc} */
    @Override
    public ItemDTO createItem(CreateItemDTO newItem) {
        Item created = itemDAO.createItem(newItem);
        return MAPPER.convertValue(created, ItemDTO.class);
    }

    /** {@inheritDoc} */
    @Override
    public List<ItemDTO> getAllItems() {
        Collection<Item> items = itemDAO.getAllItems();
        return items.stream().map(item -> MAPPER.convertValue(item, ItemDTO.class)).collect(Collectors.toList());
    }

    /** {@inheritDoc} */
    @Override
    public ItemDTO getItem(Long itemId) {
        Item created = itemDAO.getItem(itemId);
        return MAPPER.convertValue(created, ItemDTO.class);
    }

    /** {@inheritDoc} */
    @Override
    public ItemDTO updateItem(Long id, UpdateItemDTO updateRequest) {
        ItemDTO transformedReq = new ItemDTO(id, updateRequest.getName(), updateRequest.getPrice(), updateRequest.getAmount());
        Item updatedItem = itemDAO.updateItem(transformedReq);

        if (updatedItem == null) {
            return null;
        }

        return MAPPER.convertValue(updatedItem, ItemDTO.class);
    }

    /** {@inheritDoc} */
    @Override
    public ItemDTO deleteItem(Long itemId) {
        Item deleted = itemDAO.deleteItem(itemId);
        return MAPPER.convertValue(deleted, ItemDTO.class);
    }
}
