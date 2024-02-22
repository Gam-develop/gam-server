package com.gam.api.domain.report.service;

import com.gam.api.domain.report.dto.request.ReportCreateRequestDTO;

public interface ReportService {
    void createReport(ReportCreateRequestDTO request);
}
