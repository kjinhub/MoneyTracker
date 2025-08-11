package com.moneyTrack.project.present;

import com.moneyTrack.project.Service.TransactionService;
import com.moneyTrack.project.Domain.TransactionDto;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService txService;
    public TransactionController(TransactionService txService) { this.txService = txService; }

    @PostMapping
    public TransactionDto create(@RequestBody TransactionDto dto) { return txService.create(dto); }

    @PutMapping("/{id}")
    public TransactionDto update(@PathVariable Long id, @RequestBody TransactionDto dto) {
        return txService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { txService.delete(id); }

    @GetMapping("/{id}")
    public TransactionDto get(@PathVariable Long id) { return txService.get(id); }

    @GetMapping
    public List<TransactionDto> list(@RequestParam LocalDate start, @RequestParam LocalDate end) {
        return txService.list(start, end);
    }
}
