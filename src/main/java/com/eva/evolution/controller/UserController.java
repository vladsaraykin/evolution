package com.eva.evolution.controller;

import com.eva.evolution.dto.UpdateUserTransactionDto;
import com.eva.evolution.dto.UserTransactionIdDto;
import com.eva.evolution.dto.UserProfile;
import com.eva.evolution.entity.UserProfileEntity;
import com.eva.evolution.entity.UserTransactionEntity;
import com.eva.evolution.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController("user")
@RequiredArgsConstructor
public class UserController {

    private final UserProfileService userProfileService;
    private final EvolutionIntegrationService evolutionService;

    @PostMapping("/create_profile")
    public Mono<UserProfileEntity> createUserProfile(@RequestBody UserProfile userProfile) {
        return userProfileService.createUserProfile(userProfile);
    }

    @GetMapping("/get_profile")
    public Mono<EvolutionUserProfile> getEvolutionUserProfile(@RequestHeader(name = "user") String userId) {
        return userProfileService.getUserProfileByAppHudId(userId)
                .flatMap(userProfileEntity -> evolutionService.getUserProfile(userProfileEntity));
    }

    @PostMapping("/sync_paid_features")
    public Mono<UserTransactionEntity> syncPaidFeatures(@RequestHeader("user") String appHudId,
                                                        @RequestBody UserTransactionIdDto userTransactionDto){
        return userProfileService.getUserProfileByAppHudId(appHudId)
                        .flatMap(user -> userProfileService.addTransaction(user.getId(), userTransactionDto.getTransactionId()));
    }

    @PostMapping("/did_change_user_id")
    public Mono<Void> didChangeUserId(@RequestBody UpdateUserTransactionDto updateUserTransactionDto){
        return
                userProfileService.didChangeUserId(updateUserTransactionDto);
    }
}
