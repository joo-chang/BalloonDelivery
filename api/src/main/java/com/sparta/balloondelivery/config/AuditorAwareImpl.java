package com.sparta.balloondelivery.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // 인증된 사용자의 ID 또는 이름 반환 (예: SecurityContextHolder에서 가져오기)
        return Optional.of("testUser");
    }
}