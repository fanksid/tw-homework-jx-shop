package com.superxc.jxshop.service;

import com.superxc.jxshop.entity.Order;
import com.superxc.jxshop.entity.OrderItem;
import com.superxc.jxshop.entity.Product;
import com.superxc.jxshop.repository.OrderItemRepository;
import com.superxc.jxshop.repository.OrderRepository;
import com.superxc.jxshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public Order newOrder(List<OrderItem> orderItems) {
        Order order = new Order();
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
//        order.setLogisticsId(1L);
        order.setStatus("UNPAID");
        order.setUserId(1L);
        order = orderRepository.save(order);

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderId(order.getId());

            Product product = productRepository.getOne(orderItem.getProductId());

            orderItem.setSnapshotName(product.getName());
            orderItem.setSnapshotDescription(product.getDescription());
            orderItem.setSnapshotPrice(product.getPrice());

            orderItemRepository.save(orderItem);
        }
        return order;
    }

}
