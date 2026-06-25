package com.laundry.main.order.service;

import com.laundry.main.order.dto.OrderRequest;
import com.laundry.main.order.dto.OrderResponse;
import com.laundry.main.order.dto.OrderTrackingResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    OrderResponse getOrderById(Long orderId);

    List<OrderResponse> getAllOrders();

    OrderResponse updateOrderStatus(Long orderId,String status);

    OrderTrackingResponse trackOrder(String orderNumber,String mobile);
}