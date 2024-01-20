package com.gam.api.repository.queryDto.user;

import com.gam.api.entity.User;

public record UserScrapQueryDto(Long scrapId, User user) {
}
