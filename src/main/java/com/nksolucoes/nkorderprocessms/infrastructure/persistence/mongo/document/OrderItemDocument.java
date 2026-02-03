package com.nksolucoes.nkorderprocessms.infrastructure.persistence.mongo.document;

import java.math.BigDecimal;

public record OrderItemDocument(
        String sku,
        String name,
        BigDecimal unitPrice,
        int quantity
) {
}
