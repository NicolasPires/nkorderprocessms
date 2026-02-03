package com.nksolucoes.nkorderprocessms.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void shouldCalculateTotals() {
        var items = List.of(
                new OrderItem("SKU-1", "Item 1", new BigDecimal("10.50"), 2),
                new OrderItem("SKU-2", "Item 2", new BigDecimal("5.00"), 1)
        );

        var order = Order.create("ECOMMERCE", "ABC-123", items);

        assertEquals(new BigDecimal("26.00"), order.totalAmount());
        assertEquals(3, order.totalItems());
        assertEquals(OrderStatus.CREATED, order.status());
        assertNotNull(order.createdAt());
        assertNotNull(order.id());
    }

    @Test
    void shouldRejectEmptyItems() {
        var ex = assertThrows(IllegalArgumentException.class, () ->
                Order.create("ECOMMERCE", "ABC-123", List.of())
        );
        assertTrue(ex.getMessage().contains("items"));
    }
}
