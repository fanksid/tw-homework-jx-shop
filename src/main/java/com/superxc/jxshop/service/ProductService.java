package com.superxc.jxshop.service;

import com.superxc.jxshop.entity.Product;
import com.superxc.jxshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Product add(Product product) {
        Product addedProduct = productRepository.save(product);
        // TODO: 创建对应商品的库存为0
        return addedProduct;
    }
}
