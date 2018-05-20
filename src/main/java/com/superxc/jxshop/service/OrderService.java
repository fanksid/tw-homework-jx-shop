package com.superxc.jxshop.service;

import com.superxc.jxshop.entity.*;
import com.superxc.jxshop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static com.superxc.jxshop.controller.OrderController.PAID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private LogisticsRepository logisticsRepository;

    @Transactional
    public void pay(Long orderId) {
        Logistics logistics = new Logistics(null, "inbound", null, null);
        logistics = logisticsRepository.save(logistics);

        Order order = orderRepository.getOne(orderId);
        order.setStatus(PAID);
        order.setPayTime(new Timestamp(System.currentTimeMillis()));
        order.setLogisticsId(logistics.getId());

        orderRepository.save(order);
    }

    @Transactional
    public Order newOrder(List<OrderItem> orderItems) {
        Order order = new Order();
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        order.setStatus("unpaid");
        order.setUserId(1L);
        order = orderRepository.save(order);
        double totalPrice = 0;

        for (OrderItem orderItem : orderItems) {

            Product product = productRepository.getOne(orderItem.getProductId());
            Optional<Inventory> optionalInventory = inventoryRepository.findById(product.getId());
            if (!optionalInventory.isPresent()) {
                // 商品不存在
                return null;
            }

            Inventory inventory = optionalInventory.get();
            if (inventory.getCount() < orderItem.getPurchaseCount()) {
                // 购买的数量超过实际库存数量
                return null;
            }

            updateInventory(orderItem, inventory);

            orderItemRepository.save(new OrderItem(order.getId(),
                        orderItem.getProductId(),
                        orderItem.getPurchaseCount(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice()
                    )
            );

            totalPrice += product.getPrice() * orderItem.getPurchaseCount();
        }
        order.setTotalPrice(totalPrice);
        order = orderRepository.save(order);
        return order;
    }

    private void updateInventory(OrderItem orderItem, Inventory inventory) {
        inventory.setCount(inventory.getCount() - orderItem.getPurchaseCount());
        inventory.setCountLock(orderItem.getPurchaseCount());
    }

}
