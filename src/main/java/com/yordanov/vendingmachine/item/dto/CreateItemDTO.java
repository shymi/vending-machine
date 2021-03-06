package com.yordanov.vendingmachine.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemDTO {
    @NotBlank(message = "{item.name.blank}")
    @Schema(example = "Sprite")
    private String name;

    @NotNull(message = "{item.price.missing}")
    @Min(
            value = 0,
            message = "{item.price.invalid}")
    @Schema(example = "2.50")
    private Float price;

    @NotNull(message = "{item.amount.missing}")
    @Range(
            min = 0,
            max = 10,
            message = "{item.amount.invalid}")
    @Schema(example = "7")
    private Integer amount;
}
