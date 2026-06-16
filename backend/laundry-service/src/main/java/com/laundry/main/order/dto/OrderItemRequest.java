package com.laundry.main.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Order item create request")
public class OrderItemRequest {

    @Schema(description = "Reference service id for the order item", example = "1")
    @NotNull(message = "Service id is required")
    private Long serviceId;

    @Schema(description = "Name of the order item", example = "Shirt Wash")
    @NotBlank(message = "Item name is required")
    private String itemName;

    @Schema(description = "Quantity of items", example = "2")
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

}
