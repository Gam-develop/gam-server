package com.gam.api.service;

import com.gam.api.common.ExceptionMessage;
import com.gam.api.config.S3Config;
import com.gam.api.dto.image.PresignedRequestDTO;
import com.gam.api.dto.image.PresignedResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final S3Config s3Config;
    private final S3Presigner s3Presigner;
    private final String workBucketPath = "work/";
    private final ArrayList<String> imageFileExtension =
            new ArrayList<>(List.of("jpg", "jpeg", "png", "JPG", "JPEG", "PNG"));

    public PresignedResponseDTO getPresignedUrl(String fileName) {
        return createPresignedUrl(fileName);
    }

    public List<PresignedResponseDTO> getPresignedUrls(PresignedRequestDTO presignedRequestDTO) {
        val fileNames = presignedRequestDTO.fileNames();

        val preSignedUrls = fileNames.stream()
                .map(fileName -> createPresignedUrl(fileName))
                .collect(Collectors.toList());

        return preSignedUrls;
    }

    private PresignedResponseDTO createPresignedUrl(String fileName) {
        val keyName = workBucketPath + UUID.randomUUID() + fileName;
        val splitFileName = fileName.split("\\.");
        val extension = splitFileName[splitFileName.length-1];
        val contentType = "image/" + extension;

        if (!imageFileExtension.contains(extension)) {
            throw new RuntimeException(ExceptionMessage.INVALID_EXTENSION.getName());
        }

        val objectRequest = PutObjectRequest.builder()
                .bucket(s3Config.getBucketName())
                .key(keyName)
                .contentType(contentType)
                .build();

        val presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .putObjectRequest(objectRequest)
                .build();

        val presignedRequest = s3Presigner.presignPutObject(presignRequest);
        val signedUrl = presignedRequest.url().toString();

        return PresignedResponseDTO.of(signedUrl, keyName);
    }
}
