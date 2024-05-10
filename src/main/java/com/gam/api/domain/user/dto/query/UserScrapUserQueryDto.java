package com.gam.api.domain.user.dto.query;

import com.gam.api.domain.user.entity.User;
import java.time.LocalDateTime;

public record UserScrapUserQueryDto(User user, Long correctCount, boolean scrapStatus, LocalDateTime selectedFirstAt) {
}
