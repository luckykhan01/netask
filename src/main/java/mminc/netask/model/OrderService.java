package mminc.netask.model;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    Order createOrder(Long userId, List<Long> productIds);
    Order addProductToOrder(Long orderId, Long productId);
    Order removeProductFromOrder(Long orderId, Long productId);
    Order updateOrderStatus(Long orderId, OrderStatus newStatus);
    Order getOrderById(Long id);
    List<Order> getAllOrders();
    BigDecimal calculateOrderTotal(Long orderId);
    List<Order> getUserOrders(Long userId);
}
