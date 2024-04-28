package com.eva.evolution.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Table(value = "user_profile")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class UserProfileEntity implements Serializable {

    @Id
    private UUID id;
    private String appHudId;
    private String gender;
    private LocalDate birthDate;
    private LocalTime birthTime;
    private String tz;
    private String city;
    private String language;
}
