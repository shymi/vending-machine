package com.yordanov.vendingmachine.item.dao.impl;

import com.yordanov.vendingmachine.item.dao.IItemDAO;
import com.yordanov.vendingmachine.item.dto.CreateItemDTO;
import com.yordanov.vendingmachine.item.dto.ItemDTO;
import com.yordanov.vendingmachine.item.entity.Item;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ItemDAO implements IItemDAO {
    /**
     * Using Long as an ID, because of force of habit(had too many problems with int ids in production)
     * Integer values for current requirements should be plenty enough
    **/
    private static final Map<Long, Item> REPOSITORY = new HashMap<>();
    private static Long CURRENT_INDEX = (long)1;

    // initializing with some data
    static {
        Item item = new Item(CURRENT_INDEX, "Coca Cola", (float)1.20, 10);
        REPOSITORY.put(item.getId(), item);

        CURRENT_INDEX++;

        item = new Item(CURRENT_INDEX, "Sprite", (float)1.10, 9);
        REPOSITORY.put(item.getId(), item);

        CURRENT_INDEX++;

        item = new Item(CURRENT_INDEX, "San Benedetto", (float)2.20, 8);
        REPOSITORY.put(item.getId(), item);

        CURRENT_INDEX++;
    }

    /** {@inheritDoc} */
    @Override
    public Item createItem(CreateItemDTO newItem) {
        Item item = new Item(CURRENT_INDEX, newItem.getName(), newItem.getPrice(), newItem.getAmount());
        CURRENT_INDEX++;

        REPOSITORY.put(item.getId(), item);
        return item;
    }

    /** {@inheritDoc} */
    @Override
    public Collection<Item> getAllItems() {
        return REPOSITORY.values();
    }

    /** {@inheritDoc} */
    @Override
    public Item getItem(Long itemId) {
        return REPOSITORY.get(itemId);
    }

    /** {@inheritDoc} */
    @Override
    public Item updateItem(ItemDTO updateItem) {
        Item fetchedItem = getItem(updateItem.getId());

        if (fetchedItem == null) {
            return null;
        }

        if (updateItem.getPrice() != null) {
            fetchedItem.setPrice(updateItem.getPrice());
        }

        if (updateItem.getName() != null) {
            fetchedItem.setName(updateItem.getName());
        }

        if (updateItem.getAmount() != null) {
            fetchedItem.setAmount(updateItem.getAmount());
        }

        return fetchedItem;
    }

    /** {@inheritDoc} */
    @Override
    public Item deleteItem(Long itemId) {
        return REPOSITORY.remove(itemId);
    }

    @Override
    public void decrementAmount(Long itemId) {
        REPOSITORY.get(itemId).setAmount(REPOSITORY.get(itemId).getAmount() - 1);
    }
}
