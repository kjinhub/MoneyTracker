package com.moneyTrack.project.Domain;

public class Budget {
    private Long id;
    private int year;
    private int month; // 1~12
    private Long amount;

    // getters & setters ...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public int getMonth() { return month; }
    public void setMonth(int month) { this.month = month; }
    public Long getAmount() { return amount; }
    public void setAmount(Long amount) { this.amount = amount; }
}
