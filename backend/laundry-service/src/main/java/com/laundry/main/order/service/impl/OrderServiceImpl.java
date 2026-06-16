package com.laundry.main.order.service.impl;

import com.laundry.main.customer.entity.Customer;
import com.laundry.main.customer.repository.CustomerRepository;
import com.laundry.main.exception.ResourceNotFoundException;
import com.laundry.main.order.dto.OrderItemResponse;
import com.laundry.main.order.dto.OrderRequest;
import com.laundry.main.order.dto.OrderResponse;
import com.laundry.main.order.entity.Order;
import com.laundry.main.order.entity.OrderItem;
import com.laundry.main.order.enums.OrderStatus;
import com.laundry.main.order.mapper.OrderMapper;
import com.laundry.main.order.repository.OrderRepository;
import com.laundry.main.order.service.OrderService;
import com.laundry.main.order.util.OrderNumberGenerator;
import com.laundry.main.servicecatalog.entity.ServiceMaster;
import com.laundry.main.servicecatalog.repository.ServiceMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ServiceMasterRepository serviceRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found with id: " + request.getCustomerId()));
        Order order = orderMapper.toEntity(request);
        List<OrderItem> orderItems = orderMapper.toEntity(request.getItems());
        order.setItems(orderItems);
        order.setCustomer(customer);
        order.setStatus(OrderStatus.CREATED);
        order.setOrderNumber(OrderNumberGenerator.generate());
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItem item : order.getItems()) {
            ServiceMaster service = serviceRepository.findById(item.getService().getServiceId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Service not found with id: " + item.getService().getServiceId()));
            item.setOrder(order);
            item.setService(service);
            item.setUnitPrice(service.getPrice());
            BigDecimal lineAmount = service.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            item.setLineAmount(lineAmount);
            totalAmount = totalAmount.add(lineAmount);
        }
        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);
        return mapResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long orderId) {
        return mapResponse(findOrder(orderId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream().map(this::mapResponse).toList();
    }

    @Override
    public OrderResponse updateOrderStatus(Long orderId, String status) {
        Order order = findOrder(orderId);
        order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        return mapResponse(orderRepository.save(order));
    }

    private Order findOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
    }

    private OrderResponse mapResponse(Order order) {
        OrderResponse response = orderMapper.toResponse(order);
        List<OrderItemResponse> items = order.getItems().stream().map(orderMapper::toResponse).toList();
        response.setItems(items);
        return response;
    }
}