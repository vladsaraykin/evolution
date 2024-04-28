package com.eva.evolution.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserTransactionDto {

    @JsonProperty("apphud_id")
    private String appHudId;

    @JsonProperty("transaction_ids")
    private List<String> transactionIds;
}
