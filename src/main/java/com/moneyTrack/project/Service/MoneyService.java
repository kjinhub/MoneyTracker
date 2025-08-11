package com.moneyTrack.project.Service;

import com.moneyTrack.project.Domain.Budget;
import com.moneyTrack.project.Domain.MonthlySummaryDto;
import com.moneyTrack.project.Infrastructure.InMemoryBudgetRepository;
import com.moneyTrack.project.Infrastructure.InMemoryTransactionRepository;
import com.moneyTrack.project.Domain.Transaction.Type;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

@Service
public class MoneyService {
    private final InMemoryTransactionRepository txRepo;
    private final InMemoryBudgetRepository budgetRepo;

    public MoneyService(InMemoryTransactionRepository txRepo, InMemoryBudgetRepository budgetRepo) {
        this.txRepo = txRepo;
        this.budgetRepo = budgetRepo;
    }

    // JSON Body로 받은 Budget을 이용해 설정 (항상 새 기록)
    public Budget setMonthlyBudget(YearMonth ym, long amount) {
        Budget b = new Budget();                 // 신규
        b.setYear(ym.getYear());
        b.setMonth(ym.getMonthValue());
        b.setAmount(amount);
        return budgetRepo.save(b);               // id 계속 증가
    }

    public MonthlySummaryDto getMonthlySummary(YearMonth ym) {
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();

        long income = txRepo.sumAmountByTypeBetween(Type.INCOME, start, end);
        long expense = txRepo.sumAmountByTypeBetween(Type.EXPENSE, start, end);
        Map<String, Long> byCategory = txRepo.aggregateExpenseByCategory(start, end);

        Long budget = budgetRepo
            .findLatestByYearAndMonth(ym.getYear(), ym.getMonthValue())
            .map(Budget::getAmount).orElse(null);

        MonthlySummaryDto dto = new MonthlySummaryDto();
        dto.month = ym;
        dto.totalIncome = income;
        dto.totalExpense = expense;
        dto.balance = income - expense;
        dto.budget = budget;
        dto.remainingBudget = (budget == null) ? null : budget - expense;
        dto.byCategory = byCategory;
        return dto;
    }
}
