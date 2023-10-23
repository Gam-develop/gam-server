package com.gam.api.service.report;

import com.gam.api.dto.report.request.ReportCreateRequestDTO;

public interface ReportService {
    void createReport(ReportCreateRequestDTO request);
}
