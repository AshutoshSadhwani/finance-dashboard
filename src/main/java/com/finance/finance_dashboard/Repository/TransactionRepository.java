package com.finance.finance_dashboard.Repository;


import com.finance.finance_dashboard.entity.RecordType;
import com.finance.finance_dashboard.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId);

    // Filtering
    List<Transaction> findByUserIdAndType(Long userId, RecordType type);

    List<Transaction> findByUserIdAndCategory(Long userId, String category);

    List<Transaction> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);


    @Query("""
        SELECT SUM(t.amount)
        FROM Transaction t
        WHERE t.user.email = :email AND t.type = :type
    """)
    Double sumByType(String email, RecordType type);


    @Query("""
        SELECT t.category, SUM(t.amount)
        FROM Transaction t
        WHERE t.user.email = :email
        GROUP BY t.category
    """)
    List<Object[]> sumByCategory(String email);


    @Query("""
        SELECT 
            FUNCTION('TO_CHAR', t.date, 'YYYY-MM'),
            SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END),
            SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END)
        FROM Transaction t
        WHERE t.user.email = :email
        GROUP BY FUNCTION('TO_CHAR', t.date, 'YYYY-MM')
        ORDER BY 1
    """)
    List<Object[]> monthlySummary(String email);


    List<Transaction> findTop5ByUserEmailOrderByDateDesc(String email);



}