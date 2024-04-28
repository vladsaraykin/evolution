package com.eva.evolution.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserTransactionIdDto {

    @JsonProperty("transaction_id")
    private String transactionId;
}
