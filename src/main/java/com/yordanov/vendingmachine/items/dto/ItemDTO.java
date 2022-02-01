package com.yordanov.vendingmachine.items.dto;

import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Data
public class ItemDTO {
    @NotBlank
    @Schema(example = "1")
    private Long id;

    @Schema(example = "Coca Cola")
    private String name;

    @Schema(example = "2.50")
    private Float price;

    @Max(10)
    @Schema(example = "10")
    private Integer amount;
}
