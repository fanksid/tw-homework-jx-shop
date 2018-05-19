package com.superxc.jxshop.repository;

import com.superxc.jxshop.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{
}
