package com.eva.evolution.repository;

import com.eva.evolution.entity.AppHudEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AppHudRepository  extends ReactiveCrudRepository<AppHudEntity, String> {
}
