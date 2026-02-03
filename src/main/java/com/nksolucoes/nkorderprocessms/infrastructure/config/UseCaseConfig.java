package com.nksolucoes.nkorderprocessms.infrastructure.config;

import com.nksolucoes.nkorderprocessms.application.port.in.CreateOrderUseCase;
import com.nksolucoes.nkorderprocessms.application.port.in.GetOrderUseCase;
import com.nksolucoes.nkorderprocessms.application.port.out.OrderRepositoryPort;
import com.nksolucoes.nkorderprocessms.application.usecase.CreateOrderService;
import com.nksolucoes.nkorderprocessms.application.usecase.GetOrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CreateOrderUseCase createOrderUseCase(OrderRepositoryPort repository) {
        return new CreateOrderService(repository);
    }

    @Bean
    public GetOrderUseCase getOrderUseCase(OrderRepositoryPort repository) {
        return new GetOrderService(repository);
    }
}
