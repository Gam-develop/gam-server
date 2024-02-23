package com.gam.api.domain.s3.dto.request;

import java.util.List;

public record PresignedRequestDTO(List<String> fileNames) {
}
