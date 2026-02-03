package com.nksolucoes.nkorderprocessms.infrastructure.persistence.mongo.document;

import com.nksolucoes.nkorderprocessms.domain.model.OrderStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Document(collection = "orders")
@CompoundIndex(name = "uk_source_external", def = "{'sourceSystem': 1, 'externalOrderId': 1}", unique = true)
public record OrderDocument(
        @Id String id,
        String sourceSystem,
        String externalOrderId,
        List<OrderItemDocument> items,
        BigDecimal totalAmount,
        int totalItems,
        OrderStatus status,
        Instant createdAt
) {
}
