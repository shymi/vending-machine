package com.yordanov.vendingmachine.items.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Data
public class CreateItemDTO {
    @NotBlank
    @Schema(example = "Sprite")
    private String name;

    @NotBlank
    @Schema(example = "2.50")
    private Float price;

    @NotBlank
    @Max(10)
    @Schema(example = "7")
    private Integer amount;
}
