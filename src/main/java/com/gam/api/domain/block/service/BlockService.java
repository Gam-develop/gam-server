package com.gam.api.domain.block.service;

import com.gam.api.domain.block.dto.request.BlockRequestDTO;
import com.gam.api.domain.block.dto.response.BlockResponseDTO;

public interface BlockService {
    BlockResponseDTO createBlock(Long userId, BlockRequestDTO request);
}
