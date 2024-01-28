package com.gam.api.service.block;

import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.dto.block.request.BlockRequestDTO;
import com.gam.api.dto.block.response.BlockResponseDTO;

import com.gam.api.entity.Block;
import com.gam.api.entity.User;
import com.gam.api.repository.BlockRepository;
import com.gam.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class BlockServiceImpl implements BlockService{
    private final BlockRepository blockRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public BlockResponseDTO createBlock(Long userId, BlockRequestDTO request) {
        val targetUserId = request.targetUserId();
        val user = findUser(userId);
        val targetUser = findUser(targetUserId);
        Block block = blockRepository.findByUserIdAndTargetId(userId, targetUserId);

        if (Objects.nonNull(block)) {
            val status = block.isStatus();
            validateStatusRequest(status, request.currentBlockStatus());
            block.setStatus(!status);
            return BlockResponseDTO.of(targetUser, block.isStatus());
        }
        block = Block.builder()
                    .user(user)
                    .targetId(targetUserId)
                    .build();
        blockRepository.save(block);
        return BlockResponseDTO.of(targetUser, true);
    }


    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));
    }

    private void validateStatusRequest(boolean status, boolean requestStatus) {
        if (status != requestStatus) {
            throw new IllegalArgumentException(ExceptionMessage.NOT_MATCH_DB_BLOCK_STATUS.getMessage());
        }
    }
}
