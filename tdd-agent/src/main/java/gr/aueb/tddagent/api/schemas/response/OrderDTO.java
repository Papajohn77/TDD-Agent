package gr.aueb.tddagent.api.schemas.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import gr.aueb.tddagent.domain.OrderStatus;

public record OrderDTO(
    UUID id,
    OrderStatus status,
    @JsonProperty("total_amount") BigDecimal totalAmount,
    String currency,
    @JsonProperty("created_at") Instant createdAt,
    @JsonProperty("updated_at") Instant updatedAt
) {}
