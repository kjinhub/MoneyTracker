package com.moneyTrack.project.Infrastructure;

import com.moneyTrack.project.Domain.Budget;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryBudgetRepository {
    private final Map<Long, Budget> storage = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    public Budget save(Budget b) {
        if (b.getId() == null) b.setId(seq.getAndIncrement());
        storage.put(b.getId(), b);
        return b;
    }

    // 해당 연월의 "가장 최신" 하나만 필요할 때 사용
    public Optional<Budget> findLatestByYearAndMonth(int year, int month) {
        return storage.values().stream()
            .filter(x -> x.getYear() == year && x.getMonth() == month)
            .max(Comparator.comparing(Budget::getId));
    }

    // (옵션) 이력 전체 조회
    public List<Budget> findAllByYearAndMonth(int year, int month) {
        return storage.values().stream()
            .filter(x -> x.getYear() == year && x.getMonth() == month)
            .sorted(Comparator.comparing(Budget::getId))
            .toList();
    }
}
