package com.laundry.main.payment.controller;

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
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(
            @Valid @RequestBody PaymentRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Payment created successfully.",
                        paymentService.createPayment(request)));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(
            @PathVariable Long paymentId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payment fetched successfully.",
                        paymentService.getPaymentById(paymentId)));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getPaymentsByOrderId(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payments fetched successfully.",
                        paymentService.getPaymentsByOrderId(orderId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getAllPayments() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payments fetched successfully.",
                        paymentService.getAllPayments()));
    }
}
