package com.moneyTrack.project.Domain;

import java.time.LocalDate;

public class Transaction {
    private Long id;
    private Type type;        // INCOME / EXPENSE
    private String category;
    private Long amount;
    private LocalDate date;
    private String memo;

    public enum Type { INCOME, EXPENSE }

    // getters & setters ...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Long getAmount() { return amount; }
    public void setAmount(Long amount) { this.amount = amount; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getMemo() { return memo; }
    public void setMemo(String memo) { this.memo = memo; }
}
