package com.nksolucoes.nkorderprocessms.infrastructure.web.dto.response;

import java.math.BigDecimal;

public record OrderItemResponse(
        String sku,
        String name,
        BigDecimal unitPrice,
        int quantity,
        BigDecimal total
) {
}
