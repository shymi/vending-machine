package com.yordanov.vendingmachine.item.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yordanov.vendingmachine.coin.dao.ICoinDao;
import com.yordanov.vendingmachine.coin.dto.BalanceDTO;
import com.yordanov.vendingmachine.common.exception.InvalidRequestException;
import com.yordanov.vendingmachine.item.dao.IItemDAO;
import com.yordanov.vendingmachine.item.dto.CreateItemDTO;
import com.yordanov.vendingmachine.item.dto.ItemDTO;
import com.yordanov.vendingmachine.item.dto.UpdateItemDTO;
import com.yordanov.vendingmachine.item.entity.Item;
import com.yordanov.vendingmachine.item.exception.InsufficientBalanceException;
import com.yordanov.vendingmachine.item.exception.ItemMissingException;
import com.yordanov.vendingmachine.item.exception.ItemNoAmount;
import com.yordanov.vendingmachine.item.service.IItemService;
import com.yordanov.vendingmachine.item.validator.ItemUpdateValidator;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService implements IItemService {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final IItemDAO itemDAO;
    private final ICoinDao coinDao;

    public ItemService(IItemDAO itemDAO, ICoinDao coinDao) {
        this.itemDAO = itemDAO;
        this.coinDao = coinDao;
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

        if (items.isEmpty()) {
            return Collections.emptyList();
        }

        return items.stream().map(item -> MAPPER.convertValue(item, ItemDTO.class)).collect(Collectors.toList());
    }

    /** {@inheritDoc} */
    @Override
    public ItemDTO getItem(Long itemId) throws ItemMissingException {
        Item item = itemDAO.getItem(itemId);

        if (item == null) {
            throw new ItemMissingException();
        }

        return MAPPER.convertValue(item, ItemDTO.class);
    }

    /** {@inheritDoc} */
    @Override
    public ItemDTO updateItem(Long id, UpdateItemDTO updateRequest) throws ItemMissingException, InvalidRequestException {
        if (!ItemUpdateValidator.isValid(updateRequest)) {
            throw new InvalidRequestException();
        }

        ItemDTO transformedReq = new ItemDTO(id, updateRequest.getName(), updateRequest.getPrice(), updateRequest.getAmount());
        Item updatedItem = itemDAO.updateItem(transformedReq);

        if (updatedItem == null) {
            throw new ItemMissingException();
        }

        return MAPPER.convertValue(updatedItem, ItemDTO.class);
    }

    /** {@inheritDoc} */
    @Override
    public ItemDTO deleteItem(Long itemId) throws ItemMissingException {
        Item deleted = itemDAO.deleteItem(itemId);

        if (deleted == null) {
            throw new ItemMissingException();
        }

        return MAPPER.convertValue(deleted, ItemDTO.class);
    }

    /** {@inheritDoc} */
    @Override
    public BalanceDTO purchase(Long id) throws ItemMissingException, InsufficientBalanceException, ItemNoAmount {
        Item item = itemDAO.getItem(id);

        if (item == null) {
            throw new ItemMissingException();
        }

        if (item.getAmount() == 0) {
            throw new ItemNoAmount();
        }

        itemDAO.decrementAmount(id);

        return MAPPER.convertValue(coinDao.subtractFromBalance(item.getPrice()), BalanceDTO.class);
    }
}
