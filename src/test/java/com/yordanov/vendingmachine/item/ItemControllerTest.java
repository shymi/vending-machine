package com.yordanov.vendingmachine.item;

import com.yordanov.vendingmachine.item.dto.ItemDTO;
import com.yordanov.vendingmachine.util.exception.error.ApiError;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MessageSource messageSource;

    @Test
    public void shouldReturnErrorOnInvalidRequest() {
        ItemDTO item = new ItemDTO();
        item.setId((long)1);
        HttpEntity<ItemDTO> request = new HttpEntity<>(item);

        ResponseEntity<ApiError> response = this.restTemplate.exchange(
                getBaseUrl() + "/item",
                HttpMethod.POST,
                request,
                ApiError.class);
        assertEquals(response.getStatusCodeValue(), 400);
        assertEquals(response.getBody().getMessage(), messageSource.getMessage("item.update.invalid", null, Locale.getDefault()));
    }

    private String getBaseUrl() {
        return  "http://localhost:" + port + "/api/v1";
    }
}
