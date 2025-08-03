package gr.aueb.tddagent.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import gr.aueb.tddagent.domain.Order;
import gr.aueb.tddagent.domain.OrderStatus;
import gr.aueb.tddagent.domain.User;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByStatusOrderByCreatedAtDesc(OrderStatus status);
    List<Order> findByUserOrderByCreatedAtDesc(User user);
    List<Order> findByUserAndStatusOrderByCreatedAtDesc(User user, OrderStatus status);
}
