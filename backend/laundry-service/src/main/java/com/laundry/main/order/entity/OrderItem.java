package com.laundry.main.order.entity;

import com.laundry.main.audit.BaseAuditableEntity;
import com.laundry.main.servicecatalog.entity.ServiceMaster;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem extends BaseAuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_item_id")
  private Long orderItemId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "service_id")
  private ServiceMaster service;

  @Column(name = "item_name")
  private String itemName;

  @Column(name = "quantity")
  private Integer quantity;

  @Column(name = "unit_price", precision = 10, scale = 2)
  private BigDecimal unitPrice;

  @Column(name = "line_amount", precision = 10, scale = 2)
  private BigDecimal lineAmount;
}
