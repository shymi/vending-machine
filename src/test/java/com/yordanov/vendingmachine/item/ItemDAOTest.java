package com.yordanov.vendingmachine.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yordanov.vendingmachine.item.dao.IItemDAO;
import com.yordanov.vendingmachine.item.dto.CreateItemDTO;
import com.yordanov.vendingmachine.item.dto.ItemDTO;
import com.yordanov.vendingmachine.item.entity.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ItemDAOTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private IItemDAO itemDAO;

    @Test
    public void contextLoads() {
        assertThat(itemDAO).isNotNull();
    }

    @Test
    public void canInsertAndRetrieveItem() {
        int size = itemDAO.getAllItems().size();
        CreateItemDTO createItemReq = new CreateItemDTO("Fanta", (float)1.10, 10);
        Item item = itemDAO.createItem(createItemReq);

        assertThat(itemDAO.getAllItems().size()).isEqualTo(size + 1);
        assertThat(itemDAO.getItem(item.getId())).isEqualTo(item);
    }

    @Test
    public void canInsertAndRemoveItem() {
        int size = itemDAO.getAllItems().size();
        CreateItemDTO createItemReq = new CreateItemDTO("BBB", (float)1.50, 5);
        Item item = itemDAO.createItem(createItemReq);

        assertThat(itemDAO.getAllItems().size()).isEqualTo(size + 1);
        assertThat(itemDAO.deleteItem(item.getId())).isEqualTo(item);
        assertThat(itemDAO.getAllItems().size()).isEqualTo(size);
    }

    @Test
    public void canInsertAndFullyUpdateItem() {
        float newPrice = (float)2.30;
        int newAmount = 8;
        String newName = "Prisun+";

        CreateItemDTO createItemReq = new CreateItemDTO("Prisun", (float)1.70, 5);
        Item item = itemDAO.createItem(createItemReq);

        ItemDTO itemForUpdate = MAPPER.convertValue(item, ItemDTO.class);
        itemForUpdate.setPrice(newPrice);
        itemForUpdate.setAmount(newAmount);
        itemForUpdate.setName(newName);
        itemDAO.updateItem(itemForUpdate);

        assertThat(itemDAO.getItem(item.getId())).isNotNull();
        assertThat(itemDAO.getItem(item.getId()).getPrice()).isEqualTo(newPrice);
        assertThat(itemDAO.getItem(item.getId()).getAmount()).isEqualTo(newAmount);
        assertThat(itemDAO.getItem(item.getId()).getName()).isEqualTo(newName);
    }

    @Test
    public void canInsertAndUpdateOnlyItemPrice() {
        float newPrice = (float)2.30;

        CreateItemDTO createItemReq = new CreateItemDTO("Prisun", (float)1.70, 5);
        Item item = itemDAO.createItem(createItemReq);

        ItemDTO itemForUpdate = new ItemDTO();
        itemForUpdate.setId(item.getId());
        itemForUpdate.setPrice(newPrice);
        itemDAO.updateItem(itemForUpdate);

        assertThat(itemDAO.getItem(item.getId())).isNotNull();
        assertThat(itemDAO.getItem(item.getId()).getPrice()).isEqualTo(newPrice);
        assertThat(itemDAO.getItem(item.getId()).getAmount()).isEqualTo(item.getAmount());
        assertThat(itemDAO.getItem(item.getId()).getName()).isEqualTo(item.getName());
    }

    @Test
    public void canInsertAndUpdateOnlyItemAmount() {
        int newAmount = 7;

        CreateItemDTO createItemReq = new CreateItemDTO("Prisun", (float)1.70, 5);
        Item item = itemDAO.createItem(createItemReq);

        ItemDTO itemForUpdate = new ItemDTO();
        itemForUpdate.setId(item.getId());
        itemForUpdate.setAmount(newAmount);
        itemDAO.updateItem(itemForUpdate);

        assertThat(itemDAO.getItem(item.getId())).isNotNull();
        assertThat(itemDAO.getItem(item.getId()).getPrice()).isEqualTo(item.getPrice());
        assertThat(itemDAO.getItem(item.getId()).getAmount()).isEqualTo(newAmount);
        assertThat(itemDAO.getItem(item.getId()).getName()).isEqualTo(item.getName());
    }

    @Test
    public void canInsertAndUpdateOnlyItemName() {
        String newName = "New Prisun";

        CreateItemDTO createItemReq = new CreateItemDTO("Prisun", (float)1.70, 5);
        Item item = itemDAO.createItem(createItemReq);

        ItemDTO itemForUpdate = new ItemDTO();
        itemForUpdate.setId(item.getId());
        itemForUpdate.setName(newName);
        itemDAO.updateItem(itemForUpdate);

        assertThat(itemDAO.getItem(item.getId())).isNotNull();
        assertThat(itemDAO.getItem(item.getId()).getPrice()).isEqualTo(item.getPrice());
        assertThat(itemDAO.getItem(item.getId()).getAmount()).isEqualTo(item.getAmount());
        assertThat(itemDAO.getItem(item.getId()).getName()).isEqualTo(newName);
    }
}
