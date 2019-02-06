package ru.einster.moneytransfer.feature.transfer;

import ru.einster.moneytransfer.feature.account.Account;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "FROM_ACCOUNT_ID")
    private Account fromAccount;


    @ManyToOne
    @JoinColumn(name = "TO_ACCOUNT_ID")
    private Account toAccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }
}
