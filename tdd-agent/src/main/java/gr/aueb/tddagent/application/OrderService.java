package gr.aueb.tddagent.application;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import gr.aueb.tddagent.api.schemas.request.UpdateOrderStatusDTO;
import gr.aueb.tddagent.api.schemas.response.OrderDTO;
import gr.aueb.tddagent.api.schemas.response.OrdersListDTO;
import gr.aueb.tddagent.domain.Order;
import gr.aueb.tddagent.domain.OrderStatus;
import gr.aueb.tddagent.domain.User;
import gr.aueb.tddagent.exception.custom.NotFoundException;
import gr.aueb.tddagent.persistence.OrderRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrdersListDTO getOrders(User user, OrderStatus status) {
        List<Order> orders = (status != null)
            ? orderRepository.findByUserAndStatusOrderByCreatedAtDesc(user, status)
            : orderRepository.findByUserOrderByCreatedAtDesc(user);

        List<OrderDTO> dtoList = orders.stream()
            .map(this::mapToDTO)
            .toList();

        return new OrdersListDTO(dtoList);
    }

    public OrderDTO updateOrderStatus(UUID orderId, UpdateOrderStatusDTO request) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new NotFoundException("There is no order with id=" + orderId));

        order.setStatus(request.status());
        order = orderRepository.save(order);
        return mapToDTO(order);
    }

    private OrderDTO mapToDTO(Order order) {
        return new OrderDTO(
            order.getId(),
            order.getStatus(),
            order.getTotalAmount(),
            order.getCurrency(),
            order.getCreatedAt(),
            order.getUpdatedAt()
        );
    }
}
