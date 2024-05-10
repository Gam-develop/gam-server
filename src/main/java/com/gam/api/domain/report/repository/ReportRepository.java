package com.gam.api.domain.report.repository;

import com.gam.api.domain.report.entity.Report;
import com.gam.api.domain.report.entity.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllByStatus(ReportStatus status);
    void deleteAllByTargetUserId(Long userId);
}
