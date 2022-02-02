package com.yordanov.vendingmachine.coin.controler;

import com.yordanov.vendingmachine.coin.dto.AddCoinDTO;
import com.yordanov.vendingmachine.coin.dto.BalanceDTO;
import com.yordanov.vendingmachine.common.error.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/coin")
public interface ICoinController {
    @Operation(summary = "Get current balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Balance retrieved",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = BalanceDTO.class)))}) })
    @GetMapping("/balance")
    ResponseEntity getCurrentBalance();

    @Operation(summary = "Add coin to balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coin successfully added",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = BalanceDTO.class)))}),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))})})
    @PostMapping("")
    ResponseEntity addCoin(@RequestBody AddCoinDTO addCoin);

    @Operation(summary = "Reset balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Balance reset successful",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = BalanceDTO.class)))})})
    @DeleteMapping("")
    ResponseEntity resetBalance();
}
