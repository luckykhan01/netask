package mminc.netask.controller;

import lombok.RequiredArgsConstructor;
import mminc.netask.model.Order;
import mminc.netask.model.OrderService;
import mminc.netask.model.OrderStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestParam Long userId,
            @RequestBody List<Long> productIds) {
        Order order = orderService.createOrder(userId, productIds);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @PostMapping("/{orderId}/products/{productId}")
    public ResponseEntity<Order> addProductToOrder(
            @PathVariable Long orderId,
            @PathVariable Long productId) {
        Order order = orderService.addProductToOrder(orderId, productId);
        return ResponseEntity.ok(order);
    }


    @DeleteMapping("/{orderId}/products/{productId}")
    public ResponseEntity<Order> removeProductFromOrder(
            @PathVariable Long orderId,
            @PathVariable Long productId) {
        Order order = orderService.removeProductFromOrder(orderId, productId);
        return ResponseEntity.ok(order);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus orderStatus) {
        Order order = orderService.updateOrderStatus(orderId, orderStatus);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}/total")
    public ResponseEntity<BigDecimal> calculateOrderTotal(
            @PathVariable Long orderId) {
        BigDecimal total = orderService.calculateOrderTotal(orderId);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(
            @PathVariable Long userId) {
        List<Order> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(
            @PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List <Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

}
