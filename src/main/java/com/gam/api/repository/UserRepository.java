package com.gam.api.repository;

import com.gam.api.entity.User;
import com.gam.api.entity.UserStatus;
import com.gam.api.repository.queryDto.user.UserScrapUserQueryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  
    Optional<User> getUserById(Long userId);
    boolean existsByUserName(String userName);

    List<User> findByUserStatusOrderByScrapCountDesc(UserStatus userStatus); // TODO - 기획

    @Query(value = "SELECT u FROM User u WHERE LOWER(u.userName) LIKE %:keyword% ORDER BY u.createdAt DESC")
    List<User> findByUserName(@Param("keyword") String keyword);
  
    @Query(value = "SELECT u FROM User u WHERE LOWER(u.userName) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY u.createdAt DESC")
    List<User> findByKeyWord(@Param("keyword") String keyword);
  
    @Query("SELECT u FROM User u JOIN FETCH u.works WHERE u.userStatus = :userStatus")
    List<User> findAllByUserStatusWithWorks( @Param("userStatus") UserStatus userStatus);

    @Query("select distinct new com.gam.api.repository.queryDto.user.UserScrapUserQueryDto(us, coalesce(scrap.status, false), us.selectedFirstAt) " +
            "from User us " +
            "left join fetch Work work on work.user.id = us.id " +
            "left join fetch UserScrap scrap on scrap.targetId = us.id and scrap.user.id = :userId " +
            "where us.userStatus = 'PERMITTED' and " +
            "      us.id not in (select block.targetId from Block block where block.user.id = :userId and block.status = true) " +
            "and us.id != :userId and us.role = 'USER' " +
            "order by us.selectedFirstAt desc")
    List<UserScrapUserQueryDto>findAllPermittedWithNotBlocked(@Param("userId") long userId);

    List<User> findAll();

    @Query(value = "SELECT u FROM User u WHERE LOWER(u.userName) LIKE LOWER(CONCAT('%', :keyword, '%')) and u.userStatus!='REPORTED' and u.id!=:userId ORDER BY u.createdAt DESC")
    List<User> findByKeyWord(@Param("userId")Long userId, @Param("keyword") String keyword);

    @Query("select u "+
            " from User u "+
            " where u.id in :userId")
    List<User> getByUserIdList(@Param("userId") List<Long> userId);
}
