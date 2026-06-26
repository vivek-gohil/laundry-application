package com.laundry.main.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Order response")
public class OrderResponse {

  @Schema(example = "1")
  private Long orderId;

  @Schema(example = "ORD-20260616143000")
  private String orderNumber;

  @Schema(example = "1")
  private Long customerId;

  @Schema(example = "Rahul Sharma")
  private String customerName;

  @Schema(example = "2026-06-20")
  private LocalDate pickupDate;

  @Schema(example = "2026-06-22")
  private LocalDate deliveryDate;

  @Schema(example = "CREATED")
  private String status;

  @Schema(example = "250.00")
  private BigDecimal totalAmount;

  private List<OrderItemResponse> items;
}
