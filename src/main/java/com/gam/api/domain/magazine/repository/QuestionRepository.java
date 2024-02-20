package com.gam.api.domain.magazine.repository;

import com.gam.api.domain.magazine.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
