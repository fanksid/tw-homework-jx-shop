package com.superxc.jxshop.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Inventory {
    @Id
    private Long product_id;

    private Long count;

    public Inventory() {
    }

    public Inventory(Long count) {
        this.count = count;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
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
                "product_id=" + product_id +
                ", count=" + count +
                '}';
    }
}
