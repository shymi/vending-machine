package com.yordanov.vendingmachine.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Required;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class CreateItemDTO {
    @NotBlank
    @Schema(example = "Sprite")
    private String name;

    @NotBlank
    @Min(0)
    @Schema(example = "2.50")
    private Float price;

    @NotBlank
    @Max(10)
    @Min(0)
    @Schema(example = "7")
    private Integer amount;
}
