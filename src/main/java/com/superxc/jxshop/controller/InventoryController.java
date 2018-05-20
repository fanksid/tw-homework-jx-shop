package com.superxc.jxshop.controller;

import com.superxc.jxshop.entity.Inventory;
import com.superxc.jxshop.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class InventoryController {

    @Autowired
    private InventoryRepository inventoryRepository;

    /**
     * update product inventory
     *
     * @param id product id
     * @param newInventory
     * @return
     */
    @PutMapping(value = "/inventories/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Inventory newInventory) {
        Optional<Inventory> optionalInventory = inventoryRepository.findById(id);
        if (optionalInventory.isPresent()) {
            Inventory inventory = optionalInventory.get();
            inventory.setCount(newInventory.getCount());
            inventoryRepository.save(inventory);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
