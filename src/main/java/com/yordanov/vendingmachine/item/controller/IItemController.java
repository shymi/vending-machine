package com.yordanov.vendingmachine.item.controller;

import com.yordanov.vendingmachine.item.dto.CreateItemDTO;
import com.yordanov.vendingmachine.item.dto.ItemDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IItemController {
    @Operation(summary = "Get all items in vending machine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List retrieved",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = ItemDTO.class)))}),
            @ApiResponse(responseCode = "404", description = "No items in vending machine",
                    content = @Content) })
    @GetMapping("/list")
    List<ItemDTO> getAllItems();

    @Operation(summary = "Get a single item in vending machine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item retrieved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDTO.class))}),
            @ApiResponse(responseCode = "404", description = "No items in vending machine",
                    content = @Content) })
    @GetMapping("/{id}")
    ItemDTO getItem(@PathVariable("id") Long id);

    @Operation(summary = "Add a single item in vending machine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item retrieved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content) })
    @PutMapping("/")
    ItemDTO addItem(@RequestBody CreateItemDTO item);

    @Operation(summary = "Update a single item in vending machine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item retrieved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content) })
    @PostMapping("/")
    ItemDTO updateItem(@RequestBody ItemDTO item);

    @Operation(summary = "Remove a single item in vending machine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item retrieved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Item in vending machine not found",
                    content = @Content) })
    @DeleteMapping("/{id}")
    void deleteItem(@PathVariable("id") Long id);
}
