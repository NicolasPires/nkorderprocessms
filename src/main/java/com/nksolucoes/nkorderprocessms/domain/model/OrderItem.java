package com.nksolucoes.nkorderprocessms.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public record OrderItem(
        String sku,
        String name,
        BigDecimal unitPrice,
        int quantity
) {
    public OrderItem {
        Objects.requireNonNull(sku, "sku must not be null");
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(unitPrice, "unitPrice must not be null");
        if (sku.isBlank()) throw new IllegalArgumentException("sku must not be blank");
        if (name.isBlank()) throw new IllegalArgumentException("name must not be blank");
        if (unitPrice.signum() < 0) throw new IllegalArgumentException("unitPrice must be >= 0");
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be > 0");
    }

    public BigDecimal total() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
