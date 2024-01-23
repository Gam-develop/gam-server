package com.gam.api.repository.queryDto.user;

import com.gam.api.entity.User;
import com.gam.api.entity.superclass.TimeStamped;
import java.time.LocalDateTime;

public record UserScrapUserQueryDto(User user, boolean scrapStatus, LocalDateTime selectedFirstAt) {
}
