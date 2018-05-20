package com.superxc.jxshop.repository;

import com.superxc.jxshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>{
    public List<Order> findAllByUserId(Long userId);
}
