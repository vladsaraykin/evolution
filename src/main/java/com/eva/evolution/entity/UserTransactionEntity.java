package com.eva.evolution.entity;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(value = "user_transaction")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class UserTransactionEntity {

    private UUID id;
    private UUID userId;
    private String transactionId;

    private LocalDateTime createdAt;
}
