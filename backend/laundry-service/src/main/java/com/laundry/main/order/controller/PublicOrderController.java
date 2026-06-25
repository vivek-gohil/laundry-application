package com.laundry.main.order.controller;

import com.laundry.main.common.ApiResponse;
import com.laundry.main.order.dto.OrderTrackingResponse;
import com.laundry.main.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/orders")
@RequiredArgsConstructor
@Tag(name = "Public Order", description = "Public order tracking APIs")
public class PublicOrderController {

    private final OrderService orderService;

    @GetMapping("/track")
    @Operation(
            summary = "Track order",
            description = "Returns public tracking details for an order using order number and customer mobile")
    public ResponseEntity<ApiResponse<OrderTrackingResponse>> trackOrder(
            @Parameter(description = "Order number to track", required = true, example = "ORD-20260616143000")
            @RequestParam String orderNumber,
            @Parameter(description = "Customer mobile number used for the order", required = true, example = "9876543210")
            @RequestParam String mobile) {

        OrderTrackingResponse response =
                orderService.trackOrder(orderNumber, mobile);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Order found successfully.",
                        response));
    }
}
