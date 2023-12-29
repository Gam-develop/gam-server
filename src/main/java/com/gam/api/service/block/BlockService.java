package com.gam.api.service.block;

import com.gam.api.dto.block.request.BlockRequestDTO;
import com.gam.api.dto.block.response.BlockResponseDTO;

public interface BlockService {
    BlockResponseDTO createBlock(Long userId, BlockRequestDTO request);
}
