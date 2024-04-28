package com.eva.evolution.repository;

import com.eva.evolution.entity.UserProfileEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserProfileRepository extends ReactiveCrudRepository<UserProfileEntity, UUID> {
    Mono<UserProfileEntity> getUserProfileByAppHudId(String appHudId);
}
