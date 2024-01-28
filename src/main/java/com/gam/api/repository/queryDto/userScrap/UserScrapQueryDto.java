package com.gam.api.repository.queryDto.userScrap;

import java.time.LocalDateTime;

public record UserScrapQueryDto(Long scrapId, LocalDateTime modifiedAt, Long targetId){
}
