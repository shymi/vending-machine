package com.yordanov.vendingmachine.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ItemDTO {
    @NotNull(message = "{item.id.blank}")
    @Schema(example = "1")
    private Long id;

    @Schema(example = "Coca Cola")
    private String name;

    @Min(
            value = 0,
            message = "{item.price.invalid}")
    @Schema(example = "2.50")
    private Float price;

    @Range(
            min = 0,
            max = 10,
            message = "{item.amount.invalid}")
    @Schema(example = "7")
    private Integer amount;
}
