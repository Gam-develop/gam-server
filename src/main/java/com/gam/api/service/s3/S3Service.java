package com.gam.api.service.s3;

import com.gam.api.dto.s3.request.PresignedRequestDTO;
import com.gam.api.dto.s3.response.PresignedResponseDTO;
import java.util.List;

public interface S3Service {
    PresignedResponseDTO getPresignedUrl(String fileName);
    List<PresignedResponseDTO> getPresignedUrls(PresignedRequestDTO presignedRequestDTO);
    void deleteS3Image(String fileName);
}
