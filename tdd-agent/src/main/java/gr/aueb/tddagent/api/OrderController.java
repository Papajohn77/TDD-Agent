package gr.aueb.tddagent.api;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gr.aueb.tddagent.api.schemas.request.UpdateOrderStatusDTO;
import gr.aueb.tddagent.api.schemas.response.OrderDTO;
import gr.aueb.tddagent.api.schemas.response.OrdersListDTO;
import gr.aueb.tddagent.application.OrderService;
import gr.aueb.tddagent.domain.OrderStatus;
import gr.aueb.tddagent.domain.User;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<OrdersListDTO> getOrders(
        @RequestParam(required = false) OrderStatus status,
        Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        OrdersListDTO orders = orderService.getOrders(user, status);
        return ResponseEntity.ok(orders);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
        @PathVariable UUID orderId,
        @RequestBody UpdateOrderStatusDTO updateRequest
    ) {
        OrderDTO updated = orderService.updateOrderStatus(orderId, updateRequest);
        return ResponseEntity.ok(updated);
    }
}
