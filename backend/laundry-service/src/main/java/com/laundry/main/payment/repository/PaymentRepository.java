package com.laundry.main.payment.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.laundry.main.payment.entity.Payment;
import com.laundry.main.payment.enums.PaymentStatus;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaymentReference(String paymentReference);

    List<Payment> findByOrderOrderId(Long orderId);

    @Query("""
           SELECT COALESCE(SUM(p.paymentAmount),0)
           FROM Payment p
           WHERE p.order.orderId = :orderId
           """)
    BigDecimal getTotalPaidAmount(
            @Param("orderId")
            Long orderId);

    List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);

    boolean existsByPaymentReference(String paymentReference);
}
