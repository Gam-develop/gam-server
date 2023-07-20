package com.gam.api.service.user;

import com.gam.api.common.exception.WorkException;
import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.dto.search.response.SearchUserWorkDTO;
import com.gam.api.dto.user.request.UserOnboardRequestDTO;
import com.gam.api.dto.user.request.UserProfileUpdateRequestDTO;
import com.gam.api.dto.user.request.UserScrapRequestDTO;
import com.gam.api.dto.user.request.UserUpdateLinkRequestDTO;
import com.gam.api.dto.user.response.*;
import com.gam.api.dto.work.response.WorkPortfolioGetResponseDTO;
import com.gam.api.dto.work.response.WorkPortfolioListResponseDTO;
import com.gam.api.entity.*;
import com.gam.api.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserScrapRepository userScrapRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final UserTagRepository userTagRepository;
    private final WorkRepository workRepository;

    @Transactional
    @Override
    public UserScrapResponseDTO scrapUser(Long userId, UserScrapRequestDTO request) {
        val targetUser = findUser(request.targetUserId());
        val user = findUser(userId);

        val userScrap = userScrapRepository.findByUser_idAndTargetId(userId, targetUser.getId());

        if (Objects.nonNull(userScrap)) {
            val status = userScrap.isStatus();
            validateStatusRequest(status, request.currentScrapStatus());

            if (status) {
                targetUser.scrapCountDown();
            } else {
                targetUser.scrapCountUp();
            }

            userScrap.setScrapStatus(!status);

            return UserScrapResponseDTO.of(targetUser.getId(), targetUser.getUserName(), userScrap.isStatus());
        }
        createUserScrap(user, targetUser.getId(), targetUser);

        return UserScrapResponseDTO.of(targetUser.getId(), targetUser.getUserName(), true);
    }

    @Transactional
    @Override
    public void updateInstagramLink(Long userId, UserUpdateLinkRequestDTO request) {
        val user = findUser(userId);
        user.setInstagramLink(request.link());
    }

    @Transactional
    @Override
    public void updateBehanceLink(Long userId, UserUpdateLinkRequestDTO request) {
        val user = findUser(userId);
        user.setBehanceLink(request.link());
    }

    @Transactional
    @Override
    public void updateNotionLink(Long userId, UserUpdateLinkRequestDTO request) {
        val user = findUser(userId);
        user.setNotionLink(request.link());
    }

    @Override
    public List<SearchUserWorkDTO> searchUserAndWork(String keyword) {

        Set<Work> workSet = new HashSet<>();
        val user = userRepository.findByUserName(keyword);
        workSet.addAll(workRepository.findByUserId(user.getId()));
        workSet.addAll(workRepository.findByKeyword(keyword));
        val workList = new ArrayList<>(workSet);

        //워크 리스트 돌면서 return,
        //createAt 넣을지 말지 고민중, 아마 넣어야할듯
    }

    @Override
    public UserMyProfileResponseDTO getMyProfile(Long userId){
        val user = findUser(userId);
        return UserMyProfileResponseDTO.of(user);
    }

    @Transactional
    @Override
    public UserProfileUpdateResponseDTO updateMyProfile(Long userId, UserProfileUpdateRequestDTO request){
        val user = findUser(userId);

        if (request.userInfo() != null) {
            user.setInfo(request.userInfo());
        }

        if (request.userDetail() != null) {
            user.setDetail(request.userDetail());
        }

        if (request.email() != null) {
            user.setEmail(request.email());
        }

        if (request.tags() != null) {
            val newTags = request.tags();
            createUserTags(newTags, user);
        }

        return UserProfileUpdateResponseDTO.of(user);
    }

    @Transactional
    @Override
    public void onboardUser(Long userId, UserOnboardRequestDTO userOnboardRequestDTO) {
        val userName = userOnboardRequestDTO.userName();
        val info = userOnboardRequestDTO.info();
        val tags = userOnboardRequestDTO.tags();

        if (tags.length > 3) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_TAG_COUNT.getMessage());
        }

        val user = findUser(userId);

        createUserTags(tags, user);
        user.onboardUser(userName, info, tags);
    }

    @Override
    public UserNameCheckResponseDTO checkUserNameDuplicated(String userName) {
        val isDuplicated = userRepository.existsByUserName(userName);
        return UserNameCheckResponseDTO.of(isDuplicated);
    }

    @Override
    public List<UserScrapsResponseDTO> getUserScraps(Long userId) {
        val scraps = userScrapRepository.getAllByUser_idAndStatus(userId, true);

        val scrapUsers = scraps.stream()
                .map((scrap) -> {
                    val scrapId = scrap.getId();
                    val targetId = scrap.getTargetId();
                    val targetUser =  userRepository.findById(targetId)
                            .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));
                    return UserScrapsResponseDTO.of(scrapId, targetUser);
                    })
                .collect(Collectors.toList());
            return scrapUsers;
    }

    @Override
    public UserProfileResponseDTO getUserProfile(Long myId, Long userId) {
        val user = findUser(userId);
        val userScrap = userScrapRepository.findByUser_idAndTargetId(myId, userId);

        if(Objects.nonNull(userScrap)){
            return UserProfileResponseDTO.of(true, user);
        }
        return UserProfileResponseDTO.of(false, user);
    }

    @Override
    public List<UserResponseDTO> getPopularDesigners(Long userId) {
        val users = userRepository.findTop5ByOrderByScrapCountDesc();
        return users.stream().map((user) -> {
            val userScrap = userScrapRepository.findByUser_idAndTargetId(userId, user.getId());
            if (Objects.nonNull(userScrap)){
                return UserResponseDTO.of(user,true);
            }
            return UserResponseDTO.of(user, false);
         }).collect(Collectors.toList());
    }

    @Override
    public WorkPortfolioListResponseDTO getMyPortfolio(Long userId) {
        val user = findUser(userId);
        val works = getUserPortfolio(userId);
        return WorkPortfolioListResponseDTO.of(user, works);
    }


    @Override
    public WorkPortfolioGetResponseDTO getPortfolio(Long requestUserId, Long userId) {
        val requestUser = findUser(requestUserId);
        val user = findUser(userId);
        val works = getUserPortfolio(userId);

        val scrapList = requestUser.getUserScraps().stream()
                .map(UserScrap::getTargetId)
                .toList();

        val isScraped = scrapList.contains(user.getId());

        return WorkPortfolioGetResponseDTO.of(isScraped, works);
    }

    @Override
    public List<UserDiscoveryResponseDTO> getDiscoveryUsers(Long userId) {
        val users = userRepository.findAllByIdNotOrderBySelectedFirstAtDesc(userId);

        return users.stream().map((user) -> {
            val targetUserId = user.getId();
            val firstWorkId = user.getFirstWorkId();
            val firstWork = findWork(firstWorkId);

            val userScrap = userScrapRepository.findByUser_idAndTargetId(userId, targetUserId);
            if (Objects.isNull(userScrap)) {
                return UserDiscoveryResponseDTO.of(user, false, firstWork);
                }
            return UserDiscoveryResponseDTO.of(user, userScrap.isStatus(), firstWork);
        }).collect(Collectors.toList());
    }

    private List<Work> getUserPortfolio(Long userId) {
        val works = workRepository.findByUserIdAndIsFirstOrderByCreatedAtDesc(userId, false);

        val representiveWork = workRepository.getWorkByUserIdAndIsFirst(userId, true)
                .orElseThrow(() -> new WorkException(ExceptionMessage.NOT_FOUND_FIRST_WORK.getMessage(), HttpStatus.BAD_REQUEST));

        works.add(0, representiveWork);

        return works;
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));
    }

    private Work findWork(Long workId) {
        return workRepository.findById(workId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_WORK.getMessage()));
    }

    private void createUserScrap(User user, Long targetId, User targetUser){
        userScrapRepository.save(UserScrap.builder()
                .user(user)
                .targetId(targetId)
                .build());
        targetUser.scrapCountUp();
    }


    private void validateStatusRequest(boolean status, boolean requestStatus) {
        if (status != requestStatus) {
            throw new IllegalArgumentException(ExceptionMessage.NOT_MATCH_DB_SCRAP_STATUS.getMessage());
        }
    }

    private void createUserTags(int[] newTags, User user) {
        val tags = tagRepository.findAll();

        userTagRepository.deleteAllByUser_id(user.getId());

        for (Integer tag: newTags) {
            userTagRepository.save(UserTag.builder()
                    .user(user)
                    .tag(tags.get(tag-1))
                    .build());
        }
        user.setTags(newTags);
    }
}
