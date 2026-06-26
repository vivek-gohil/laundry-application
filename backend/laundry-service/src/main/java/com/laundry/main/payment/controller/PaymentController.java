package com.laundry.main.payment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.laundry.main.common.ApiResponse;
import com.laundry.main.payment.dto.PaymentRequest;
import com.laundry.main.payment.dto.PaymentResponse;
import com.laundry.main.payment.service.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payment", description = "Payment APIs")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @Operation(summary = "Create payment", description = "Creates a new payment for an order")
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(
            @Parameter(description = "Payment create request", required = true)
            @Valid @RequestBody PaymentRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Payment created successfully.",
                        paymentService.createPayment(request)));
    }

    @GetMapping("/{paymentId}")
    @Operation(summary = "Get payment by id", description = "Returns payment details for a given payment id")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(
            @Parameter(description = "Payment id", required = true)
            @PathVariable Long paymentId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payment fetched successfully.",
                        paymentService.getPaymentById(paymentId)));
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Get payments by order id", description = "Returns all payments associated with a given order")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getPaymentsByOrderId(
            @Parameter(description = "Order id", required = true)
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payments fetched successfully.",
                        paymentService.getPaymentsByOrderId(orderId)));
    }

    @GetMapping
    @Operation(summary = "Get all payments", description = "Returns all recorded payments")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getAllPayments() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payments fetched successfully.",
                        paymentService.getAllPayments()));
    }
}
