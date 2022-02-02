package com.yordanov.vendingmachine.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    @Schema(example = "1")
    private Long id;

    @Schema(example = "Coca Cola")
    private String name;

    @Schema(example = "2.50")
    private Float price;

    @Schema(example = "7")
    private Integer amount;
}
