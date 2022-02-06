package com.yordanov.vendingmachine.item.service;

import com.yordanov.vendingmachine.coin.dto.BalanceDTO;
import com.yordanov.vendingmachine.common.exception.InvalidRequestException;
import com.yordanov.vendingmachine.item.dto.CreateItemDTO;
import com.yordanov.vendingmachine.item.dto.ItemDTO;
import com.yordanov.vendingmachine.item.dto.UpdateItemDTO;
import com.yordanov.vendingmachine.item.exception.InsufficientBalanceException;
import com.yordanov.vendingmachine.item.exception.ItemMissingException;
import com.yordanov.vendingmachine.item.exception.ItemNoAmount;

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
     * @return list of all items in the vending machine or empty list if there are no items
     */
    List<ItemDTO> getAllItems();

    /**
     * Get information for a single item in the vending machine
     *
     * @param itemId id of the item to be retrieved
     *
     * @throws ItemMissingException when item with given id is not found
     *
     * @return the item if found or null if there is no item with given id
     */
    ItemDTO getItem(Long itemId) throws ItemMissingException;

    /**
     * Update a single item in the vending machine
     *
     * @param id id of the item to be updated
     * @param updateItem item data to be updated
     *
     * @throws ItemMissingException when item with given id is not found
     * @throws InvalidRequestException when update item data is invalid
     *
     * @return the updated item if found or null if there was no item with given id
     */
    ItemDTO updateItem(Long id, UpdateItemDTO updateItem) throws ItemMissingException, InvalidRequestException;

    /**
     * Delete a single item in the vending machine
     *
     * @param itemId item data to be updated
     *
     * @throws ItemMissingException when item with given id is not found
     *
     * @return the updated item if found or null if there was no item with given id
     */
    ItemDTO deleteItem(Long itemId) throws ItemMissingException;

    /**
     * Purchase an item with the given id
     *
     * @param id item id which will be purchased
     *
     * @return the balance after purchase
     *
     * @throws ItemMissingException when item is missing
     * @throws InsufficientBalanceException when balance is insufficient
     */
    BalanceDTO purchase(Long id) throws ItemMissingException, InsufficientBalanceException, ItemNoAmount;
}
