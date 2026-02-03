package com.nksolucoes.nkorderprocessms.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public record Order(
        String id,
        String sourceSystem,
        String externalOrderId,
        List<OrderItem> items,
        BigDecimal totalAmount,
        int totalItems,
        OrderStatus status,
        Instant createdAt
) {
    public Order {
        Objects.requireNonNull(sourceSystem, "sourceSystem must not be null");
        Objects.requireNonNull(externalOrderId, "externalOrderId must not be null");
        Objects.requireNonNull(items, "items must not be null");
        Objects.requireNonNull(totalAmount, "totalAmount must not be null");
        Objects.requireNonNull(status, "status must not be null");
        Objects.requireNonNull(createdAt, "createdAt must not be null");
        if (sourceSystem.isBlank()) throw new IllegalArgumentException("sourceSystem must not be blank");
        if (externalOrderId.isBlank()) throw new IllegalArgumentException("externalOrderId must not be blank");
        if (items.isEmpty()) throw new IllegalArgumentException("items must not be empty");
        if (totalAmount.signum() < 0) throw new IllegalArgumentException("totalAmount must be >= 0");
        if (totalItems <= 0) throw new IllegalArgumentException("totalItems must be > 0");
    }

    public static Order create(String sourceSystem, String externalOrderId, List<OrderItem> items) {
        Objects.requireNonNull(items, "items must not be null");
        var totalAmount = items.stream()
                .map(OrderItem::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var totalItems = items.stream()
                .mapToInt(OrderItem::quantity)
                .sum();

        return new Order(
                UUID.randomUUID().toString(),
                sourceSystem,
                externalOrderId,
                List.copyOf(items),
                totalAmount,
                totalItems,
                OrderStatus.CREATED,
                Instant.now()
        );
    }
}
