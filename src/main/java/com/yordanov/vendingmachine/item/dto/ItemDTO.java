package com.yordanov.vendingmachine.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class ItemDTO {
    @NotBlank
    @Schema(example = "1")
    private Long id;

    @Schema(example = "Coca Cola")
    private String name;

    @Min(0)
    @Schema(example = "2.50")
    private Float price;

    @Max(10)
    @Min(0)
    @Schema(example = "10")
    private Integer amount;
}
