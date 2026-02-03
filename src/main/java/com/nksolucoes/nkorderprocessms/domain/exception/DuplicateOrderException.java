package com.nksolucoes.nkorderprocessms.domain.exception;

public class DuplicateOrderException extends DomainException {
    public DuplicateOrderException(String sourceSystem, String externalOrderId) {
        super("Order already exists for sourceSystem=%s and externalOrderId=%s".formatted(sourceSystem, externalOrderId));
    }
}
