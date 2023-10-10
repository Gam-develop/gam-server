package com.gam.api.service.report;

import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.dto.report.request.ReportCreateRequestDTO;
import com.gam.api.entity.Report;
import com.gam.api.entity.User;
import com.gam.api.entity.UserStatus;
import com.gam.api.entity.Work;
import com.gam.api.repository.ReportRepository;
import com.gam.api.repository.UserRepository;
import com.gam.api.repository.WorkRepository;
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

    @Transactional
    @Override
    public void createReport(ReportCreateRequestDTO request) {
        val targetUser = findUser(request.targetUserId());

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
}
