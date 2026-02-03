package com.nksolucoes.nkorderprocessms.application.port.out;

import com.nksolucoes.nkorderprocessms.domain.model.Order;

import java.util.Optional;

public interface OrderRepositoryPort {
    Optional<Order> findById(String id);
    Optional<Order> findByExternalKey(String sourceSystem, String externalOrderId);
    Order save(Order order);
}
