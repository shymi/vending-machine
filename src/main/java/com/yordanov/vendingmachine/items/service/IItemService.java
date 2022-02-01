package com.yordanov.vendingmachine.items.service;

import com.yordanov.vendingmachine.items.dto.CreateItemDTO;
import com.yordanov.vendingmachine.items.dto.ItemDTO;

import java.util.List;

public interface IItemService {
    /**
     * Create a new item in the vending machine
     *
     * @param newItem new item request
     *
     * @return the created item
     */
    ItemDTO createItem(CreateItemDTO newItem);

    /**
     * Get all items in the vending machine
     *
     * @return list of all items in the vending machine or null if there are no items
     */
    List<ItemDTO> getAllItems();

    /**
     * Get information for a single item in the vending machine
     *
     * @param itemId id of the item to be retrieved
     *
     * @return the item if found or null if there is no item with given id
     */
    ItemDTO getItem(Long itemId);

    /**
     * Update a single item in the vending machine
     *
     * @param updateItem item data to be updated
     *
     * @return the updated item if found or null if there was no item with given id
     */
    ItemDTO updateItem(ItemDTO updateItem);

    /**
     * Delete a single item in the vending machine
     *
     * @param itemId item data to be updated
     *
     * @return the updated item if found or null if there was no item with given id
     */
    ItemDTO deleteItem(Long itemId);
}
