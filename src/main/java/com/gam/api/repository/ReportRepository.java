package com.gam.api.repository;

import com.gam.api.entity.Report;
import com.gam.api.entity.ReportStatus;
import com.gam.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
