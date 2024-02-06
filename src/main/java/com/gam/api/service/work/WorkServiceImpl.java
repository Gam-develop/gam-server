package com.gam.api.service.work;

import com.gam.api.common.exception.WorkException;
import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.dto.work.request.WorkCreateRequestDTO;
import com.gam.api.dto.work.request.WorkDeleteRequestDTO;
import com.gam.api.dto.work.request.WorkEditRequestDTO;
import com.gam.api.dto.work.request.WorkFirstAssignRequestDto;
import com.gam.api.dto.work.response.WorkEditResponseDTO;
import com.gam.api.dto.work.response.WorkResponseDTO;
import com.gam.api.entity.User;
import com.gam.api.entity.UserStatus;
import com.gam.api.entity.Work;
import com.gam.api.repository.UserRepository;
import com.gam.api.repository.WorkRepository;
import com.gam.api.service.s3.S3ServiceImpl;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;


@RequiredArgsConstructor
@Service
@Slf4j
public class WorkServiceImpl implements WorkService {
    private final WorkRepository workRepository;
    private final UserRepository userRepository;
    private final S3ServiceImpl s3Service;
    private final int MAX_WORK_COUNT = 3;

    @Override
    @Transactional
    public WorkResponseDTO createWork(Long userId, WorkCreateRequestDTO request) {
        val user = findUser(userId);

        val userWorkCount = user.getActiveWorks().size();
        if (userWorkCount >= MAX_WORK_COUNT) {
            throw new WorkException(ExceptionMessage.WORK_COUNT_EXCEED.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if (user.getUserStatus().equals(UserStatus.NOT_PERMITTED)) {
            user.updateUserStatus(UserStatus.PERMITTED);
        }

        if (request.image().isEmpty()) {
            throw new WorkException(ExceptionMessage.WORK_NO_THUMBNAIL.getMessage(), HttpStatus.BAD_REQUEST);
        }

        val work = Work.builder()
                .title(request.title())
                .detail(request.detail())
                .photoUrl(request.image())
                .user(user)
                .build();

        workRepository.save(work);
        if (userWorkCount == 0) {
            val workId = setFirstWork(user, work);
        }
        return WorkResponseDTO.of(work.getId());
    }

    @Override
    @Transactional
    public WorkResponseDTO deleteWork(Long userId, WorkDeleteRequestDTO request) {
        val workId = request.workId();
        val user = findUser(userId);
        val work = findWork(request.workId());

        if(!work.isActive()) {
            throw new WorkException(ExceptionMessage.ALREADY_NOT_ACTIVE_WORK.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if (!isOwner(work, userId)) {
            throw new WorkException(ExceptionMessage.NOT_WORK_OWNER.getMessage(), HttpStatus.BAD_REQUEST);
        }

        val wasFirst = work.isFirst();
        val workCount = user.getActiveWorks().size();
        work.setActive(false);

        if (workCount == 1) { // 작업물이 한개 였을 때
            work.setIsFirst(false);
            user.setSelectedFirstAt(null);
            user.setWorkThumbNail(null);
            user.setFirstWorkId(null);
            user.updateUserStatus(UserStatus.NOT_PERMITTED);
            return WorkResponseDTO.of(workId);
        }

        if (wasFirst) { // 작업물이 두개 이상이었을 때
            work.setIsFirst(false);
            val recentWork = workRepository.findFirstByUserIdAndIsActiveOrderByCreatedAtDesc(userId, true)
                                                 .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_WORK.getMessage()));
            setFirstWork(user, recentWork);
        }

        return WorkResponseDTO.of(workId);
    }


    @Override
    @Transactional
    public void updateFirstWork(Long userId, WorkFirstAssignRequestDto request){
        val workId = request.workId();

        val currentWork = findWork(request.workId());

        if(!isOwner(currentWork, userId)) {
            throw new WorkException(ExceptionMessage.NOT_WORK_OWNER.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if (currentWork.isFirst()) {
            throw new IllegalArgumentException(ExceptionMessage.ALREADY_FIRST_WORK.getMessage());
        }

        val pastFirstWork = workRepository.getWorkByUserIdAndIsFirst(userId, true);

        if (pastFirstWork.isPresent()) {
            pastFirstWork.get().setIsFirst(false);
        }
        currentWork.setIsFirst(true);

        val user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));

        user.setWorkThumbNail(currentWork.getPhotoUrl()); // TODO - 코드리뷰 반영 - setFirstWork
        user.updateSelectedFirstAt();
        user.setFirstWorkId(workId);
    }

    @Transactional
    @Override
    public WorkEditResponseDTO updateWork(Long userId, WorkEditRequestDTO request) {
        val workId = request.workId();

        val work = findWork(workId);

        if (!isOwner(work, userId)) {
            throw new WorkException(ExceptionMessage.NOT_WORK_OWNER.getMessage(), HttpStatus.BAD_REQUEST);
        }

        work.setTitle(request.title());
        work.setDetail(request.detail());

        if (!work.getPhotoUrl().equals(request.image())) {
            val deletePhotoUrl = work.getPhotoUrl();
            s3Service.deleteS3Image(deletePhotoUrl);
            work.setPhotoUrl(request.image());
        }

        return WorkEditResponseDTO.of(workId);
    }

    private boolean isOwner(Work work, Long userId){
        if (!work.isOwner(userId)) {
            return false;
        }
        return true;
    }

    private User findUser(Long userId) {
        return userRepository.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));
    }

    private Work findWork(Long workId) {
        return workRepository.getWorkById(workId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_WORK.getMessage()));
    }

    private Long setFirstWork(User user, Work work) {
        work.setIsFirst(true);
        user.setFirstWorkId(work.getId());
        user.updateSelectedFirstAt();
        user.setWorkThumbNail(work.getPhotoUrl());
        return work.getId();
    }
}
