package com.gam.api.dto.s3.request;

import java.util.List;

public record PresignedRequestDTO(List<String> fileNames) {
}
