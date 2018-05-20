package com.superxc.jxshop.controller;

import com.superxc.jxshop.entity.Order;
import com.superxc.jxshop.entity.OrderItem;
import com.superxc.jxshop.repository.OrderRepository;
import com.superxc.jxshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @PostMapping()
    public ResponseEntity<String> add(@RequestBody List<OrderItem> orderItems) {
        Order order = orderService.newOrder(orderItems);
        HttpHeaders headers = new HttpHeaders();
        headers.add("location", "http://localhost:8083/orders/" + order.getId());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public Order get(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            return optionalOrder.get();
        }
        return null;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestParam String orderStatus) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            // TODO: 这里应当校验输入的status的合法性
            order.setStatus(orderStatus);
            orderRepository.save(order);
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }
}
