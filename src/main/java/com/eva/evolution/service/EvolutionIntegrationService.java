package com.eva.evolution.service;

import com.eva.evolution.entity.UserProfileEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class EvolutionIntegrationService {

    private final WebClient webClient;

    @Value("${evolution.host}")
    private String host;
    @Value("${evolution.api-secret}")
    private String apiSecret;
    @Value("${evolution.api-client}")
    private String apiClient;

    public Mono<Map<String, Object>> getUserProfile(UserProfileEntity userProfileEntity) {
        return webClient.post()
                .uri(host + "/api/experimental/mini-profile/calc")
                .header("x-api-client", apiClient)
                .header("x-api-secret", apiSecret)
                .bodyValue(EvolutionUserProfileDto.builder()
                        .gender(userProfileEntity.getGender())
                        .city(userProfileEntity.getCity())
                        .tz(userProfileEntity.getTz())
                        .language(userProfileEntity.getLanguage())
                        .year(userProfileEntity.getBirthDate().getYear())
                        .month(userProfileEntity.getBirthDate().getMonthValue())
                        .day(userProfileEntity.getBirthDate().getDayOfMonth())
                        .hour(userProfileEntity.getBirthTime().getHour())
                        .minute(userProfileEntity.getBirthTime().getMinute())
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        response -> {
                            if (response.statusCode().is4xxClientError()) {
                                if (response.statusCode().equals(HttpStatus.FORBIDDEN) || response.statusCode().equals(HttpStatus.UNAUTHORIZED)) {
                                    log.error("Authorization to evolution is failed");
                                    return response.createException();
                                }
                            }
                            if (response.statusCode().is5xxServerError()) {
                                log.error("Something went wrong in evolution service");
                            }
                            return response.createException();
                        })
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    @Builder
    @Getter
    @Setter
    private static class EvolutionUserProfileDto {
        private String gender;
        private int year;
        private int month;
        private int day;
        private int hour;
        private int minute;
        private String tz;
        private String city;
        private String language;
    }
}
