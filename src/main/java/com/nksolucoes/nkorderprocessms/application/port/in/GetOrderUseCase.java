package com.nksolucoes.nkorderprocessms.application.port.in;

import com.nksolucoes.nkorderprocessms.domain.model.Order;

public interface GetOrderUseCase {
    Order byId(String id);
    Order byExternalKey(String sourceSystem, String externalOrderId);
}
