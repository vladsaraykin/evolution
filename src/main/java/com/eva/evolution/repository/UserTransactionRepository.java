package com.eva.evolution.repository;

import com.eva.evolution.entity.UserProfileEntity;
import com.eva.evolution.entity.UserTransactionEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface UserTransactionRepository extends ReactiveCrudRepository<UserTransactionEntity, UUID> {
}
