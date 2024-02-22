package com.gam.api.domain.work.service;

import com.gam.api.domain.work.dto.request.WorkCreateRequestDTO;
import com.gam.api.domain.work.dto.request.WorkDeleteRequestDTO;
import com.gam.api.domain.work.dto.request.WorkEditRequestDTO;
import com.gam.api.domain.work.dto.request.WorkFirstAssignRequestDto;
import com.gam.api.domain.work.dto.response.WorkEditResponseDTO;
import com.gam.api.domain.work.dto.response.WorkResponseDTO;

public interface WorkService {
    WorkResponseDTO createWork(Long userId, WorkCreateRequestDTO request);
    WorkResponseDTO deleteWork(Long userId, WorkDeleteRequestDTO request);
    void updateFirstWork(Long userId, WorkFirstAssignRequestDto request);
    WorkEditResponseDTO updateWork(Long userId, WorkEditRequestDTO request);
}
