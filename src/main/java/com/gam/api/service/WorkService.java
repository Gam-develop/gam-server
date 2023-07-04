package com.gam.api.service;

import com.gam.api.dto.work.request.WorkCreateRequestDTO;
import com.gam.api.dto.work.request.WorkDeleteRequestDTO;
import com.gam.api.dto.work.response.WorkResponseDTO;

public interface WorkService {
    WorkResponseDTO createWork(Long userId, WorkCreateRequestDTO request);
    WorkResponseDTO deleteWork(Long userId, WorkDeleteRequestDTO request);
}
