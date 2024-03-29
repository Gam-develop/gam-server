package com.gam.api.domain.report.service;

import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.domain.report.dto.request.ReportCreateRequestDTO;
import com.gam.api.domain.report.entity.Report;
import com.gam.api.domain.user.entity.User;
import com.gam.api.domain.work.entity.Work;
import com.gam.api.domain.report.repository.ReportRepository;
import com.gam.api.domain.user.repository.UserRepository;
import com.gam.api.domain.work.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final WorkRepository workRepository;

    @Transactional
    @Override
    public void createReport(ReportCreateRequestDTO request) {
        val targetUser = findUser(request.targetUserId());
        findWork(request.workId());

        val report = Report.builder()
                                    .targetUser(targetUser)
                                    .content(request.content())
                                    .workId(request.workId())
                                    .build();

        reportRepository.save(report);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));
    }

    private Work findWork(Long workId) {
        return workRepository.findById(workId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_WORK.getMessage()));
    }
}
