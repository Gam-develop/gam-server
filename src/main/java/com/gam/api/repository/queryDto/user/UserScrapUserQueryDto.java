package com.gam.api.repository.queryDto.user;

import com.gam.api.entity.User;
import java.time.LocalDateTime;

public record UserScrapUserQueryDto(User user, Long correctCount, boolean scrapStatus, LocalDateTime selectedFirstAt) {
}