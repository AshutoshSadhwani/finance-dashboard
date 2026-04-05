package com.finance.finance_dashboard.dto;

import com.finance.finance_dashboard.entity.RecordType;
import lombok.Data;
import java.time.LocalDate;
import jakarta.validation.constraints.*;

@Data
public class TransactionDTO {

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than 0")
    private Double amount;

    @NotNull(message = "Type is required")
    private RecordType type;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Date is required")
    private LocalDate date;

    private String notes;
}
