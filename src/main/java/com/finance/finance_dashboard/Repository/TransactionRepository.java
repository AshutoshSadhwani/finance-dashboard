package com.finance.finance_dashboard.Repository;


import com.finance.finance_dashboard.entity.RecordType;
import com.finance.finance_dashboard.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId);

    // Filtering
    List<Transaction> findByUserIdAndType(Long userId, RecordType type);

    List<Transaction> findByUserIdAndCategory(Long userId, String category);

    List<Transaction> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);
}