package com.gam.api.repository;

import com.gam.api.entity.Magazine;
import com.gam.api.entity.User;
import com.gam.api.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserById(Long userId);
    boolean existsByUserName(String userName);
    List<User> findTop20ByUserStatusOrderByScrapCountDesc(UserStatus userStatus);

    List<User> findAllByIdNotOrderBySelectedFirstAtDesc(Long id);
    @Query(value = "SELECT u FROM User u WHERE LOWER(u.userName) LIKE %:keyword% ORDER BY u.createdAt DESC")
    List<User> findByUserName(@Param("keyword") String keyword);
    @Query(value = "SELECT u FROM User u WHERE LOWER(u.userName) LIKE %:keyword% ORDER BY u.createdAt DESC")
    List<User> findByKeyWord(@Param("keyword") String keyword);
    @Query("SELECT u FROM User u JOIN FETCH u.works WHERE u.userStatus = :userStatus")
    List<User> findAllByUserStatusWithWorks( @Param("userStatus") UserStatus userStatus);

}
