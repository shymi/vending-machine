package com.yordanov.vendingmachine.item.controller;

import com.yordanov.vendingmachine.coin.dto.BalanceDTO;
import com.yordanov.vendingmachine.item.dto.CreateItemDTO;
import com.yordanov.vendingmachine.item.dto.ItemDTO;
import com.yordanov.vendingmachine.item.dto.UpdateItemDTO;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/item")
public interface IItemController {
    @Operation(summary = "Get all items in vending machine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List retrieved",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = ItemDTO.class)))}) })
    @GetMapping("/list")
    ResponseEntity getAllItems();

    @Operation(summary = "Get a single item in vending machine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item retrieved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDTO.class))}),
            @ApiResponse(responseCode = "404", description = "No items in vending machine",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}) })
    @GetMapping("/{id}")
    ResponseEntity getItem(@PathVariable("id") Long id);

    @Operation(summary = "Add a single item in vending machine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item retrieved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}) })
    @PutMapping("")
    ResponseEntity addItem(@Valid @RequestBody CreateItemDTO item);

    @Operation(summary = "Update a single item in vending machine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated Item",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}) })
    @PostMapping("/{id}")
    ResponseEntity updateItem(@PathVariable("id") Long id, @Valid @RequestBody UpdateItemDTO item);

    @Operation(summary = "Remove a single item in vending machine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted item",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Item not found in vending machine",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}) })
    @DeleteMapping("/{id}")
    ResponseEntity deleteItem(@PathVariable("id") Long id);

    @Operation(summary = "Purchase item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item purchased",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BalanceDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Item could not be purchased",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}) })
    @PostMapping("/purchase/{id}")
    ResponseEntity purchaseItem(@PathVariable("id") Long id);
}
