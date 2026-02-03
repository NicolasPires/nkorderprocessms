package com.nksolucoes.nkorderprocessms.infrastructure.persistence.mongo.adapter;

import com.nksolucoes.nkorderprocessms.application.port.out.OrderRepositoryPort;
import com.nksolucoes.nkorderprocessms.domain.model.Order;
import com.nksolucoes.nkorderprocessms.domain.model.OrderItem;
import com.nksolucoes.nkorderprocessms.infrastructure.persistence.mongo.document.OrderDocument;
import com.nksolucoes.nkorderprocessms.infrastructure.persistence.mongo.document.OrderItemDocument;
import com.nksolucoes.nkorderprocessms.infrastructure.persistence.mongo.repository.OrderMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MongoOrderRepositoryAdapter implements OrderRepositoryPort {

    private final OrderMongoRepository repository;

    public MongoOrderRepositoryAdapter(OrderMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Order> findById(String id) {
        return repository.findById(id).map(MongoOrderRepositoryAdapter::toDomain);
    }

    @Override
    public Optional<Order> findByExternalKey(String sourceSystem, String externalOrderId) {
        return repository.findBySourceSystemAndExternalOrderId(sourceSystem, externalOrderId)
                .map(MongoOrderRepositoryAdapter::toDomain);
    }

    @Override
    public Order save(Order order) {
        var saved = repository.save(toDocument(order));
        return toDomain(saved);
    }

    private static OrderDocument toDocument(Order order) {
        var items = order.items().stream()
                .map(i -> new OrderItemDocument(i.sku(), i.name(), i.unitPrice(), i.quantity()))
                .toList();

        return new OrderDocument(
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

    private static Order toDomain(OrderDocument doc) {
        var items = doc.items().stream()
                .map(i -> new OrderItem(i.sku(), i.name(), i.unitPrice(), i.quantity()))
                .toList();

        return new Order(
                doc.id(),
                doc.sourceSystem(),
                doc.externalOrderId(),
                items,
                doc.totalAmount(),
                doc.totalItems(),
                doc.status(),
                doc.createdAt()
        );
    }
}
