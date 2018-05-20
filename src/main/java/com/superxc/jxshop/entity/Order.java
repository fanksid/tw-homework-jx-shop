package com.superxc.jxshop.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "jxOrder") // order是mysql的关键字，故加上jx前缀
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    private Timestamp createTime;

    private Timestamp payTime;

    private Long logisticsId;

    private Long userId;

    private Timestamp cancelTime;

    private Timestamp finishTime;

    private Double totalPrice;

    public Timestamp getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "orderId")
    private List<OrderItem> purchaseItemList = new ArrayList<>();

    public List<OrderItem> getPurchaseItemList() {
        return purchaseItemList;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Order() {
    }

    public Order(String status, Timestamp createTime, Timestamp payTime, Long logisticsId, Long userId, Timestamp cancelTime, Timestamp finishTime, Double totalPrice) {
        this.status = status;
        this.createTime = createTime;
        this.payTime = payTime;
        this.logisticsId = logisticsId;
        this.userId = userId;
        this.cancelTime = cancelTime;
        this.finishTime = finishTime;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getPayTime() {
        return payTime;
    }

    public void setPayTime(Timestamp payTime) {
        this.payTime = payTime;
    }

    public Long getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(Long logisticsId) {
        this.logisticsId = logisticsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Timestamp getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Timestamp cancelTime) {
        this.cancelTime = cancelTime;
    }
}
