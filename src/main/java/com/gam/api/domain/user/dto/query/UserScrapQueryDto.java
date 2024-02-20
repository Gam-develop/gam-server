package com.gam.api.domain.user.dto.query;

import java.time.LocalDateTime;

public record UserScrapQueryDto(Long scrapId, LocalDateTime modifiedAt, Long targetId){
}
