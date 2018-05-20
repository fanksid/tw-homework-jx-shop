package com.superxc.jxshop.controller;

import com.superxc.jxshop.entity.Logistics;
import com.superxc.jxshop.repository.LogisticsRepository;
import com.superxc.jxshop.service.LogisticsService;
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
    public static final String SIGNED = "signed";

    @Autowired
    private LogisticsRepository logisticsRepository;

    @Autowired
    private LogisticsService logisticsService;

    /**
     * get logistics by id
     *
     * @param id logistics id
     * @return
     */
    @GetMapping(value = "/{id}")
    public Logistics get(@PathVariable Long id) {
        Optional<Logistics> optionalLogistics = logisticsRepository.findById(id);
        if (optionalLogistics.isPresent()) {
            return optionalLogistics.get();
        }
        return null;
    }

    /**
     * update logistics status and order status
     *
     * @param logisticsId
     * @param orderId
     * @param logisticsStatus
     * @return
     */
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
                    logisticsService.signed(logisticsId, orderId);
                    break;
                default:
                    throw new Exception("unsupported status");
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private Timestamp getNowTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    private void setLogisticsStatusToShippingAndUpdateOutboundTime(@PathVariable Long logisticsId) {
        Logistics logistics = logisticsRepository.getOne(logisticsId);
        logistics.setStatus(SHIPPING);
        logistics.setOutboundTime(getNowTimeStamp());
        logisticsRepository.save(logistics);
    }
}
