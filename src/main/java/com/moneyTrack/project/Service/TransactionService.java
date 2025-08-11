package com.moneyTrack.project.Service;

import com.moneyTrack.project.Domain.Transaction;
import com.moneyTrack.project.Domain.TransactionDto;
import com.moneyTrack.project.Infrastructure.InMemoryTransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final InMemoryTransactionRepository txRepo;
    public TransactionService(InMemoryTransactionRepository txRepo) { this.txRepo = txRepo; }

    public TransactionDto create(TransactionDto dto) {
        Transaction t = toEntity(dto);
        return toDto(txRepo.save(t));
    }

    public TransactionDto update(Long id, TransactionDto dto) {
        Transaction t = txRepo.findById(id).orElseThrow();
        t.setType(dto.type);
        t.setCategory(dto.category);
        t.setAmount(dto.amount);
        t.setDate(dto.date);
        t.setMemo(dto.memo);
        return toDto(txRepo.save(t));
    }

    public void delete(Long id) { txRepo.deleteById(id); }

    public TransactionDto get(Long id) {
        return txRepo.findById(id).map(this::toDto).orElseThrow();
    }

    public List<TransactionDto> list(LocalDate start, LocalDate end) {
        return txRepo.findByDateBetween(start, end).stream().map(this::toDto).collect(Collectors.toList());
    }

    private Transaction toEntity(TransactionDto d) {
        Transaction t = new Transaction();
        t.setId(d.id);
        t.setType(d.type);
        t.setCategory(d.category);
        t.setAmount(d.amount);
        t.setDate(d.date);
        t.setMemo(d.memo);
        return t;
    }
    private TransactionDto toDto(Transaction t) {
        TransactionDto d = new TransactionDto();
        d.id = t.getId();
        d.type = t.getType();
        d.category = t.getCategory();
        d.amount = t.getAmount();
        d.date = t.getDate();
        d.memo = t.getMemo();
        return d;
    }
}
