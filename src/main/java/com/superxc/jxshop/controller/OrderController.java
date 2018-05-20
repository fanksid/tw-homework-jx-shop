package com.superxc.jxshop.controller;

import com.superxc.jxshop.entity.Inventory;
import com.superxc.jxshop.entity.Logistics;
import com.superxc.jxshop.entity.Order;
import com.superxc.jxshop.entity.OrderItem;
import com.superxc.jxshop.repository.InventoryRepository;
import com.superxc.jxshop.repository.LogisticsRepository;
import com.superxc.jxshop.repository.OrderRepository;
import com.superxc.jxshop.service.InventoryService;
import com.superxc.jxshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    public static final String PAID = "paid";
    private static final String WITHDRAWN = "withdrawn";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private LogisticsRepository logisticsRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryService inventoryService;

    /**
     * create a order
     *
     * @param orderItems
     * @return
     */
    @PostMapping()
    public ResponseEntity<String> add(@RequestBody List<OrderItem> orderItems) {
        Order order = orderService.newOrder(orderItems);
        if (order == null) {
            return new ResponseEntity<String>("purchase failed", HttpStatus.BAD_REQUEST);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("location", "http://localhost:8083/orders/" + order.getId());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    /**
     * get a order
     *
     * @param id order id
     * @return order
     */
    @GetMapping(value = "/{id}")
    public Order get(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            return optionalOrder.get();
        }
        return null;
    }

    /**
     * update order status
     *
     * @param id order id
     * @param orderStatus new status
     * @return
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestParam String orderStatus) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            switch (orderStatus) {
                case PAID:
                    orderService.pay(id);
                    break;
                case WITHDRAWN:
                    order.setStatus(WITHDRAWN);
                    order.setCancelTime(new Timestamp(System.currentTimeMillis()));
                    orderRepository.save(order);

                    unlockInventory(order);
                    break;
            }

            return new ResponseEntity<String>(HttpStatus.OK);
        }
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }

    private void unlockInventory(Order order) {
        for (OrderItem orderItem : order.getPurchaseItemList()) {
            Optional<Inventory> optionalInventory = inventoryRepository.findById(orderItem.getProductId());
            if (optionalInventory.isPresent()) {
                inventoryService.unlockCount(optionalInventory.get(), orderItem.getPurchaseCount());
            }
        }
    }

    /**
     * get orders by user id
     * @param userId
     * @return
     */
    @GetMapping
    public List<Order> getOrdersByUserId(@RequestParam Long userId) {
        return orderRepository.findAllByUserId(userId);
    }
}
