package com.nksolucoes.nkorderprocessms.domain.exception;

public class OrderNotFoundException extends DomainException {
    public OrderNotFoundException(String id) {
        super("Order not found for id=%s".formatted(id));
    }
}
