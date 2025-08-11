package com.moneyTrack.project.Domain;

import java.time.YearMonth;
import java.util.Map;

public class MonthlySummaryDto {
    public YearMonth month;
    public Long totalIncome;
    public Long totalExpense;
    public Long balance;
    public Long budget;
    public Long remainingBudget;
    public Map<String, Long> byCategory;
}
