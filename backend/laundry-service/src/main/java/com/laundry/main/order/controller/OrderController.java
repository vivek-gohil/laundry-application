package com.laundry.main.order.controller;

import com.laundry.main.common.ApiResponse;
import com.laundry.main.order.dto.OrderRequest;
import com.laundry.main.order.dto.OrderResponse;
import com.laundry.main.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Order", description = "Order CRUD APIs")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @Operation(summary = "Create order", description = "Creates a new order")
  @PostMapping
  public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
      @Valid @RequestBody OrderRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success("Order created successfully", orderService.createOrder(request)));
  }

  @Operation(summary = "Get order by id", description = "Returns an order by order id")
  @GetMapping("/{orderId}")
  public ResponseEntity<ApiResponse<OrderResponse>> getOrder(@PathVariable Long orderId) {
    return ResponseEntity.ok(
        ApiResponse.success("Order fetched successfully", orderService.getOrderById(orderId)));
  }

  @Operation(summary = "Get all orders", description = "Returns all orders")
  @GetMapping
  public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
    return ResponseEntity.ok(
        ApiResponse.success("Orders fetched successfully", orderService.getAllOrders()));
  }

  @Operation(summary = "Update order status", description = "Updates an existing order status")
  @PatchMapping("/{orderId}/status")
  public ResponseEntity<ApiResponse<OrderResponse>> updateStatus(
      @PathVariable Long orderId,
      @Parameter(description = "New order status", required = true) @RequestParam String status) {
    return ResponseEntity.ok(
        ApiResponse.success(
            "Order status updated successfully", orderService.updateOrderStatus(orderId, status)));
  }
}
