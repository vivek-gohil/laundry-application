package com.laundry.main.payment.service;

import java.util.List;

import com.laundry.main.payment.dto.PaymentRequest;
import com.laundry.main.payment.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse createPayment(
            PaymentRequest request);

    PaymentResponse getPaymentById(
            Long paymentId);

    List<PaymentResponse> getPaymentsByOrderId(
            Long orderId);

    List<PaymentResponse> getAllPayments();
}
