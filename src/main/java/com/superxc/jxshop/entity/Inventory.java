package com.superxc.jxshop.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Inventory {
    @Id
    private Long productId;

    private Long count;

    public Inventory() {
    }

    public Inventory(Long count) {
        this.count = count;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "productId=" + productId +
                ", count=" + count +
                '}';
    }
}
