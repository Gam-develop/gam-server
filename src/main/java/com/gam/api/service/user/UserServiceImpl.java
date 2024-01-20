package com.gam.api.service.user;

import com.gam.api.common.exception.GamException;
import com.gam.api.common.exception.ScrapException;
import com.gam.api.common.exception.WorkException;
import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.dto.search.response.SearchUserWorkDTO;
import com.gam.api.dto.user.request.*;
import com.gam.api.dto.user.response.*;
import com.gam.api.dto.work.response.WorkPortfolioGetResponseDTO;
import com.gam.api.dto.work.response.WorkPortfolioListResponseDTO;
import com.gam.api.entity.*;
import com.gam.api.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserScrapRepository userScrapRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final UserTagRepository userTagRepository;
    private final WorkRepository workRepository;
    private final ReportRepository reportRepository;
    private static final int MAIN_GET_DESIGNER_COUNT = 5;

    @Transactional
    @Override
    public UserScrapResponseDTO scrapUser(Long userId, UserScrapRequestDTO request) {
        val targetUser = findUser(request.targetUserId());
        val user = findUser(userId);

        if (targetUser.getId() == userId) {
            throw new ScrapException(ExceptionMessage.INVALID_SCRAP_ID.getMessage(), HttpStatus.BAD_REQUEST);
        }

        val userScrap = userScrapRepository.findByUser_idAndTargetId(userId, targetUser.getId());

        if (Objects.isNull(userScrap)) {
            createUserScrap(user, targetUser.getId(), targetUser);
            return UserScrapResponseDTO.of(targetUser.getId(), targetUser.getUserName(), true);
        }

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
    public List<SearchUserWorkDTO> searchUserAndWork(Long myId, String keyword) {
        Set<Work> workSet = new HashSet<>(); //hashSet : 중복 제거를 위함

        // 키워드에 일치하는 user찾기
        val userList = userRepository.findByKeyWord(keyword);
        if (!userList.isEmpty()) {
            userList.forEach(user -> workSet.addAll(workRepository.findAllByUserId(user.getId())));
        }

        // 키워드에 맞는 work모두 갖고와서 hashSet에 추가 - 중복 제거됨,
        workSet.addAll(workRepository.findByKeyword(keyword));

        // 내 작업물 제외
        val myWorkList = workRepository.findAllByUserId(myId);
        Set<Work> myWorkSet = new HashSet<>(myWorkList);
        workSet.removeAll(myWorkSet);

        // 신고 된 유저 작업물 제외, fetch-join 이용
        val reportedWorksSet = userRepository.findAllByUserStatusWithWorks(UserStatus.REPORTED)
                .stream()
                .flatMap(user -> user.getWorks().stream())
                .collect(Collectors.toSet());
        workSet.removeAll(reportedWorksSet);

        // 내가 차단한 유저들 작업물 제외
        val me = findUser(myId);
        List<User> blockUsers = getBlockUsers(me);
        val blockedWorksSet = blockUsers.stream() //TODO[성능] - 성능저하 가능성 있음
                                        .flatMap(user -> user.getWorks().stream())
                                        .collect(Collectors.toSet());
        workSet.removeAll(blockedWorksSet);

        val workList = new ArrayList<>(workSet);

        workList.sort(comparing(Work::getCreatedAt).reversed());
        return workList.stream()
                .map(SearchUserWorkDTO::of)
                .collect(Collectors.toList());
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
        val me = findUser(userId);

        val scraps = userScrapRepository.findUserScrapsExceptBlockUser(userId);
        return scraps.stream().
                map((scrap) -> UserScrapsResponseDTO.of(scrap.scrapId(), scrap.user())).toList();
    }
      

    @Override
    public UserProfileResponseDTO getUserProfile(Long myId, Long userId) {
        val user = findUser(userId);
        val userScrap = userScrapRepository.findByUser_idAndTargetId(myId, userId);

        if(Objects.nonNull(userScrap)){
            return UserProfileResponseDTO.of(userScrap.isStatus(), user);
        }
        return UserProfileResponseDTO.of(false, user);
    }

    @Override
    public List<UserResponseDTO> getPopularDesigners(Long userId) { //TODO - 쿼리 지연
        val users = userRepository.findByUserStatusOrderByScrapCountDesc(UserStatus.PERMITTED);

        val me = findUser(userId);
        removeBlockUsers(users, me);

        int count = MAIN_GET_DESIGNER_COUNT; // Top 5만 갖고옴
        val first5Users = users.subList(0, Math.min(count, users.size()));

        return first5Users.stream().map((user) -> {
            val userScrap = userScrapRepository.findByUser_idAndTargetId(userId, user.getId());
            if (Objects.nonNull(userScrap)){
                return UserResponseDTO.of(user, userScrap.isStatus());
            }
            return UserResponseDTO.of(user, false);
         }).collect(Collectors.toList());
    }

    @Override
    public WorkPortfolioListResponseDTO getMyPortfolio(Long userId) {
        val user = findUser(userId);
        val works = getMineFolio(userId); //TODO - 메소드 네이밍..
        return WorkPortfolioListResponseDTO.of(user, works);
    }


    @Transactional
    @Override
    public WorkPortfolioGetResponseDTO getPortfolio(Long requestUserId, Long userId) {
        val requestUser = findUser(requestUserId);
        val user = findUser(userId);
        user.setViewCount(user.getViewCount() +1);
        val works = getUserPortfolio(userId);

        val scrapList = requestUser.getUserScraps().stream()
                .map(UserScrap::getTargetId)
                .toList();

        val isScraped = scrapList.contains(user.getId());

        return WorkPortfolioGetResponseDTO.of(isScraped, works);
    }

    @Override
    public List<UserDiscoveryResponseDTO> getDiscoveryUsers(Long userId, UserDiscoveryRequestDTO request) { //TODO - 쿼리 지연
        val users = userRepository.findAllByIdNotAndUserStatusOrderBySelectedFirstAtDesc(userId, UserStatus.PERMITTED);

        val me = findUser(userId);
        removeBlockUsers(users, me);

        return users.stream().map((user) -> {
            val targetUserId = user.getId();
            val firstWorkId = user.getFirstWorkId();
            Work firstWork;

            if (firstWorkId == null && !user.getWorks().isEmpty()) { // firstWork가 설정이 제대로 안된 경우
                firstWork = workRepository.findByUserIdOrderByCreatedAtDesc(targetUserId);
            } else {
                firstWork = findWork(firstWorkId);
            }

            val userScrap = userScrapRepository.findByUser_idAndTargetId(userId, targetUserId);
            if (Objects.isNull(userScrap)) {
                return UserDiscoveryResponseDTO.of(user, false, firstWork);
            }
            return UserDiscoveryResponseDTO.of(user, userScrap.isStatus(), firstWork);
        }).collect(Collectors.toList());
    }

    private List<Work> getMineFolio(Long userId) { //TODO - 메소드 네이밍..
        val works = workRepository.findByUserIdAndIsFirstOrderByCreatedAtDesc(userId, false);

        val representiveWork = workRepository.getWorkByUserIdAndIsFirst(userId, true);
        if (representiveWork.isPresent()){
            works.add(0, representiveWork.get());
            return works;
        }
        return works;
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

    private void removeBlockUsers(List<User> users, User me) {
        val myBlockUserList = getBlockUsers(me);
        users.removeAll(myBlockUserList);
    }

    private List<User> getBlockUsers(User user) { // TODO[성능] - 성능 저하 가능성 있음
        return user.getBlocks()
                .stream()
                .filter(Block::isStatus)
                .map(Block::getTargetId)
                .map(userRepository::findById) // User를 Optional<User>로 변환
                .filter(Optional::isPresent)   // 삭제되지 않은 사용자만 필터링
                .map(Optional::get)           // Optional에서 User로 변환
                .collect(Collectors.toList());
    }

    private boolean checkReportedUser(User targetUser) {
        return targetUser.getUserStatus() == UserStatus.REPORTED;
    }
}
