package com.finance.finance_dashboard.controller;

import com.finance.finance_dashboard.dto.DashboardDTO;
import com.finance.finance_dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class DashboardGraphQLController {

    private final DashboardService dashboardService;

    @QueryMapping
    public DashboardDTO dashboard(Authentication auth) {
        return dashboardService.getFullDashboard(auth.getName());
    }
}