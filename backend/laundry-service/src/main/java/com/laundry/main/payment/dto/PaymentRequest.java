package com.laundry.main.payment.dto;

import com.laundry.main.payment.enums.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Schema(description = "Payment create request")
public class PaymentRequest {

  @Schema(description = "Order id for this payment", example = "1")
  @NotNull(message = "Order Id is required")
  private Long orderId;

  @Schema(description = "Payment amount", example = "250.00")
  @NotNull(message = "Payment amount is required")
  @DecimalMin(value = "0.01", message = "Payment amount must be greater than zero")
  private BigDecimal paymentAmount;

  @Schema(description = "Payment method", example = "CREDIT_CARD")
  @NotNull(message = "Payment method is required")
  private PaymentMethod paymentMethod;

  @Schema(description = "External transaction identifier", example = "TXN123456789")
  private String transactionId;

  @Schema(description = "Optional remarks for the payment", example = "Paid in full")
  private String remarks;
}
