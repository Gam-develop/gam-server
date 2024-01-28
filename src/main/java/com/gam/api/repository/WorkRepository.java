package com.gam.api.repository;

import com.gam.api.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkRepository extends JpaRepository<Work, Long> {
    Optional<Work> getWorkById(Long workId);
    void deleteById(Long workId);
    Optional<Work> getWorkByUserIdAndIsFirst(Long userId, Boolean status);
    List<Work> findByUserIdAndIsFirstOrderByCreatedAtDesc(Long userId, boolean isFirst);
    @Query("SELECT w FROM Work w JOIN FETCH w.user")
    List<Work> findAllByUserId(Long userId);
    @Query(value = "SELECT w FROM Work w WHERE LOWER(w.title) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY w.createdAt DESC")
    List<Work> findByKeyword(String keyword);
    Work findByUserIdOrderByCreatedAtDesc(Long userId);
}
