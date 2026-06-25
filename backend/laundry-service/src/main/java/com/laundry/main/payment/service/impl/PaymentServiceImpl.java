package com.laundry.main.payment.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laundry.main.exception.BadRequestException;
import com.laundry.main.exception.ResourceNotFoundException;
import com.laundry.main.order.entity.Order;
import com.laundry.main.order.repository.OrderRepository;
import com.laundry.main.payment.dto.PaymentRequest;
import com.laundry.main.payment.dto.PaymentResponse;
import com.laundry.main.payment.entity.Payment;
import com.laundry.main.payment.enums.PaymentStatus;
import com.laundry.main.payment.mapper.PaymentMapper;
import com.laundry.main.payment.repository.PaymentRepository;
import com.laundry.main.payment.service.PaymentService;
import com.laundry.main.common.constants.ReferencePrefix;
import com.laundry.main.common.util.ReferenceNumberGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentResponse createPayment(
            PaymentRequest request) {

        Order order = findOrder(request.getOrderId());

        BigDecimal totalPaid =
                paymentRepository.getTotalPaidAmount(order.getOrderId());

        BigDecimal orderAmount =
                order.getTotalAmount();

        BigDecimal newTotalPaid =
                totalPaid.add(request.getPaymentAmount());

        validatePayment(orderAmount, newTotalPaid);

        Payment payment =
                paymentMapper.toEntity(request);

        payment.setOrder(order);

        payment.setPaymentReference(
                ReferenceNumberGenerator.generate(
                        ReferencePrefix.PAYMENT));

        payment.setPaymentDate(
                LocalDateTime.now());

        payment.setPaymentStatus(
                determinePaymentStatus(
                        orderAmount,
                        newTotalPaid));

        Payment savedPayment =
                paymentRepository.save(payment);

        return paymentMapper.toResponse(savedPayment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(
            Long paymentId) {

        Payment payment =
                findPayment(paymentId);

        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getPaymentsByOrderId(
            Long orderId) {

        return paymentRepository.findByOrderOrderId(orderId)
                .stream()
                .map(paymentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toResponse)
                .toList();
    }

    // =====================================================
    // PRIVATE METHODS
    // =====================================================

    private Order findOrder(
            Long orderId) {

        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with id : " + orderId));
    }

    private Payment findPayment(
            Long paymentId) {

        return paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found with id : " + paymentId));
    }

    private void validatePayment(
            BigDecimal orderAmount,
            BigDecimal newTotalPaid) {

        if (newTotalPaid.compareTo(orderAmount) > 0) {

            throw new BadRequestException(
                    "Payment amount exceeds order amount.");
        }
    }

    private PaymentStatus determinePaymentStatus(
            BigDecimal orderAmount,
            BigDecimal totalPaid) {

        if (totalPaid.compareTo(BigDecimal.ZERO) == 0) {
            return PaymentStatus.PENDING;
        }

        if (totalPaid.compareTo(orderAmount) < 0) {
            return PaymentStatus.PARTIAL;
        }

        return PaymentStatus.PAID;
    }
}
