package com.eva.evolution.repository;

import com.eva.evolution.entity.UserTransactionEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.UUID;

public interface UserTransactionRepository extends ReactiveCrudRepository<UserTransactionEntity, UUID> {
   Flux<UserTransactionEntity> findAllByTransactionIdIn(Collection<String> transactionIds);
}
