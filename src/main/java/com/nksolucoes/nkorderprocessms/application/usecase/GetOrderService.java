package com.nksolucoes.nkorderprocessms.application.usecase;

import com.nksolucoes.nkorderprocessms.application.port.in.GetOrderUseCase;
import com.nksolucoes.nkorderprocessms.application.port.out.OrderRepositoryPort;
import com.nksolucoes.nkorderprocessms.domain.exception.OrderNotFoundException;
import com.nksolucoes.nkorderprocessms.domain.model.Order;

import java.util.Objects;

public class GetOrderService implements GetOrderUseCase {

    private final OrderRepositoryPort repository;

    public GetOrderService(OrderRepositoryPort repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Order byId(String id) {
        return repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public Order byExternalKey(String sourceSystem, String externalOrderId) {
        return repository.findByExternalKey(sourceSystem, externalOrderId)
                .orElseThrow(() -> new OrderNotFoundException("%s:%s".formatted(sourceSystem, externalOrderId)));
    }
}
