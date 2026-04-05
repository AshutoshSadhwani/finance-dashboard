package com.finance.finance_dashboard.controller;

import com.finance.finance_dashboard.dto.DashboardDTO;
import com.finance.finance_dashboard.dto.DashboardSummaryDTO;
import com.finance.finance_dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public DashboardDTO getDashboard(Authentication auth) {
        return dashboardService.getFullDashboard(auth.getName());
    }
}