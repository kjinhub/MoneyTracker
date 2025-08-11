package com.moneyTrack.project.Infrastructure;

import com.moneyTrack.project.Domain.Transaction;
import com.moneyTrack.project.Domain.Transaction.Type;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryTransactionRepository {
    private final Map<Long, Transaction> storage = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    public Transaction save(Transaction t) {
        if (t.getId() == null) t.setId(seq.getAndIncrement());
        storage.put(t.getId(), t);
        return t;
    }

    public Optional<Transaction> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public void deleteById(Long id) {
        storage.remove(id);
    }

    public List<Transaction> findByDateBetween(LocalDate start, LocalDate end) {
        return storage.values().stream()
            .filter(t -> !t.getDate().isBefore(start) && !t.getDate().isAfter(end))
            .sorted(Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getId))
            .collect(Collectors.toList());
    }

    public long sumAmountByTypeBetween(Type type, LocalDate start, LocalDate end) {
        return storage.values().stream()
            .filter(t -> t.getType() == type)
            .filter(t -> !t.getDate().isBefore(start) && !t.getDate().isAfter(end))
            .mapToLong(Transaction::getAmount)
            .sum();
    }

    public Map<String, Long> aggregateExpenseByCategory(LocalDate start, LocalDate end) {
        Map<String, Long> map = new HashMap<>();
        storage.values().stream()
            .filter(t -> t.getType() == Type.EXPENSE)
            .filter(t -> !t.getDate().isBefore(start) && !t.getDate().isAfter(end))
            .forEach(t -> map.merge(t.getCategory(), t.getAmount(), Long::sum));
        return map;
    }
}
