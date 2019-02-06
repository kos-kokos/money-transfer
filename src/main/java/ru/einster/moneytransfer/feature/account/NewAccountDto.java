package ru.einster.moneytransfer.feature.account;

import java.math.BigDecimal;

public class NewAccountDto {

    private BigDecimal balance;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
