package com.yordanov.vendingmachine.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
public class UpdateItemDTO {
    @Schema(example = "Sprite")
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
