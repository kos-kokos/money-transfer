package ru.einster.moneytransfer;

import java.math.BigDecimal;

public class AccountInfo {

    public AccountInfo(Long id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    private Long id;
    private BigDecimal balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
