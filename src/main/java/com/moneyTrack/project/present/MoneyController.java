package com.moneyTrack.project.present;

import com.moneyTrack.project.Service.MoneyService;
import com.moneyTrack.project.Domain.Budget;
import com.moneyTrack.project.Domain.MonthlySummaryDto; // ← 패키지에 맞춰 import 수정
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.Map;

@RestController
@RequestMapping("/api/money")
public class MoneyController {
    private final MoneyService moneyService;
    public MoneyController(MoneyService moneyService) { this.moneyService = moneyService; }

    // JSON Body: { "year": 2025, "month": 8, "amount": 500000 }
    @PostMapping("/budget")
    public Budget setBudget(@RequestBody Map<String, Object> body) {
        int year = ((Number) body.get("year")).intValue();
        int month = ((Number) body.get("month")).intValue();
        long amount = ((Number) body.get("amount")).longValue();
        return moneyService.setMonthlyBudget(YearMonth.of(year, month), amount);
    }

    @GetMapping("/summary")
    public MonthlySummaryDto summary(@RequestParam String ym) {
        return moneyService.getMonthlySummary(YearMonth.parse(ym)); // "2025-08"
    }
}
