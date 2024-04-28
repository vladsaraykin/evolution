package com.eva.evolution.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    @JsonProperty("apphud_id")
    private String appHudId;
    private String gender;
    private short year;
    private short month;
    private short day;
    private short hour;
    private short minute;
    private String tz;
    private String city;
    private String language;
}
