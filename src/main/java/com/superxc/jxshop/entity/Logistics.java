package com.superxc.jxshop.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Logistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp outboundTime;

    private String status;

    private Timestamp signedTime;

    private String deliveryMan;

    public Logistics() {
    }

    public Logistics(Timestamp outboundTime, String status, Timestamp signedTime, String deliveryMan) {
        this.outboundTime = outboundTime;
        this.status = status;
        this.signedTime = signedTime;
        this.deliveryMan = deliveryMan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(Timestamp outboundTime) {
        this.outboundTime = outboundTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getSignedTime() {
        return signedTime;
    }

    public void setSignedTime(Timestamp signedTime) {
        this.signedTime = signedTime;
    }

    public String getDeliveryMan() {
        return deliveryMan;
    }

    public void setDeliveryMan(String deliveryMan) {
        this.deliveryMan = deliveryMan;
    }
}
