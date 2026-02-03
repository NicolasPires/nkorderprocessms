package com.nksolucoes.nkorderprocessms.application.usecase;

import com.nksolucoes.nkorderprocessms.application.port.in.CreateOrderInput;
import com.nksolucoes.nkorderprocessms.application.port.out.OrderRepositoryPort;
import com.nksolucoes.nkorderprocessms.domain.model.Order;
import com.nksolucoes.nkorderprocessms.domain.model.OrderItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class CreateOrderServiceTest {

    @Test
    void shouldReturnExistingOrderWhenExternalKeyAlreadyExists() {
        var existing = Order.create("ECOMMERCE", "ABC-123", List.of(new OrderItem("SKU", "Item", BigDecimal.TEN, 1)));

        var repo = new InMemoryRepo()
                .withFindByExternal(Optional.of(existing));

        var service = new CreateOrderService(repo);

        var input = new CreateOrderInput("ECOMMERCE", "ABC-123", existing.items());
        var result = service.execute(input);

        assertEquals(existing.id(), result.id());
        assertEquals(0, repo.saveCount.get());
    }

    @Test
    void shouldCreateNewOrderWhenExternalKeyDoesNotExist() {
        var repo = new InMemoryRepo()
                .withFindByExternal(Optional.empty());

        var service = new CreateOrderService(repo);

        var items = List.of(new OrderItem("SKU", "Item", new BigDecimal("2.50"), 2));
        var input = new CreateOrderInput("ECOMMERCE", "NEW-1", items);

        var result = service.execute(input);

        assertEquals("ECOMMERCE", result.sourceSystem());
        assertEquals("NEW-1", result.externalOrderId());
        assertEquals(new BigDecimal("5.00"), result.totalAmount());
        assertEquals(2, result.totalItems());
        assertEquals(1, repo.saveCount.get());
    }

    static class InMemoryRepo implements OrderRepositoryPort {
        private Optional<Order> findByExternal = Optional.empty();
        final AtomicInteger saveCount = new AtomicInteger();

        InMemoryRepo withFindByExternal(Optional<Order> value) {
            this.findByExternal = value;
            return this;
        }

        @Override
        public Optional<Order> findById(String id) {
            return Optional.empty();
        }

        @Override
        public Optional<Order> findByExternalKey(String sourceSystem, String externalOrderId) {
            return findByExternal;
        }

        @Override
        public Order save(Order order) {
            saveCount.incrementAndGet();
            return order;
        }
    }
}
