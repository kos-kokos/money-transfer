package ru.einster.moneytransfer.feature.transfer;

import java.math.BigDecimal;

public class TransferDto extends NewTransferDto {

    private Long id;

    public TransferDto(Long id, Long fromAccountId, Long toAccountId, BigDecimal amount) {
        this.id = id;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }
}
