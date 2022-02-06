package com.yordanov.vendingmachine.item.dao;

import com.yordanov.vendingmachine.item.dto.CreateItemDTO;
import com.yordanov.vendingmachine.item.dto.ItemDTO;
import com.yordanov.vendingmachine.item.entity.Item;

import java.util.Collection;

public interface IItemDAO {
    /**
     * Create a new item in the vending machine
     *
     * @param newItem new item request
     *
     * @return the created item
     */
    Item createItem(CreateItemDTO newItem);

    /**
     * Get all items in the vending machine
     *
     * @return list of all items in the vending machine or empty list if there are no items
     */
    Collection<Item> getAllItems();

    /**
     * Get information for a single item in the vending machine
     *
     * @param itemId id of the item to be retrieved
     *
     * @return the item if found or null if there is no item with given id
     */
    Item getItem(Long itemId);

    /**
     * Update a single item in the vending machine
     *
     * @param updateItem item data to be updated
     *
     * @return the updated item if found or null if there was no item with given id
     */
    Item updateItem(ItemDTO updateItem);

    /**
     * Delete a single item in the vending machine
     *
     * @param itemId item data to be updated
     *
     * @return the deleted item if found or null if there was no item with given id
     */
    Item deleteItem(Long itemId);

    /**
     * Decrease item amount with 1
     *
     * @param itemId item data to be updated
     */
    void decrementAmount(Long itemId);
}
