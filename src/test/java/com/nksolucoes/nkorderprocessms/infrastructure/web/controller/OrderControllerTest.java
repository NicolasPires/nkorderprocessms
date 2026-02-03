package com.nksolucoes.nkorderprocessms.infrastructure.web.controller;

import com.nksolucoes.nkorderprocessms.application.port.in.CreateOrderUseCase;
import com.nksolucoes.nkorderprocessms.application.port.in.GetOrderUseCase;
import com.nksolucoes.nkorderprocessms.domain.model.Order;
import com.nksolucoes.nkorderprocessms.domain.model.OrderItem;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = OrderController.class,
        excludeAutoConfiguration = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class}
)
class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private CreateOrderUseCase createOrderUseCase;

    @MockitoBean
    private GetOrderUseCase getOrderUseCase;

    @Test
    void shouldCreateOrder() throws Exception {
        var order = Order.create("ECOMMERCE", "ABC-123", List.of(new OrderItem("SKU", "Item", BigDecimal.TEN, 1)));
        Mockito.when(createOrderUseCase.execute(any())).thenReturn(order);

        var payload = """
                {
                  "sourceSystem": "ECOMMERCE",
                  "externalOrderId": "ABC-123",
                  "items": [
                    { "sku": "SKU", "name": "Item", "unitPrice": 10.00, "quantity": 1 }
                  ]
                }
                """;

        mvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/orders/" + order.id()))
                .andExpect(jsonPath("$.id").value(order.id()))
                .andExpect(jsonPath("$.sourceSystem").value("ECOMMERCE"))
                .andExpect(jsonPath("$.externalOrderId").value("ABC-123"));
    }
}
