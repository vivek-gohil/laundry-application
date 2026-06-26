package com.laundry.main.order.repository;

import com.laundry.main.order.entity.Order;
import com.laundry.main.order.enums.OrderStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

  Optional<Order> findByOrderNumber(String orderNumber);

  Optional<Order> findByOrderNumberAndCustomerMobile(String orderNumber, String mobile);

  boolean existsByOrderNumber(String orderNumber);

  List<Order> findByCustomerCustomerId(Long customerId);

  List<Order> findByStatus(OrderStatus status);
}
