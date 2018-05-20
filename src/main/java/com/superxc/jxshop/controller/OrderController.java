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
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/orders")
    public ResponseEntity<String> add(@RequestBody List<OrderItem> orderItems) {
//        orderItems.forEach(System.out::println);
        Order order = orderService.newOrder(orderItems);
        HttpHeaders headers = new HttpHeaders();
        headers.add("location", "http://localhost:8083/orders/" + order.getId());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = "/orders/{id}")
    public Order get(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            return optionalOrder.get();
        }
        return null;
    }
}
