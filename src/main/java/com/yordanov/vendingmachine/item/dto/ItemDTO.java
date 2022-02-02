package com.yordanov.vendingmachine.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    @NotNull
    @Schema(example = "1")
    private Long id;

    @NotNull
    @Schema(example = "Coca Cola")
    private String name;

    @NotNull
    @Schema(example = "2.50")
    private Float price;

    @NotNull
    @Schema(example = "7")
    private Integer amount;
}
