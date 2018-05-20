package com.superxc.jxshop.service;

import com.superxc.jxshop.entity.Inventory;
import com.superxc.jxshop.entity.Order;
import com.superxc.jxshop.entity.OrderItem;
import com.superxc.jxshop.entity.Product;
import com.superxc.jxshop.repository.InventoryRepository;
import com.superxc.jxshop.repository.OrderItemRepository;
import com.superxc.jxshop.repository.OrderRepository;
import com.superxc.jxshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

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
