package com.finance.finance_dashboard.dto;

import com.finance.finance_dashboard.entity.Transaction;
import lombok.Data;

import java.util.List;

@Data
public class DashboardDTO {

    private DashboardSummaryDTO summary;
    private List<CategorySummaryDTO> categories;
    private List<TrendDTO> trends;
    private List<Transaction> recent;


}
