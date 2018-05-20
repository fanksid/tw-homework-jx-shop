package com.superxc.jxshop.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private Long productId;

    private Long purchaseCount;

    private String snapshotName;

    private String snapshotDescription;

    private Double snapshotPrice;

    public OrderItem() {
    }

    public OrderItem(Long productId, Long purchaseCount) {
        this.productId = productId;
        this.purchaseCount = purchaseCount;
    }

    public OrderItem(Long orderId, Long productId, Long purchaseCount, String snapshotName, String snapshotDescription, Double snapshotPrice) {
        this.orderId = orderId;
        this.productId = productId;
        this.purchaseCount = purchaseCount;
        this.snapshotName = snapshotName;
        this.snapshotDescription = snapshotDescription;
        this.snapshotPrice = snapshotPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(Long purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public String getSnapshotName() {
        return snapshotName;
    }

    public void setSnapshotName(String snapshotName) {
        this.snapshotName = snapshotName;
    }

    public String getSnapshotDescription() {
        return snapshotDescription;
    }

    public void setSnapshotDescription(String snapshotDescription) {
        this.snapshotDescription = snapshotDescription;
    }

    public Double getSnapshotPrice() {
        return snapshotPrice;
    }

    public void setSnapshotPrice(Double snapshotPrice) {
        this.snapshotPrice = snapshotPrice;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", purchaseCount=" + purchaseCount +
                ", snapshotName='" + snapshotName + '\'' +
                ", snapshotDescription='" + snapshotDescription + '\'' +
                ", snapshotPrice=" + snapshotPrice +
                '}';
    }
}
