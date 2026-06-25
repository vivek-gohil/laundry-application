package com.laundry.main.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.laundry.main.payment.enums.PaymentMethod;
import com.laundry.main.payment.enums.PaymentStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {

    private Long paymentId;

    private Long orderId;

    private String orderNumber;

    private String paymentReference;

    private BigDecimal paymentAmount;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private String transactionId;

    private String remarks;

    private LocalDateTime paymentDate;
}
