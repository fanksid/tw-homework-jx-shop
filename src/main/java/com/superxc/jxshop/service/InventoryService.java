package com.superxc.jxshop.service;

import com.superxc.jxshop.entity.Inventory;
import com.superxc.jxshop.entity.Order;
import com.superxc.jxshop.entity.OrderItem;
import com.superxc.jxshop.repository.InventoryRepository;
import com.superxc.jxshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public void lockCount(Inventory inventory, long num) {
        assert inventory.getCount() >= num;

        inventory.setCountLock(inventory.getCountLock() + num);
        inventory.setCount(inventory.getCount() - num);
    }

    @Transactional
    public void unlockCount(Inventory inventory, long num) {
        assert  inventory.getCountLock() >= num;

        inventory.setCountLock(inventory.getCountLock() - num);
        inventory.setCount(inventory.getCount() + num);
    }

    @Transactional
    public void removeLockCount(Long orderId) {

        for (OrderItem orderItem : orderRepository.getOne(orderId).getPurchaseItemList()) {
            Inventory inventory = inventoryRepository.getOne(orderItem.getProductId());
            assert inventory.getCountLock() >= orderItem.getPurchaseCount();
            inventory.setCountLock(inventory.getCountLock() - orderItem.getPurchaseCount());
            inventoryRepository.save(inventory);
        }

    }
}
