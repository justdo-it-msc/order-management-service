package com.sparta.order_management_service.domain.order.repository;

import com.sparta.order_management_service.domain.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
