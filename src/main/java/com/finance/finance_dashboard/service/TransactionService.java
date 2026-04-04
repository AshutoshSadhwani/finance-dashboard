package com.finance.finance_dashboard.service;


import com.finance.finance_dashboard.Repository.TransactionRepository;
import com.finance.finance_dashboard.Repository.UserRepository;
import com.finance.finance_dashboard.dto.TransactionDTO;
import com.finance.finance_dashboard.entity.RecordType;
import com.finance.finance_dashboard.entity.Transaction;
import com.finance.finance_dashboard.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public Transaction addTransaction(TransactionDTO dto, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        Transaction transaction = Transaction.builder()
                .amount(dto.getAmount())
                .type(dto.getType())
                .category(dto.getCategory())
                .date(dto.getDate())
                .notes(dto.getNotes())
                .user(user)
                .build();

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getUserTransactions(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return transactionRepository.findByUserId(user.getId());
    }

    //UPDATE
     public Transaction updateTransaction(Long id, Transaction updated, String email) {

        Transaction existing = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // ✅ Authorization check
        if (!existing.getUser().getEmail().equals(email)) {
            throw new RuntimeException("You are not allowed to update this transaction");
        }

        // ✅ Partial update (avoid null overwrite)
        if (updated.getAmount() != null) {
            existing.setAmount(updated.getAmount());
        }

        if (updated.getType() != null) {
            existing.setType(updated.getType());
        }

        if (updated.getCategory() != null) {
            existing.setCategory(updated.getCategory());
        }

        if (updated.getDate() != null) {
            existing.setDate(updated.getDate());
        }

        if (updated.getNotes() != null) {
            existing.setNotes(updated.getNotes());
        }

        return transactionRepository.save(existing);
    }

    // ✅ DELETE
    public void deleteTransaction(Long id, String email) {
        Transaction existing = transactionRepository.findById(id)
                .orElseThrow();

        if (!existing.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }

        transactionRepository.delete(existing);
    }

//    // ✅ FILTER BY TYPE
//    public List<Transaction> filterByType(String email, RecordType type) {
//        User user = userRepository.findByEmail(email).orElseThrow();
//        return transactionRepository.findByUserIdAndType(user.getId(), type);
//    }
//
//    // ✅ FILTER BY CATEGORY
//    public List<Transaction> filterByCategory(String email, String category) {
//        User user = userRepository.findByEmail(email).orElseThrow();
//        return transactionRepository.findByUserIdAndCategory(user.getId(), category);
//    }
//
//    // ✅ FILTER BY DATE RANGE
//    public List<Transaction> filterByDateRange(String email, LocalDate start, LocalDate end) {
//        User user = userRepository.findByEmail(email).orElseThrow();
//        return transactionRepository.findByUserIdAndDateBetween(user.getId(), start, end);
//    }

    public List<Transaction> filterTransactions(String email,
                                                RecordType type,
                                                String category,
                                                String start,
                                                String end) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Transaction> transactions = transactionRepository.findByUserId(user.getId());

        // ✅ Filter by type
        if (type != null) {
            transactions = transactions.stream()
                    .filter(t -> t.getType() == type)
                    .toList();
        }

        // ✅ Filter by category
        if (category != null && !category.isEmpty()) {
            transactions = transactions.stream()
                    .filter(t -> t.getCategory().equalsIgnoreCase(category))
                    .toList();
        }

        // ✅ Filter by date range
        if (start != null && end != null) {
            LocalDate startDate = LocalDate.parse(start);
            LocalDate endDate = LocalDate.parse(end);

            transactions = transactions.stream()
                    .filter(t -> !t.getDate().isBefore(startDate)
                            && !t.getDate().isAfter(endDate))
                    .toList();
        }

        return transactions;
    }
}