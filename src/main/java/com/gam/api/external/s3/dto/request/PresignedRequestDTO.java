package com.gam.api.external.s3.dto.request;

import java.util.List;

public record PresignedRequestDTO(List<String> fileNames) {
}
