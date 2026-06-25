package com.laundry.main.order.mapper;

import com.laundry.main.common.mapper.CentralMapperConfig;
import com.laundry.main.customer.entity.Customer;
import com.laundry.main.order.dto.OrderItemRequest;
import com.laundry.main.order.dto.OrderItemResponse;
import com.laundry.main.order.dto.OrderRequest;
import com.laundry.main.order.dto.OrderResponse;
import com.laundry.main.order.dto.OrderTrackingResponse;
import com.laundry.main.order.entity.Order;
import com.laundry.main.order.entity.OrderItem;
import com.laundry.main.servicecatalog.entity.ServiceMaster;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(config = CentralMapperConfig.class)
public interface OrderMapper {

    // =====================================================
    // ORDER
    // =====================================================

    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "orderNumber", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    Order toEntity(OrderRequest request);

    @Mapping(target = "customerId",
            source = "customer.customerId")

    @Mapping(target = "customerName",
            source = "customer")

    @Mapping(target = "status",
            expression = "java(order.getStatus() == null ? null : order.getStatus().name())")

    @Mapping(target = "items",
            source = "items")
    OrderResponse toResponse(Order order);

    @Mapping(target = "customerName",
            source = "customer")

    @Mapping(target = "mobile",
            source = "customer.mobile",
            qualifiedByName = "maskMobile")

    @Mapping(target = "status",
            expression = "java(order.getStatus() == null ? null : order.getStatus().name())")
    OrderTrackingResponse toTrackingResponse(Order order);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "orderNumber", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "items", ignore = true)
    void updateEntity(
            OrderRequest request,
            @MappingTarget Order order);

    // =====================================================
    // ORDER ITEM
    // =====================================================

    @Mapping(target = "orderItemId", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "service", source = "serviceId")
    @Mapping(target = "unitPrice", ignore = true)
    @Mapping(target = "lineAmount", ignore = true)
    OrderItem toEntity(OrderItemRequest request);

    @Mapping(target = "serviceId",
            source = "service.serviceId")

    @Mapping(target = "serviceName",
            source = "service.serviceName")
    OrderItemResponse toResponse(OrderItem orderItem);

    List<OrderItem> toEntity(
            List<OrderItemRequest> requests);

    List<OrderItemResponse> mapItems(
            List<OrderItem> items);

    // =====================================================
    // CUSTOM MAPPERS
    // =====================================================

    default ServiceMaster mapServiceIdToService(
            Long serviceId) {

        if (serviceId == null) {
            return null;
        }

        ServiceMaster service =
                new ServiceMaster();

        service.setServiceId(serviceId);

        return service;
    }

    default Long mapServiceToServiceId(
            ServiceMaster service) {

        return service == null
                ? null
                : service.getServiceId();
    }

    default String mapCustomerName(
            Customer customer) {

        if (customer == null) {
            return null;
        }

        String firstName =
                customer.getFirstName() == null
                        ? ""
                        : customer.getFirstName();

        String lastName =
                customer.getLastName() == null
                        ? ""
                        : customer.getLastName();

        return (firstName + " " + lastName).trim();
    }

    @Named("maskMobile")
    default String maskMobile(String mobile) {

        if (mobile == null || mobile.length() != 10) {
            return mobile;
        }

        return mobile.substring(0, 2)
                + "******"
                + mobile.substring(8);
    }
}