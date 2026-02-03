package com.nksolucoes.nkorderprocessms.application.usecase;

import com.nksolucoes.nkorderprocessms.application.port.in.CreateOrderInput;
import com.nksolucoes.nkorderprocessms.application.port.in.CreateOrderUseCase;
import com.nksolucoes.nkorderprocessms.application.port.out.OrderRepositoryPort;
import com.nksolucoes.nkorderprocessms.domain.model.Order;

import java.util.Objects;

public class CreateOrderService implements CreateOrderUseCase {

    private final OrderRepositoryPort repository;

    public CreateOrderService(OrderRepositoryPort repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Order execute(CreateOrderInput input) {
        return repository.findByExternalKey(input.sourceSystem(), input.externalOrderId())
                .orElseGet(() -> repository.save(Order.create(input.sourceSystem(), input.externalOrderId(), input.items())));
    }
}
