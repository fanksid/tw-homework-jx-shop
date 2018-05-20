package com.superxc.jxshop.service;

import com.superxc.jxshop.entity.Inventory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

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


}
