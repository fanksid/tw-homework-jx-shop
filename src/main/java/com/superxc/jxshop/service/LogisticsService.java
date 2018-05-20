package com.superxc.jxshop.service;

import com.superxc.jxshop.entity.Logistics;
import com.superxc.jxshop.entity.Order;
import com.superxc.jxshop.repository.LogisticsRepository;
import com.superxc.jxshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Timestamp;

import static com.superxc.jxshop.controller.LogisticsController.SIGNED;


@Service
public class LogisticsService {
    private static final String FINISH = "finish";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private LogisticsRepository logisticsRepository;

    @Autowired
    private InventoryService inventoryService;

    @Transactional
    public void signed(Long logisticsId, Long orderId) {
        setLogisticsStatusToSignedAndUpdateSignedTime(logisticsId);
        setOrderStatusToFinishAndUpdateFinishTime(orderId);

        inventoryService.removeLockCount(orderId);
    }

    private void setOrderStatusToFinishAndUpdateFinishTime(@PathVariable Long orderId) {
        Order order = orderRepository.getOne(orderId);
        order.setStatus(FINISH);
        order.setFinishTime(getNowTimeStamp());
        orderRepository.save(order);
    }

    private void setLogisticsStatusToSignedAndUpdateSignedTime(@PathVariable Long logisticsId) {
        Logistics logistics = logisticsRepository.getOne(logisticsId);
        logistics.setStatus(SIGNED);
        logistics.setSignedTime(getNowTimeStamp());
        logisticsRepository.save(logistics);
    }

    private Timestamp getNowTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
