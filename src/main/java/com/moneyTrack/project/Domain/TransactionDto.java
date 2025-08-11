package com.moneyTrack.project.Domain;

import java.time.LocalDate;
import com.moneyTrack.project.Domain.Transaction.Type;

public class TransactionDto {
    public Long id;
    public Type type;       // INCOME / EXPENSE
    public String category;
    public Long amount;
    public LocalDate date;  // YYYY-MM-DD
    public String memo;
}
