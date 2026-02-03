package com.nksolucoes.nkorderprocessms.infrastructure.web.dto.response;

import com.nksolucoes.nkorderprocessms.domain.model.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponse(
        String id,
        String sourceSystem,
        String externalOrderId,
        List<OrderItemResponse> items,
        BigDecimal totalAmount,
        int totalItems,
        OrderStatus status,
        Instant createdAt
) {
}
