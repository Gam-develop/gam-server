package com.gam.api.domain.s3.service;

import com.gam.api.common.exception.AwsException;
import com.gam.api.common.message.*;
import com.gam.api.config.S3Config;
import com.gam.api.domain.s3.dto.request.PresignedRequestDTO;
import com.gam.api.domain.s3.dto.response.PresignedResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.exception.SdkServiceException;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
    private final S3Config s3Config;
    private final S3Presigner s3Presigner;
    private final S3Client s3Client;
    private final ArrayList<String> imageFileExtension =
            new ArrayList<>(List.of("jpg", "jpeg", "png", "JPG", "JPEG", "PNG"));

    @Override
    public PresignedResponseDTO getPresignedUrl(String fileName, String type) {
        return createPresignedUrl(fileName, type);
    }

    @Override
    public List<PresignedResponseDTO> getPresignedUrls(PresignedRequestDTO presignedRequestDTO) {
        val fileNames = presignedRequestDTO.fileNames();
        val type = presignedRequestDTO.type();

        return fileNames.stream()
                .map(fileName -> createPresignedUrl(fileName, type))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteS3Image(String fileName) {
        val deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(s3Config.getBucketName())
                .key(fileName)
                .build();

        try {
            s3Client.deleteObject(deleteObjectRequest);
        } catch (SdkClientException | SdkServiceException e) {
            throw new AwsException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private PresignedResponseDTO createPresignedUrl(String fileName, String type) {
        val keyName = type + "/" + UUID.randomUUID();
        val splitFileName = fileName.split("\\.");
        val extension = splitFileName[splitFileName.length-1];
        val contentType = "image/" + extension;

        if (!imageFileExtension.contains(extension)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_EXTENSION.getMessage());
        }

        val objectRequest = PutObjectRequest.builder()
                .bucket(s3Config.getBucketName())
                .key(keyName)
                .contentType(contentType)
                .build();

        val request = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .putObjectRequest(objectRequest)
                .build();

        val result = s3Presigner.presignPutObject(request);
        val signedUrl = result.url().toString();

        return PresignedResponseDTO.of(signedUrl, keyName);
    }
}
