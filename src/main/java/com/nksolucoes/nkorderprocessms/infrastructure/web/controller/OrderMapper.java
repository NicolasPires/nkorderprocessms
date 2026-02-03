package com.nksolucoes.nkorderprocessms.infrastructure.web.controller;

import com.nksolucoes.nkorderprocessms.application.port.in.CreateOrderInput;
import com.nksolucoes.nkorderprocessms.domain.model.Order;
import com.nksolucoes.nkorderprocessms.domain.model.OrderItem;
import com.nksolucoes.nkorderprocessms.infrastructure.web.dto.request.CreateOrderRequest;
import com.nksolucoes.nkorderprocessms.infrastructure.web.dto.response.OrderItemResponse;
import com.nksolucoes.nkorderprocessms.infrastructure.web.dto.response.OrderResponse;

public final class OrderMapper {

    private OrderMapper() {
    }

    public static CreateOrderInput toInput(CreateOrderRequest request) {
        var items = request.items().stream()
                .map(i -> new OrderItem(i.sku(), i.name(), i.unitPrice(), i.quantity()))
                .toList();

        return new CreateOrderInput(request.sourceSystem(), request.externalOrderId(), items);
    }

    public static OrderResponse toResponse(Order order) {
        var items = order.items().stream()
                .map(i -> new OrderItemResponse(i.sku(), i.name(), i.unitPrice(), i.quantity(), i.total()))
                .toList();

        return new OrderResponse(
                order.id(),
                order.sourceSystem(),
                order.externalOrderId(),
                items,
                order.totalAmount(),
                order.totalItems(),
                order.status(),
                order.createdAt()
        );
    }
}
