package com.finance.finance_dashboard.dto;

import lombok.Data;

@Data
public class DashboardSummaryDTO {
    private double totalIncome;
    private double totalExpense;
    private double netBalance;
}