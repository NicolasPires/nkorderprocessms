package com.nksolucoes.nkorderprocessms.infrastructure.persistence.mongo.repository;

import com.nksolucoes.nkorderprocessms.infrastructure.persistence.mongo.document.OrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrderMongoRepository extends MongoRepository<OrderDocument, String> {
    Optional<OrderDocument> findBySourceSystemAndExternalOrderId(String sourceSystem, String externalOrderId);
}
