package com.superxc.jxshop.service;

import com.superxc.jxshop.entity.Inventory;
import com.superxc.jxshop.entity.Product;
import com.superxc.jxshop.repository.InventoryRepository;
import com.superxc.jxshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Transactional
    public Product add(Product product) {
        Product addedProduct = productRepository.save(product);

        setInventory0(addedProduct);

        return addedProduct;
    }

    private void setInventory0(Product addedProduct) {
        Inventory inventory = new Inventory();
        inventory.setProductId(addedProduct.getId());
        inventory.setCount(0L);
        inventoryRepository.save(inventory);
    }
}
