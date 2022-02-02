package com.yordanov.vendingmachine.item;

import com.yordanov.vendingmachine.item.dto.CreateItemDTO;
import com.yordanov.vendingmachine.item.dto.ItemDTO;
import com.yordanov.vendingmachine.item.dto.UpdateItemDTO;
import com.yordanov.vendingmachine.util.exception.error.ApiError;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerTest {
    private static final int INVALID_ID = -1;

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MessageSource messageSource;

    @Test
    public void shouldRetrieveAllItems() {
        createItem();
        ResponseEntity<List> getListResponse = this.restTemplate.exchange(
                getBaseUrl() + "/item/list",
                HttpMethod.GET,
                null,
                List.class);
        assertEquals(HttpStatus.OK.value(), getListResponse.getStatusCodeValue());
        assertThat(HttpStatus.OK.value()).isGreaterThan(0);
    }

    @Test
    public void shouldCreateItem() {
        ResponseEntity<ItemDTO> createResponse = createItem();
        assertEquals(HttpStatus.OK.value(), createResponse.getStatusCodeValue());

        ResponseEntity<ItemDTO> getResponse = getItem(createResponse.getBody().getId());
        assertEquals(HttpStatus.OK.value(), getResponse.getStatusCodeValue());
        assertEquals(createResponse.getBody().getName(), getResponse.getBody().getName());
        assertEquals(createResponse.getBody().getPrice(), getResponse.getBody().getPrice());
        assertEquals(createResponse.getBody().getAmount(), getResponse.getBody().getAmount());
    }

    @Test
    public void shouldNotCreateItemOnMissingOrInvalidAmountRequest() {
        // missing amount
        CreateItemDTO item = new CreateItemDTO();
        item.setName("Fanta");
        item.setPrice((float)1.20);
        HttpEntity<CreateItemDTO> request = new HttpEntity<>(item);

        ResponseEntity<ApiError> response = this.restTemplate.exchange(
                getBaseUrl() + "/item",
                HttpMethod.PUT,
                request,
                ApiError.class);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(
                messageSource.getMessage("item.amount.missing", null, Locale.getDefault()),
                response.getBody().getMessage());

        // amount over 10
        item = new CreateItemDTO();
        item.setName("Fanta");
        item.setPrice((float)1.20);
        item.setAmount(20);
        request = new HttpEntity<>(item);

        response = this.restTemplate.exchange(
                getBaseUrl() + "/item",
                HttpMethod.PUT,
                request,
                ApiError.class);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(
                "Amount must be between 0 and 10",
                response.getBody().getMessage());

        // negative amount
        item = new CreateItemDTO();
        item.setName("Fanta");
        item.setPrice((float)1.20);
        item.setAmount(-1);
        request = new HttpEntity<>(item);

        response = this.restTemplate.exchange(
                getBaseUrl() + "/item",
                HttpMethod.PUT,
                request,
                ApiError.class);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(
                "Amount must be between 0 and 10",
                response.getBody().getMessage());
    }

    @Test
    public void shouldNotCreateItemOnMissingOrBlankNameRequest() {
        // missing name
        CreateItemDTO item = new CreateItemDTO();
        item.setPrice((float)1.20);
        item.setAmount(10);
        HttpEntity<CreateItemDTO> request = new HttpEntity<>(item);

        ResponseEntity<ApiError> response = this.restTemplate.exchange(
                getBaseUrl() + "/item",
                HttpMethod.PUT,
                request,
                ApiError.class);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(
                messageSource.getMessage("item.name.blank", null, Locale.getDefault()),
                response.getBody().getMessage());

        // blank name
        item = new CreateItemDTO();
        item.setName("");
        item.setPrice((float)1.20);
        item.setAmount(10);
        request = new HttpEntity<>(item);

        response = this.restTemplate.exchange(
                getBaseUrl() + "/item",
                HttpMethod.PUT,
                request,
                ApiError.class);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(
                messageSource.getMessage("item.name.blank", null, Locale.getDefault()),
                response.getBody().getMessage());
    }

    @Test
    public void shouldNotCreateItemOnMissingOrInvalidPriceRequest() {
        // missing price
        CreateItemDTO item = new CreateItemDTO();
        item.setName("Fanta");
        item.setAmount(10);
        HttpEntity<CreateItemDTO> request = new HttpEntity<>(item);

        ResponseEntity<ApiError> response = this.restTemplate.exchange(
                getBaseUrl() + "/item",
                HttpMethod.PUT,
                request,
                ApiError.class);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(
                messageSource.getMessage("item.price.missing", null, Locale.getDefault()),
                response.getBody().getMessage());

        // negative price
        item = new CreateItemDTO();
        item.setName("Fanta");
        item.setPrice((float)-1.20);
        item.setAmount(10);
        request = new HttpEntity<>(item);

        response = this.restTemplate.exchange(
                getBaseUrl() + "/item",
                HttpMethod.PUT,
                request,
                ApiError.class);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(
                messageSource.getMessage("item.price.invalid", null, Locale.getDefault()),
                response.getBody().getMessage());
    }

    @Test
    public void shouldReturnErrorOnInvalidUpdateRequest() {
        ResponseEntity<ItemDTO> createdItemResponse = createItem();
        UpdateItemDTO item = new UpdateItemDTO();
        HttpEntity<UpdateItemDTO> request = new HttpEntity<>(item);

        ResponseEntity<ApiError> response = this.restTemplate.exchange(
                getBaseUrl() + "/item/" + createdItemResponse.getBody().getId(),
                HttpMethod.POST,
                request,
                ApiError.class);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(
                messageSource.getMessage("item.update.invalid", null, Locale.getDefault()),
                response.getBody().getMessage());
    }

    @Test
    public void shouldUpdateOnlyName() {
        String newName = "NewName";

        ResponseEntity<ItemDTO> createdItemResponse = createItem();
        UpdateItemDTO item = new UpdateItemDTO();
        item.setName(newName);
        HttpEntity<UpdateItemDTO> request = new HttpEntity<>(item);

        this.restTemplate.exchange(
                getBaseUrl() + "/item/" + createdItemResponse.getBody().getId(),
                HttpMethod.POST,
                request,
                ItemDTO.class);

        ResponseEntity<ItemDTO> response = getItem(createdItemResponse.getBody().getId());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(newName, response.getBody().getName());
        assertEquals(createdItemResponse.getBody().getPrice(), response.getBody().getPrice());
        assertEquals(createdItemResponse.getBody().getAmount(), response.getBody().getAmount());
    }

    @Test
    public void shouldUpdateOnlyPrice() {
        float newPrice = (float)9.99;

        ResponseEntity<ItemDTO> createdItemResponse = createItem();
        UpdateItemDTO item = new UpdateItemDTO();
        item.setPrice(newPrice);
        HttpEntity<UpdateItemDTO> request = new HttpEntity<>(item);

        this.restTemplate.exchange(
                getBaseUrl() + "/item/" + createdItemResponse.getBody().getId(),
                HttpMethod.POST,
                request,
                ItemDTO.class);

        ResponseEntity<ItemDTO> response = getItem(createdItemResponse.getBody().getId());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(createdItemResponse.getBody().getName(), response.getBody().getName());
        assertEquals(newPrice, response.getBody().getPrice());
        assertEquals(createdItemResponse.getBody().getAmount(), response.getBody().getAmount());
    }

    @Test
    public void shouldUpdateOnlyAmount() {
        int newAmount = 3;

        ResponseEntity<ItemDTO> createdItemResponse = createItem();
        UpdateItemDTO item = new UpdateItemDTO();
        item.setAmount(newAmount);
        HttpEntity<UpdateItemDTO> request = new HttpEntity<>(item);

        this.restTemplate.exchange(
                getBaseUrl() + "/item/" + createdItemResponse.getBody().getId(),
                HttpMethod.POST,
                request,
                ItemDTO.class);

        ResponseEntity<ItemDTO> response = getItem(createdItemResponse.getBody().getId());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(createdItemResponse.getBody().getName(), response.getBody().getName());
        assertEquals(createdItemResponse.getBody().getPrice(), response.getBody().getPrice());
        assertEquals(newAmount, response.getBody().getAmount());
    }

    @Test
    public void shouldNotUpdateOnInvalidPrice() {
        float newPrice = (float)-9.99;

        ResponseEntity<ItemDTO> createdItemResponse = createItem();
        UpdateItemDTO item = new UpdateItemDTO();
        item.setPrice(newPrice);
        HttpEntity<UpdateItemDTO> request = new HttpEntity<>(item);

        ResponseEntity<ApiError> updateResponse = this.restTemplate.exchange(
                getBaseUrl() + "/item/" + createdItemResponse.getBody().getId(),
                HttpMethod.POST,
                request,
                ApiError.class);
        assertEquals(HttpStatus.BAD_REQUEST.value(), updateResponse.getStatusCodeValue());
        assertEquals(
                messageSource.getMessage("item.price.invalid", null, Locale.getDefault()),
                updateResponse.getBody().getMessage());

        ResponseEntity<ItemDTO> getResponse = getItem(createdItemResponse.getBody().getId());
        assertEquals(HttpStatus.OK.value(), getResponse.getStatusCodeValue());
        assertEquals(createdItemResponse.getBody().getName(), getResponse.getBody().getName());
        assertEquals(createdItemResponse.getBody().getPrice(), getResponse.getBody().getPrice());
        assertEquals(createdItemResponse.getBody().getAmount(), getResponse.getBody().getAmount());
    }

    @Test
    public void shouldNotUpdateOnInvalidAmount() {
        // negative amount
        ResponseEntity<ItemDTO> createdItemResponse = createItem();
        UpdateItemDTO item = new UpdateItemDTO();
        item.setAmount(-1);
        HttpEntity<UpdateItemDTO> request = new HttpEntity<>(item);

        ResponseEntity<ApiError> updateResponse = this.restTemplate.exchange(
                getBaseUrl() + "/item/" + createdItemResponse.getBody().getId(),
                HttpMethod.POST,
                request,
                ApiError.class);
        assertEquals(HttpStatus.BAD_REQUEST.value(), updateResponse.getStatusCodeValue());
        assertEquals(
                "Amount must be between 0 and 10",
                updateResponse.getBody().getMessage());

        // above max amount
        item = new UpdateItemDTO();
        item.setAmount(20);
        request = new HttpEntity<>(item);
        updateResponse = this.restTemplate.exchange(
                getBaseUrl() + "/item/" + createdItemResponse.getBody().getId(),
                HttpMethod.POST,
                request,
                ApiError.class);
        assertEquals(HttpStatus.BAD_REQUEST.value(), updateResponse.getStatusCodeValue());
        assertEquals(
                "Amount must be between 0 and 10",
                updateResponse.getBody().getMessage());

        ResponseEntity<ItemDTO> getResponse = getItem(createdItemResponse.getBody().getId());
        assertEquals(HttpStatus.OK.value(), getResponse.getStatusCodeValue());
        assertEquals(createdItemResponse.getBody().getName(), getResponse.getBody().getName());
        assertEquals(createdItemResponse.getBody().getPrice(), getResponse.getBody().getPrice());
        assertEquals(createdItemResponse.getBody().getAmount(), getResponse.getBody().getAmount());
    }

    @Test
    public void shouldNotUpdateOnInvalidId() {
        ResponseEntity<ItemDTO> createdItemResponse = createItem();
        UpdateItemDTO item = new UpdateItemDTO();
        item.setPrice((float) 100);
        HttpEntity<UpdateItemDTO> request = new HttpEntity<>(item);

        ResponseEntity<ApiError> updateResponse = this.restTemplate.exchange(
                getBaseUrl() + "/item/" + INVALID_ID,
                HttpMethod.POST,
                request,
                ApiError.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), updateResponse.getStatusCodeValue());
        assertEquals(
                messageSource.getMessage("item.missing", null, Locale.getDefault()),
                updateResponse.getBody().getMessage());

        ResponseEntity<ItemDTO> getResponse = getItem(createdItemResponse.getBody().getId());
        assertEquals(HttpStatus.OK.value(), getResponse.getStatusCodeValue());
        assertEquals(createdItemResponse.getBody().getName(), getResponse.getBody().getName());
        assertEquals(createdItemResponse.getBody().getPrice(), getResponse.getBody().getPrice());
        assertEquals(createdItemResponse.getBody().getAmount(), getResponse.getBody().getAmount());
    }

    @Test
    public void shouldDeleteItem() {
        ResponseEntity<ItemDTO> createdItemResponse = createItem();

        ResponseEntity<ItemDTO> deleteResponse = this.restTemplate.exchange(
                getBaseUrl() + "/item/" + createdItemResponse.getBody().getId(),
                HttpMethod.DELETE,
                null,
                ItemDTO.class);
        assertEquals(HttpStatus.OK.value(), deleteResponse.getStatusCodeValue());
        assertEquals(createdItemResponse.getBody().getName(), deleteResponse.getBody().getName());
        assertEquals(createdItemResponse.getBody().getPrice(), deleteResponse.getBody().getPrice());
        assertEquals(createdItemResponse.getBody().getAmount(), deleteResponse.getBody().getAmount());

        ResponseEntity<ApiError> getResponse = this.restTemplate.exchange(
                getBaseUrl() + "/item/" + createdItemResponse.getBody().getId(),
                HttpMethod.GET,
                null,
                ApiError.class);

        assertEquals(HttpStatus.NOT_FOUND.value(), getResponse.getStatusCodeValue());
        assertEquals(
                messageSource.getMessage("item.missing", null, Locale.getDefault()),
                getResponse.getBody().getMessage());
    }

    @Test
    public void shouldReturnErrorOnNotFindingAnItem() {
        ResponseEntity<ApiError> deleteResponse = this.restTemplate.exchange(
                getBaseUrl() + "/item/" + INVALID_ID,
                HttpMethod.DELETE,
                null,
                ApiError.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), deleteResponse.getStatusCodeValue());
        assertEquals(
                messageSource.getMessage("item.missing", null, Locale.getDefault()),
                deleteResponse.getBody().getMessage());
    }

    private ResponseEntity<ItemDTO> createItem() {
        CreateItemDTO item = new CreateItemDTO("Fanta", (float)1.20, 5);
        HttpEntity<CreateItemDTO> request = new HttpEntity<>(item);

        return this.restTemplate.exchange(
                getBaseUrl() + "/item",
                HttpMethod.PUT,
                request,
                ItemDTO.class);
    }

    private ResponseEntity<ItemDTO> getItem(Long id) {
        return this.restTemplate.exchange(
                getBaseUrl() + "/item/" + id,
                HttpMethod.GET,
                null,
                ItemDTO.class);
    }

    private String getBaseUrl() {
        return  "http://localhost:" + port + "/api/v1";
    }
}
