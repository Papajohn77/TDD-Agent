package gr.aueb.tddagent.api.schemas.response;

import java.util.List;

public record OrdersListDTO(
    List<OrderDTO> orders
) {}
