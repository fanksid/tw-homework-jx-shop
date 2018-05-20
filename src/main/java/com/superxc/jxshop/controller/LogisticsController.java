package com.superxc.jxshop.controller;

import com.superxc.jxshop.entity.Logistics;
import com.superxc.jxshop.entity.Order;
import com.superxc.jxshop.repository.LogisticsRepository;
import com.superxc.jxshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Optional;

@RestController
@RequestMapping(value = "/logisticsRecords")
public class LogisticsController {

    private static final String SHIPPING = "shipping";
    private static final String SIGNED = "signed";
    public static final String FINISH = "finish";

    @Autowired
    private LogisticsRepository logisticsRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping(value = "/{id}")
    public Logistics get(@PathVariable Long id) {
        Optional<Logistics> optionalLogistics = logisticsRepository.findById(id);
        if (optionalLogistics.isPresent()) {
            return optionalLogistics.get();
        }
        return null;
    }


    @PutMapping(value = "/{logisticsId}/orders/{orderId}")
    public ResponseEntity<String> updateStatus(@PathVariable Long logisticsId,
                                               @PathVariable Long orderId,
                                               @RequestParam String logisticsStatus) {
        try {
            switch (logisticsStatus) {
                case SHIPPING:
                    setLogisticsStatusToShippingAndUpdateOutboundTime(logisticsId);
                    break;
                case SIGNED:
                    setLogisticsStatusToSignedAndUpdateSignedTime(logisticsId);
                    setOrderStatusToFinishAndUpdateFinishTime(orderId);
                    // TODO: 减少真实库存
                    break;
                default:
                    throw new Exception("unsupported status");
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void setOrderStatusToFinishAndUpdateFinishTime(@PathVariable Long orderId) {
        Order order = orderRepository.getOne(orderId);
        order.setStatus(FINISH);
        order.setFinishTime(getNowTimeStamp());
        orderRepository.save(order);
    }

    private Timestamp getNowTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    private void setLogisticsStatusToSignedAndUpdateSignedTime(@PathVariable Long logisticsId) {
        Logistics logistics = logisticsRepository.getOne(logisticsId);
        logistics.setStatus(SIGNED);
        logistics.setSignedTime(getNowTimeStamp());
        logisticsRepository.save(logistics);
    }

    private void setLogisticsStatusToShippingAndUpdateOutboundTime(@PathVariable Long logisticsId) {
        Logistics logistics = logisticsRepository.getOne(logisticsId);
        logistics.setStatus(SHIPPING);
        logistics.setOutboundTime(getNowTimeStamp());
        logisticsRepository.save(logistics);
    }
}
