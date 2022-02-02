package com.yordanov.vendingmachine.coin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCoinDTO {
    @NotBlank
    @Schema(example = "50st")
    private String coin;
}
