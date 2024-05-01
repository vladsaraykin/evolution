package com.eva.evolution.entity;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.UUID;

@Table(value = "app_hud")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class AppHudEntity implements Serializable {
    private String appHudId;
    private UUID userProfileId;

}
