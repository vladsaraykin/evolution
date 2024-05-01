package com.eva.evolution.controller;

import com.eva.evolution.dto.UpdateUserTransactionDto;
import com.eva.evolution.dto.UserTransactionIdDto;
import com.eva.evolution.dto.UserProfile;
import com.eva.evolution.entity.UserProfileEntity;
import com.eva.evolution.entity.UserTransactionEntity;
import com.eva.evolution.service.EvolutionIntegrationService;
import com.eva.evolution.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController("user")
@RequiredArgsConstructor
public class UserController {

    private final UserProfileService userProfileService;
    private final EvolutionIntegrationService evolutionService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/create_profile")
    public Mono<UserProfileEntity> createUserProfile(@RequestBody UserProfile userProfile) {
        return userProfileService.createUserProfile(userProfile);
    }

    @Operation(summary = "Получение профиля пользователя")
    @GetMapping("/get_profile")
    //todo обработать ошибочные варианты варинты
    public Mono<Map<String, Object>> getEvolutionUserProfile(@RequestHeader(name = "user") String userId) {
        return userProfileService.getUserProfileByAppHudId(userId)
                .flatMap(evolutionService::getUserProfile);
    }

    @Operation(summary = "Сохранение транзации пользователя")
    @PostMapping("/sync_paid_features")
    public Mono<UserTransactionEntity> syncPaidFeatures(@RequestHeader("user") String appHudId,
                                                        @RequestBody UserTransactionIdDto userTransactionDto){
        return userProfileService.getUserProfileByAppHudId(appHudId)
                        .flatMap(user -> userProfileService.addTransaction(user.getId(), userTransactionDto.getTransactionId()));
    }

    @Operation(summary = "Механизм обновления пользователя", description = "Заменит appHudId у пользователя")
    @PostMapping("/did_change_user_id")
    public Mono<UserProfileEntity> changeUserAppHudId(@RequestBody UpdateUserTransactionDto updateUserTransactionDto){
        return userProfileService.changeUserAppHudId(updateUserTransactionDto);
    }
}
