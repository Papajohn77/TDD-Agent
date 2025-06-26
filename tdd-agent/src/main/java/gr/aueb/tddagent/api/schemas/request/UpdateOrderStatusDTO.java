package gr.aueb.tddagent.api.schemas.request;

import gr.aueb.tddagent.domain.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusDTO(
    @NotNull(message = "Status is required")
    OrderStatus status
) {}
