package com.yordanov.vendingmachine.coin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceDTO {
    @NotNull
    @Schema(example = "5.50lv")
    private float balance = 0;
}
