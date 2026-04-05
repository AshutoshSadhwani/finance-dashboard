package com.finance.finance_dashboard.dto;

import lombok.Data;

@Data
public class TrendDTO {
    private String period; // e.g. "2026-04"
    private double income;
    private double expense;
}