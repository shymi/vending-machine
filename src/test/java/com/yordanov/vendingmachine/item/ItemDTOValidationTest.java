package com.yordanov.vendingmachine.item;

import com.yordanov.vendingmachine.item.controller.IItemController;
import com.yordanov.vendingmachine.item.dto.ItemDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ItemDTOValidationTest {
    @Autowired
    private IItemController controller;

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldNotHaveErrorsWhenValid() {
        ItemDTO item = new ItemDTO();
        item.setId((long)1);
        item.setName("NewName");
        item.setPrice((float)2.20);
        item.setAmount(3);

        Set<ConstraintViolation<ItemDTO>> violations = validator.validate(item);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldHaveErrorWhenIdIsNotSet() {
        ItemDTO item = new ItemDTO();
        item.setName("NewName");
        item.setPrice((float)2.20);
        item.setAmount(3);

        controller.updateItem(item);
    }

    @Test
    public void shouldHaveErrorWhenPriceIsNegative() {
        ItemDTO item = new ItemDTO();
        item.setId((long)1);
        item.setName("NewName");
        item.setPrice((float)-2.20);
        item.setAmount(3);

        Set<ConstraintViolation<ItemDTO>> violations = validator.validate(item);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void shouldHaveErrorWhenAmountIsInvalid() {
        ItemDTO item = new ItemDTO();
        item.setId((long)1);
        item.setName("NewName");
        item.setPrice((float)2.20);
        item.setAmount(-1);

        Set<ConstraintViolation<ItemDTO>> violations = validator.validate(item);
        assertThat(violations.size()).isEqualTo(1);

        item.setAmount(11);

        violations = validator.validate(item);
        assertThat(violations.size()).isEqualTo(1);
    }
}
