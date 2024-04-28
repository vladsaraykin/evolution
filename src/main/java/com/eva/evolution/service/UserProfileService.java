package com.eva.evolution.service;

import com.eva.evolution.dto.UserProfile;
import com.eva.evolution.dto.UserTransactionIdDto;
import com.eva.evolution.entity.UserProfileEntity;
import com.eva.evolution.entity.UserTransactionEntity;
import com.eva.evolution.repository.UserProfileRepository;
import com.eva.evolution.repository.UserTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserTransactionRepository userTransactionRepository;
    public Mono<UserProfileEntity> createUserProfile(UserProfile userProfile) {
        return userProfileRepository.save(UserProfileEntity.builder()
                        .appHudId(userProfile.getAppHudId())
                        .birthDate(LocalDate.of(userProfile.getYear(), userProfile.getMonth(), userProfile.getDay()))
                        .birthTime(LocalTime.of(userProfile.getHour(), userProfile.getMinute(), 0))
                        .tz(userProfile.getTz())
                        .city(userProfile.getCity())
                        .gender(userProfile.getGender())
                        .language(userProfile.getLanguage())
                .build());
    }

    public Mono<UserProfileEntity> getUserProfileByAppHudId(String appHudId) {
        return userProfileRepository.getUserProfileByAppHudId(appHudId);
    }

    public Mono<UserTransactionEntity> addTransaction(UUID userId, String transactionId) {
        return userTransactionRepository.save(UserTransactionEntity.builder()
                        .userId(userId)
                        .transactionId(transactionId)
                .build());
    }
}
