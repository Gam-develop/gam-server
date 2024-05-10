package com.gam.api.domain.s3.service;

import com.gam.api.domain.s3.dto.request.PresignedRequestDTO;
import com.gam.api.domain.s3.dto.response.PresignedResponseDTO;
import java.util.List;

public interface S3Service {
    PresignedResponseDTO getPresignedUrl(String fileName);
    List<PresignedResponseDTO> getPresignedUrls(PresignedRequestDTO presignedRequestDTO);
    void deleteS3Image(String fileName);
}
