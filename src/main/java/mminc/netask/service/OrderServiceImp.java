package mminc.netask.service;

import lombok.RequiredArgsConstructor;
import mminc.netask.model.*;
import mminc.netask.repository.OrderRepository;
import mminc.netask.repository.ProductRepository;
import mminc.netask.repository.UserRepository;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.method.MethodValidationException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImp implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public Order createOrder(Long userId, List<Long> productIds) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found."));

        List<Product> products = productRepository.findAllById(productIds);

        if (products.isEmpty()) {
            throw new ValidationException("Order must contain at least one product.");
        }

        Order order = new Order();
        order.setUser(user);
        order.setProducts(products);
        order.setStatus(OrderStatus.NEW);

        return orderRepository.save(order);
    }

    @Override
    public Order addProductToOrder(Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId).
                orElseThrow(() -> new ResourceNotFoundException("Order not found."));

        Product product = productRepository.findById(productId).
                orElseThrow(() -> new ResourceNotFoundException("Product not found."));

        if (!order.getProducts().contains(product)) {
            order.getProducts().add(product);
        }

        return orderRepository.save(order);
    }

    @Override
    public Order removeProductFromOrder(Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId).
                orElseThrow(() -> new ResourceNotFoundException("Order not found."));

        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product not found");
        }

        boolean wasRemoved = order.getProducts().removeIf(product -> product.getId().equals(productId));

        if (!wasRemoved) {
            throw new ValidationException("Product not found in order");
        }

        orderRepository.save(order);
    }

    @Override
    public BigDecimal calculateOrderTotal(Long orderId) {
        Order order = orderRepository.findById(orderId).
                orElseThrow(() -> ResourceNotFoundException("Order not found"));

        return order.getProducts().stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Order> getUserOrders(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User with ID" + userId + "not found");
        }

        List<Order> orders = orderRepository.findByUserIdCreatedAtDesc(userId);

        return orders != null ? orders : Collections.emptyList();
    }
}
