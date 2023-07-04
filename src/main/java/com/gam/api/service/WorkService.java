package com.gam.api.service;

import com.gam.api.dto.work.request.WorkCreateRequestDTO;
import com.gam.api.dto.work.response.WorkCreateResponseDTO;

public interface WorkService {
    WorkCreateResponseDTO createWork(Long userId, WorkCreateRequestDTO request);
}
