package com.finance.finance_dashboard.controller;

import com.finance.finance_dashboard.dto.TransactionDTO;
import com.finance.finance_dashboard.entity.RecordType;
import com.finance.finance_dashboard.entity.Transaction;
import com.finance.finance_dashboard.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // ✅ CREATE
    @PostMapping
    public Transaction addTransaction(@Valid @RequestBody TransactionDTO dto,
                                      Authentication auth) {
        return transactionService.addTransaction(dto, auth.getName());
    }

    // ✅ READ
    @GetMapping
    public List<Transaction> getTransactions(Authentication auth) {
        return transactionService.getUserTransactions(auth.getName());
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public Transaction update(@PathVariable Long id,
                              @Valid @RequestBody Transaction transaction,
                              Authentication auth) {
        return transactionService.updateTransaction(id, transaction, auth.getName());
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, Authentication auth) {
        transactionService.deleteTransaction(id, auth.getName());
        return "Deleted successfully";
    }

    // ✅ FILTER (IMPROVED)
    @GetMapping("/filter")
    public List<Transaction> filterTransactions(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end,
            Authentication auth) {

        RecordType recordType = null;

        if (type != null && !type.isBlank()) {
            try {
                recordType = RecordType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid type. Use INCOME or EXPENSE");
            }
        }

        LocalDate startDate = null;
        LocalDate endDate = null;

        try {
            if (start != null && !start.isBlank()) {
                startDate = LocalDate.parse(start);
            }
            if (end != null && !end.isBlank()) {
                endDate = LocalDate.parse(end);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Use YYYY-MM-DD");
        }

        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        return transactionService.filterTransactions(
                auth.getName(),
                recordType,
                category,
                startDate,
                endDate
        );
    }
}