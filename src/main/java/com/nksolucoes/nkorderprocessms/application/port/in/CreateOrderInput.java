package com.nksolucoes.nkorderprocessms.application.port.in;

import com.nksolucoes.nkorderprocessms.domain.model.OrderItem;

import java.util.List;
import java.util.Objects;

public record CreateOrderInput(
        String sourceSystem,
        String externalOrderId,
        List<OrderItem> items
) {
    public CreateOrderInput {
        Objects.requireNonNull(sourceSystem, "sourceSystem must not be null");
        Objects.requireNonNull(externalOrderId, "externalOrderId must not be null");
        Objects.requireNonNull(items, "items must not be null");
        if (sourceSystem.isBlank()) throw new IllegalArgumentException("sourceSystem must not be blank");
        if (externalOrderId.isBlank()) throw new IllegalArgumentException("externalOrderId must not be blank");
        if (items.isEmpty()) throw new IllegalArgumentException("items must not be empty");
    }
}
