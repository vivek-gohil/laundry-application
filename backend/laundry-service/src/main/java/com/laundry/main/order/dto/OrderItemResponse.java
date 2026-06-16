package com.laundry.main.order.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Order item response")
public class OrderItemResponse {

    @Schema(example = "1")
    private Long orderItemId;

    @Schema(example = "1")
    private Long serviceId;

    @Schema(example = "Shirt Wash")
    private String serviceName;

    @Schema(example = "Shirt Wash")
    private String itemName;

    @Schema(example = "2")
    private Integer quantity;

    @Schema(example = "50.00")
    private BigDecimal unitPrice;

    @Schema(example = "100.00")
    private BigDecimal lineAmount;
}
