package com.finance.finance_dashboard.service;

import com.finance.finance_dashboard.Repository.TransactionRepository;
import com.finance.finance_dashboard.dto.CategorySummaryDTO;
import com.finance.finance_dashboard.dto.DashboardDTO;
import com.finance.finance_dashboard.dto.DashboardSummaryDTO;
import com.finance.finance_dashboard.dto.TrendDTO;
import com.finance.finance_dashboard.entity.RecordType;
import com.finance.finance_dashboard.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TransactionRepository transactionRepository;

    public DashboardSummaryDTO getSummary(String email) {

        Double income = transactionRepository.sumByType(email, RecordType.INCOME);
        Double expense = transactionRepository.sumByType(email, RecordType.EXPENSE);

        income = (income != null) ? income : 0;
        expense = (expense != null) ? expense : 0;

        DashboardSummaryDTO dto = new DashboardSummaryDTO();
        dto.setTotalIncome(income);
        dto.setTotalExpense(expense);
        dto.setNetBalance(income - expense);

        return dto;
    }

    public List<CategorySummaryDTO> getCategorySummary(String email) {

        return transactionRepository.sumByCategory(email)
                .stream()
                .map(obj -> new CategorySummaryDTO(
                ))
                .toList();
    }

    public List<TrendDTO> getMonthlyTrends(String email) {

        return transactionRepository.monthlySummary(email)
                .stream()
                .map(obj -> new TrendDTO(
                ))
                .toList();
    }

    public List<Transaction> getRecentTransactions(String email) {
        return transactionRepository.findTop5ByUserEmailOrderByDateDesc(email);
    }

    public DashboardDTO getFullDashboard(String email) {

        DashboardDTO dto = new DashboardDTO();

        dto.setSummary(getSummary(email));
        dto.setCategories(getCategorySummary(email));
        dto.setTrends(getMonthlyTrends(email));
        dto.setRecent(getRecentTransactions(email));

        return dto;
    }

}
