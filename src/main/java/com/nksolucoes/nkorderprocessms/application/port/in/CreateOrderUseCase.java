package com.nksolucoes.nkorderprocessms.application.port.in;

import com.nksolucoes.nkorderprocessms.domain.model.Order;

public interface CreateOrderUseCase {
    Order execute(CreateOrderInput input);
}
