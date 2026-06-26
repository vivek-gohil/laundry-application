package com.laundry.main.payment.entity;

import com.laundry.main.audit.BaseAuditableEntity;
import com.laundry.main.order.entity.Order;
import com.laundry.main.payment.enums.PaymentMethod;
import com.laundry.main.payment.enums.PaymentStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment")
public class Payment extends BaseAuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "payment_id")
  private Long paymentId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @Column(name = "payment_reference", nullable = false, unique = true, length = 30)
  private String paymentReference;

  @Column(name = "payment_amount", precision = 10, scale = 2, nullable = false)
  private BigDecimal paymentAmount;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_method", nullable = false)
  private PaymentMethod paymentMethod;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_status", nullable = false)
  private PaymentStatus paymentStatus;

  @Column(name = "transaction_id")
  private String transactionId;

  @Column(name = "remarks")
  private String remarks;

  @Column(name = "payment_date", nullable = false)
  private LocalDateTime paymentDate;
}
