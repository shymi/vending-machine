package com.yordanov.vendingmachine.item;

import com.yordanov.vendingmachine.coin.dto.AddCoinDTO;
import com.yordanov.vendingmachine.coin.dto.BalanceDTO;
import com.yordanov.vendingmachine.common.error.ApiError;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Locale;

import static com.yordanov.vendingmachine.utils.URLUtils.getBaseUrl;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CoinControllerTest extends AbstractControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MessageSource messageSource;

    @Test
    public void contextLoads() {
        assertThat(restTemplate).isNotNull();
        assertThat(messageSource).isNotNull();
    }

    @Test
    public void canAddCoin() {
        this.restTemplate.exchange(
                getBaseUrl(port) + "/coin",
                HttpMethod.DELETE,
                null,
                BalanceDTO.class);

        AddCoinDTO addCoin = new AddCoinDTO("2lv");
        HttpEntity<AddCoinDTO> request = new HttpEntity<>(addCoin);
        new HttpEntity<>(addCoin);
        this.restTemplate.exchange(
                getBaseUrl(port) + "/coin",
                HttpMethod.POST,
                request,
                BalanceDTO.class);

        addCoin = new AddCoinDTO("1lv");
        request = new HttpEntity<>(addCoin);
        this.restTemplate.exchange(
                getBaseUrl(port) + "/coin",
                HttpMethod.POST,
                request,
                BalanceDTO.class);

        addCoin = new AddCoinDTO("50st");
        request = new HttpEntity<>(addCoin);
        this.restTemplate.exchange(
                getBaseUrl(port) + "/coin",
                HttpMethod.POST,
                request,
                BalanceDTO.class);

        addCoin = new AddCoinDTO("20st");
        request = new HttpEntity<>(addCoin);
        this.restTemplate.exchange(
                getBaseUrl(port) + "/coin",
                HttpMethod.POST,
                request,
                BalanceDTO.class);

        addCoin = new AddCoinDTO("10st");
        request = new HttpEntity<>(addCoin);
        HttpEntity<BalanceDTO> response = this.restTemplate.exchange(
                getBaseUrl(port) + "/coin",
                HttpMethod.POST,
                request,
                BalanceDTO.class);
        Float expected = (float) 3.8;
        assertEquals(expected, response.getBody().getBalance());
    }

    @Test
    public void shouldResetBalance() {
        ResponseEntity<BalanceDTO> response = this.restTemplate.exchange(
                getBaseUrl(port) + "/coin",
                HttpMethod.DELETE,
                null,
                BalanceDTO.class);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        Float expected = (float) 0;
        assertEquals(expected, response.getBody().getBalance());
    }

    @Test
    public void shouldReturnErrorOnBadCoinRequest() {
        AddCoinDTO addCoin = new AddCoinDTO("5lv");
        HttpEntity<AddCoinDTO> request = new HttpEntity<>(addCoin);
        ResponseEntity<ApiError> response = this.restTemplate.exchange(
                getBaseUrl(port) + "/coin",
                HttpMethod.POST,
                request,
                ApiError.class);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(
                messageSource.getMessage("coin.invalid", null, Locale.getDefault()),
                response.getBody().getMessage());

        addCoin = new AddCoinDTO("70st");
        request = new HttpEntity<>(addCoin);
        response = this.restTemplate.exchange(
                getBaseUrl(port) + "/coin",
                HttpMethod.POST,
                request,
                ApiError.class);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(
                messageSource.getMessage("coin.invalid", null, Locale.getDefault()),
                response.getBody().getMessage());
    }

    @Test
    public void shouldGetCurrentBalance() {
        AddCoinDTO addCoin = new AddCoinDTO("2lv");
        HttpEntity<AddCoinDTO> request = new HttpEntity<>(addCoin);
        new HttpEntity<>(addCoin);
        this.restTemplate.exchange(
                getBaseUrl(port) + "/coin",
                HttpMethod.POST,
                request,
                BalanceDTO.class);

        addCoin = new AddCoinDTO("1lv");
        request = new HttpEntity<>(addCoin);
        this.restTemplate.exchange(
                getBaseUrl(port) + "/coin",
                HttpMethod.POST,
                request,
                BalanceDTO.class);

        addCoin = new AddCoinDTO("50st");
        request = new HttpEntity<>(addCoin);
        this.restTemplate.exchange(
                getBaseUrl(port) + "/coin",
                HttpMethod.POST,
                request,
                BalanceDTO.class);

        addCoin = new AddCoinDTO("20st");
        request = new HttpEntity<>(addCoin);
        this.restTemplate.exchange(
                getBaseUrl(port) + "/coin",
                HttpMethod.POST,
                request,
                BalanceDTO.class);

        addCoin = new AddCoinDTO("10st");
        request = new HttpEntity<>(addCoin);
        this.restTemplate.exchange(
                getBaseUrl(port) + "/coin",
                HttpMethod.POST,
                request,
                BalanceDTO.class);

        ResponseEntity<BalanceDTO> response = this.restTemplate.exchange(
                getBaseUrl(port) + "/coin/balance",
                HttpMethod.GET,
                null,
                BalanceDTO.class);
        Float expected = (float) 3.8;
        assertEquals(expected, response.getBody().getBalance());
    }
}
