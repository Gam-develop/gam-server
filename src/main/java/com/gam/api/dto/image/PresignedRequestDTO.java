package com.gam.api.dto.image;

import java.util.List;

public record PresignedRequestDTO(List<String> fileNames) {
}
