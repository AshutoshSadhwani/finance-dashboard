package com.finance.finance_dashboard.controller;

import com.finance.finance_dashboard.dto.TransactionDTO;
import com.finance.finance_dashboard.entity.RecordType;
import com.finance.finance_dashboard.entity.Transaction;
import com.finance.finance_dashboard.service.TransactionService;
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

    // CREATE
    @PostMapping
    public Transaction addTransaction(@RequestBody TransactionDTO dto,
                                      Authentication auth) {
        return transactionService.addTransaction(dto, auth.getName());
    }

    // READ
    @GetMapping
    public List<Transaction> getTransactions(Authentication auth) {
        return transactionService.getUserTransactions(auth.getName());
    }

    // UPDATE
    @PutMapping("/{id}")
    public Transaction update(@PathVariable Long id,
                              @RequestBody Transaction transaction,
                              Authentication auth) {
        return transactionService.updateTransaction(id, transaction, auth.getName());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, Authentication auth) {
        transactionService.deleteTransaction(id, auth.getName());
        return "Deleted successfully";
    }

//    // FILTER BY TYPE
//    @GetMapping("/filter/type")
//    public List<Transaction> filterByType(@RequestParam String type,
//                                          Authentication auth) {
//        return transactionService.filterByType(
//                auth.getName(),
//                RecordType.valueOf(type.toUpperCase())
//        );
//    }
//
//    // FILTER BY CATEGORY
//    @GetMapping("/filter/category")
//    public List<Transaction> filterByCategory(@RequestParam String category,
//                                              Authentication auth) {
//        return transactionService.filterByCategory(auth.getName(), category);
//    }
//
//    // FILTER BY DATE RANGE
//    @GetMapping("/filter/date")
//    public List<Transaction> filterByDate(
//            @RequestParam String start,
//            @RequestParam String end,
//            Authentication auth) {
//
//        return transactionService.filterByDateRange(
//                auth.getName(),
//                LocalDate.parse(start),
//                LocalDate.parse(end)
//        );
//    }

    @GetMapping("/filter")
    public List<Transaction> filterTransactions(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end,
            Authentication auth) {
        RecordType recordType = null;
        if (type != null) {
            try {
                recordType = RecordType.valueOf(type.toUpperCase());
            } catch (Exception e) {
                throw new RuntimeException("Invalid type value");
            }
        }
        return transactionService.filterTransactions(
                auth.getName(),
                recordType,
                category,
                start,
                end
        );
    }
}