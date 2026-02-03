package com.nksolucoes.nkorderprocessms.infrastructure.web.controller;

import com.nksolucoes.nkorderprocessms.application.port.in.CreateOrderUseCase;
import com.nksolucoes.nkorderprocessms.application.port.in.GetOrderUseCase;
import com.nksolucoes.nkorderprocessms.infrastructure.web.dto.request.CreateOrderRequest;
import com.nksolucoes.nkorderprocessms.infrastructure.web.dto.response.OrderResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase, GetOrderUseCase getOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.getOrderUseCase = getOrderUseCase;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody CreateOrderRequest request) {
        var created = createOrderUseCase.execute(OrderMapper.toInput(request));
        return ResponseEntity.created(URI.create("/orders/" + created.id())).body(OrderMapper.toResponse(created));
    }

    @GetMapping("/{id}")
    public OrderResponse getById(@PathVariable String id) {
        return OrderMapper.toResponse(getOrderUseCase.byId(id));
    }

    @GetMapping("/by-external")
    public OrderResponse getByExternal(@RequestParam String sourceSystem, @RequestParam String externalOrderId) {
        return OrderMapper.toResponse(getOrderUseCase.byExternalKey(sourceSystem, externalOrderId));
    }
}
