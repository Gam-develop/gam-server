package com.gam.api.repository;

import com.gam.api.entity.UserScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserScrapRepository extends JpaRepository<UserScrap, Long> {
    Optional<UserScrap> findByUser_idAndTargetId(Long user_id, Long target_id);
//    List<UserScrap> findByUser_idAndStatus(Long user_id, boolean status);
    List<UserScrap> getAllByUser_idAndStatus(Long user_id, boolean status);
}
