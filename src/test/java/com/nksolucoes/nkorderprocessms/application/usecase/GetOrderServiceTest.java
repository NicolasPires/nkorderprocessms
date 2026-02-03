package com.nksolucoes.nkorderprocessms.application.usecase;

import com.nksolucoes.nkorderprocessms.application.port.out.OrderRepositoryPort;
import com.nksolucoes.nkorderprocessms.domain.exception.OrderNotFoundException;
import com.nksolucoes.nkorderprocessms.domain.model.Order;
import com.nksolucoes.nkorderprocessms.domain.model.OrderItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GetOrderServiceTest {

    @Test
    void shouldThrowWhenNotFoundById() {
        var service = new GetOrderService(new Repo(Optional.empty(), Optional.empty()));

        assertThrows(OrderNotFoundException.class, () -> service.byId("x"));
    }

    @Test
    void shouldReturnOrderById() {
        var order = Order.create("ECOMMERCE", "ABC-123", List.of(new OrderItem("SKU", "Item", BigDecimal.TEN, 1)));
        var service = new GetOrderService(new Repo(Optional.of(order), Optional.empty()));

        var found = service.byId(order.id());

        assertEquals(order.id(), found.id());
    }

    @Test
    void shouldReturnOrderByExternalKey() {
        var order = Order.create("ECOMMERCE", "ABC-123", List.of(new OrderItem("SKU", "Item", BigDecimal.TEN, 1)));
        var service = new GetOrderService(new Repo(Optional.empty(), Optional.of(order)));

        var found = service.byExternalKey("ECOMMERCE", "ABC-123");

        assertEquals(order.externalOrderId(), found.externalOrderId());
    }

    static class Repo implements OrderRepositoryPort {
        private final Optional<Order> byId;
        private final Optional<Order> byExternal;

        Repo(Optional<Order> byId, Optional<Order> byExternal) {
            this.byId = byId;
            this.byExternal = byExternal;
        }

        @Override
        public Optional<Order> findById(String id) {
            return byId;
        }

        @Override
        public Optional<Order> findByExternalKey(String sourceSystem, String externalOrderId) {
            return byExternal;
        }

        @Override
        public Order save(Order order) {
            return order;
        }
    }
}
