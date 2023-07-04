package com.gam.api.service;

import com.gam.api.common.exception.WorkException;
import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.dto.work.request.WorkCreateRequestDTO;
import com.gam.api.dto.work.response.WorkCreateResponseDTO;
import com.gam.api.entity.Work;
import com.gam.api.repository.UserRepository;
import com.gam.api.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;


@RequiredArgsConstructor
@Service
public class WorkServiceImpl implements WorkService {
    private final WorkRepository workRepository;
    private final UserRepository userRepository;

    @Override
    public WorkCreateResponseDTO createWork(Long userId, WorkCreateRequestDTO request) {
        val user = userRepository.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));

        val userWorkCount = user.getWorks().size();
        if (userWorkCount >= 4) {
            throw new WorkException(ExceptionMessage.WORK_COUNT_EXCEED.getMessage(), HttpStatus.BAD_REQUEST);
        }

        val work = workRepository.save(Work.builder()
                .title(request.title())
                .detail(request.detail())
                .photoUrl(request.image())
                .user(user)
                .build());

        return WorkCreateResponseDTO.of(work.getId());
    }
}
