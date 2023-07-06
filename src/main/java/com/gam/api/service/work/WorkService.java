package com.gam.api.service.work;

import com.gam.api.dto.work.request.WorkCreateRequestDTO;
import com.gam.api.dto.work.request.WorkDeleteRequestDTO;
import com.gam.api.dto.work.request.WorkEditRequestDTO;
import com.gam.api.dto.work.response.WorkEditResponseDTO;
import com.gam.api.dto.work.response.WorkResponseDTO;

import java.security.Principal;

public interface WorkService {
    WorkResponseDTO createWork(Long userId, WorkCreateRequestDTO request);
    WorkResponseDTO deleteWork(Long userId, WorkDeleteRequestDTO request);
    WorkEditResponseDTO updateWork(Long userId, WorkEditRequestDTO request);
}
