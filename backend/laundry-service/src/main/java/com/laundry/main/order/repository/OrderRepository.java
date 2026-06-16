package com.laundry.main.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laundry.main.order.entity.Order;
import com.laundry.main.order.enums.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNumber(String orderNumber);

    boolean existsByOrderNumber(String orderNumber);

    List<Order> findByCustomerCustomerId( Long customerId);

    List<Order> findByStatus(OrderStatus status);
}
