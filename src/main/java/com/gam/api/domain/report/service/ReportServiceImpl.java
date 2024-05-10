package com.gam.api.domain.report.service;

import com.gam.api.common.exception.ReportException;
import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.domain.report.dto.request.ReportCreateRequestDTO;
import com.gam.api.domain.report.entity.Report;
import com.gam.api.domain.report.entity.ReportStatus;
import com.gam.api.domain.user.entity.User;
import com.gam.api.domain.report.repository.ReportRepository;
import com.gam.api.domain.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
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
    public void createReport(Long userId, ReportCreateRequestDTO request) {
        val targetUser = findUser(request.targetUserId());
        val user = findUser(userId);

        List<Report> userReports = user.getReports();

        List<Report> reports = userReports.stream() // todo - 기획 여쭤보기,
                                    .filter(report -> report.getTargetUser().equals(targetUser)
                                            && report.getReportUser().equals(user)
                                            && report.getStatus() == ReportStatus.PROCEEDING)
                                    .collect(Collectors.toList());

        if(!reports.isEmpty()) {
            throw new ReportException(ExceptionMessage.ALREADY_REPORT_USER.getMessage(), HttpStatus.BAD_REQUEST);
        }

        val report = Report.builder()
                                    .reportUser(user)
                                    .targetUser(targetUser)
                                    .content(request.content())
                                    .build();

        reportRepository.save(report);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));
    }
}
