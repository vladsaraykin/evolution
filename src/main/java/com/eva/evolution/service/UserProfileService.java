package com.eva.evolution.service;

import com.eva.evolution.dto.UpdateUserTransactionDto;
import com.eva.evolution.dto.UserProfile;
import com.eva.evolution.entity.AppHudEntity;
import com.eva.evolution.entity.UserProfileEntity;
import com.eva.evolution.entity.UserTransactionEntity;
import com.eva.evolution.repository.AppHudRepository;
import com.eva.evolution.repository.UserProfileRepository;
import com.eva.evolution.repository.UserTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final AppHudRepository appHudRepository;
    private final UserTransactionRepository userTransactionRepository;

    @Transactional
    public Mono<UserProfileEntity> createUserProfile(UserProfile userProfile) {
        return userProfileRepository.save(UserProfileEntity.builder()
                        .appHudId(userProfile.getAppHudId())
                        .birthDate(LocalDate.of(userProfile.getYear(), userProfile.getMonth(), userProfile.getDay()))
                        .birthTime(LocalTime.of(userProfile.getHour(), userProfile.getMinute(), 0))
                        .tz(userProfile.getTz())
                        .city(userProfile.getCity())
                        .gender(userProfile.getGender())
                        .language(userProfile.getLanguage())
                .build())
                .flatMap(userProfileEntity -> appHudRepository.save(AppHudEntity.builder()
                        .userProfileId(userProfileEntity.getId())
                        .appHudId(userProfileEntity.getAppHudId())
                        .build())
                        .map(s -> userProfileEntity));
    }

    public Mono<UserProfileEntity> getUserProfileByAppHudId(String appHudId) {
        return userProfileRepository.getUserProfileByAppHudId(appHudId);
    }

    public Mono<UserTransactionEntity> addTransaction(UUID userId, String transactionId) {
        return userTransactionRepository.save(UserTransactionEntity.builder()
                        .userProfileId(userId)
                        .transactionId(transactionId)
                .build());
    }

    @Transactional
    public Mono<UserProfileEntity> changeUserAppHudId(UpdateUserTransactionDto updateUserTransactionDto) {
        List<String> transactionIds = updateUserTransactionDto.getTransactionIds();
        return userTransactionRepository.findAllByTransactionIdIn(transactionIds)
                .map(UserTransactionEntity::getUserProfileId)
                .collectList()
                .flatMap(users -> {

                    if (users.size() > 2) {
                        log.error("Find more 1 user by transactions. {}", users);
                    }
                    return userProfileRepository.findById(users.get(0))
                            .flatMap(userEntity -> {
                                //todo сделать запрос консистентным, что бы при повторном запросе ошибку не выдавал
                                userEntity.setAppHudId(updateUserTransactionDto.getNewAppHudId());
                                return appHudRepository.save(AppHudEntity.builder()
                                        .userProfileId(users.get(0))
                                        .appHudId(updateUserTransactionDto.getNewAppHudId())
                                        .build())
                                        .flatMap(s -> userProfileRepository.save(userEntity));
                            });
                });
    }
}
