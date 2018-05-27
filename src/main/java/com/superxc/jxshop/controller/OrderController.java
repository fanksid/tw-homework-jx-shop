package com.superxc.jxshop.controller;

import com.superxc.jxshop.entity.Order;
import com.superxc.jxshop.entity.OrderItem;
import com.superxc.jxshop.repository.OrderRepository;
import com.superxc.jxshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    public static final String PAID = "paid";
    public static final String WITHDRAWN = "withdrawn";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Value("${app.host}")
    private String host;

    @Value("${app.port}")
    private String port;

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
            return new ResponseEntity<>("purchase failed", HttpStatus.BAD_REQUEST);
        }
        HttpHeaders headers = new HttpHeaders();

        headers.add("location", String.format("http://%s:%s/orders/%d", host, port, order.getId()));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
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
        return optionalOrder.orElse(null);
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
            switch (orderStatus) {
                case PAID:
                    orderService.pay(id);
                    break;
                case WITHDRAWN:
                    orderService.withdrawn(id);
                    break;
            }

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
