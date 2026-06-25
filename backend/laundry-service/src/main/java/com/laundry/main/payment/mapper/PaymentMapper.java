package com.laundry.main.payment.mapper;

import com.laundry.main.common.mapper.CentralMapperConfig;
import com.laundry.main.order.entity.Order;
import com.laundry.main.payment.dto.PaymentRequest;
import com.laundry.main.payment.dto.PaymentResponse;
import com.laundry.main.payment.entity.Payment;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = CentralMapperConfig.class)
public interface PaymentMapper {

    // =====================================================
    // PAYMENT
    // =====================================================

    @Mapping(target = "paymentId", ignore = true)
    @Mapping(target = "paymentReference", ignore = true)
    @Mapping(target = "paymentStatus", ignore = true)
    @Mapping(target = "paymentDate", ignore = true)
    @Mapping(target = "order", ignore = true)
    Payment toEntity(PaymentRequest request);

    @Mapping(target = "orderId", source = "order.orderId")
    @Mapping(target = "orderNumber", source = "order.orderNumber")
    PaymentResponse toResponse(Payment payment);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "paymentId", ignore = true)
    @Mapping(target = "paymentReference", ignore = true)
    @Mapping(target = "paymentStatus", ignore = true)
    @Mapping(target = "paymentDate", ignore = true)
    @Mapping(target = "order", ignore = true)
    void updateEntity(
            PaymentRequest request,
            @MappingTarget Payment payment);

    // =====================================================
    // CUSTOM MAPPERS
    // =====================================================

    default Order mapOrderIdToOrder(
            Long orderId) {

        if (orderId == null) {
            return null;
        }

        Order order = new Order();
        order.setOrderId(orderId);

        return order;
    }

    default Long mapOrderToOrderId(
            Order order) {

        return order == null
                ? null
                : order.getOrderId();
    }
}
