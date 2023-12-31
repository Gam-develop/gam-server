package com.gam.api.service.work;

import com.gam.api.common.exception.WorkException;
import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.dto.work.request.WorkCreateRequestDTO;
import com.gam.api.dto.work.request.WorkDeleteRequestDTO;
import com.gam.api.dto.work.request.WorkEditRequestDTO;
import com.gam.api.dto.work.request.WorkFirstAssignRequestDto;
import com.gam.api.dto.work.response.WorkEditResponseDTO;
import com.gam.api.dto.work.response.WorkResponseDTO;
import com.gam.api.entity.UserStatus;
import com.gam.api.entity.Work;
import com.gam.api.repository.UserRepository;
import com.gam.api.repository.WorkRepository;
import com.gam.api.service.s3.S3ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;


@RequiredArgsConstructor
@Service
public class WorkServiceImpl implements WorkService {
    private final WorkRepository workRepository;
    private final UserRepository userRepository;
    private final S3ServiceImpl s3Service;

    @Override
    @Transactional
    public WorkResponseDTO createWork(Long userId, WorkCreateRequestDTO request) {
        val user = userRepository.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));

        val userWorkCount = user.getWorks().size();
        if (userWorkCount >= 4) {
            throw new WorkException(ExceptionMessage.WORK_COUNT_EXCEED.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if (user.getUserStatus().equals(UserStatus.NOT_PERMITTED)) {
            user.updateUserStatus(UserStatus.PERMITTED);
        }

        val work = workRepository.save(Work.builder()
                .title(request.title())
                .detail(request.detail())
                .photoUrl(request.image())
                .user(user)
                .build());

        return WorkResponseDTO.of(work.getId());
    }

    @Override
    @Transactional
    public WorkResponseDTO deleteWork(Long userId, WorkDeleteRequestDTO request) {
        val workId = request.workId();

        val user = userRepository.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));

        if (user.getWorks().size() == 1) {
            user.deleteLinks();
            user.updateUserStatus(UserStatus.NOT_PERMITTED);
        }

        val work = workRepository.getWorkById(workId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_WORK.getMessage()));

        if (!isOwner(work, userId)) {
            throw new WorkException(ExceptionMessage.NOT_WORK_OWNER.getMessage(), HttpStatus.BAD_REQUEST);
        }

        val photoUrl = work.getPhotoUrl();

        s3Service.deleteS3Image(photoUrl);

        workRepository.deleteById(workId);

        return WorkResponseDTO.of(workId);
    }

    @Override
    @Transactional
    public void updateFirstWork(Long userId, WorkFirstAssignRequestDto request){
        val workId = request.workId();

        val currentWork = workRepository.getWorkById(workId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_WORK.getMessage()));

        if (currentWork.isFirst()){
            throw new IllegalArgumentException(ExceptionMessage.ALREADY_FIRST_WORK.getMessage());
        }

        val pastFirstWork = workRepository.getWorkByUserIdAndIsFirst(userId, true);

        if (pastFirstWork.isPresent()){
            pastFirstWork.get().setIsFirst(false);
        }
        currentWork.setIsFirst(true);

        val user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));

        user.setWorkThumbNail(currentWork.getPhotoUrl());
        user.updateSelectedFirstAt();
        user.setFirstWorkId(workId);
    }

    @Transactional
    @Override
    public WorkEditResponseDTO updateWork(Long userId, WorkEditRequestDTO request) {
        val workId = request.workId();

        val work = workRepository.getWorkById(workId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_WORK.getMessage()));

        if(!isOwner(work, userId)) {
            throw new WorkException(ExceptionMessage.NOT_WORK_OWNER.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if (request.title() != null) {
            work.setTitle(request.title());
        }

        if (request.detail() != null) {
            work.setDetail(request.detail());
        }

        if (request.image() != null) {
            val deletePhotoUrl = work.getPhotoUrl();
            s3Service.deleteS3Image(deletePhotoUrl);

            val newPhotoUrl = request.image();
            work.setPhotoUrl(newPhotoUrl);
        }

        return WorkEditResponseDTO.of(workId);
    }

    private boolean isOwner(Work work, Long userId){
        if (!work.isOwner(userId)) {
            return false;
        }
        return true;
    }
}
