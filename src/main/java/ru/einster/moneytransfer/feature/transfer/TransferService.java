package ru.einster.moneytransfer.feature.transfer;

import com.google.common.util.concurrent.Striped;
import io.micronaut.spring.tx.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.einster.moneytransfer.GenericException;
import ru.einster.moneytransfer.feature.account.Account;
import ru.einster.moneytransfer.feature.account.AccountRepository;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;

@Singleton
public class TransferService {

    private Logger log = LoggerFactory.getLogger(TransferService.class);

    private AccountRepository accountRepository;
    private TransferRepository transferRepository;

    private Striped<Lock> stripedLock = Striped.lock(10);

    public TransferService(AccountRepository accountRepository, TransferRepository transferRepository) {
        this.accountRepository = accountRepository;
        this.transferRepository = transferRepository;
    }

    @Transactional //waiting for locks should be done outside transaction
    public TransferDto processTransfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        log.info("Process transfer. fromAccount={}, toAccount={}, amount={}", fromAccountId, toAccountId, amount);
        if (fromAccountId.equals(toAccountId)) {
            throw new GenericException("fromAccount and toAccount are the same");
        }
        Long minAccountId = Math.min(fromAccountId, toAccountId);
        Long maxAccountId = Math.max(fromAccountId, toAccountId);

        Lock lock1 = stripedLock.get(minAccountId);
        log.debug("Lock on account {}", minAccountId);
        lock1.lock();
        try {
            Lock lock2 = stripedLock.get(maxAccountId);
            log.debug("Lock on account {}", maxAccountId);
            lock2.lock();
            try {

                Account fromAccount = accountRepository.findAccount(fromAccountId)
                        .orElseThrow(() -> new GenericException("Account " + fromAccountId + " not found"));
                Account toAccount = accountRepository.findAccount(toAccountId)
                        .orElseThrow(() -> new RuntimeException("Account " + toAccountId + " not found"));
                if (fromAccount.getBalance().compareTo(amount) < 0) {
                    throw new GenericException("Insufficient funds on account " + fromAccountId);
                }
                fromAccount.setBalance(fromAccount.getBalance().add(amount.negate()));
                toAccount.setBalance(toAccount.getBalance().add(amount));
                Transfer transfer = new Transfer();
                transfer.setFromAccount(fromAccount);
                transfer.setToAccount(toAccount);
                transfer.setAmount(amount);
                transferRepository.save(transfer);
                return new TransferDto(transfer.getId(), fromAccountId, toAccountId, amount);

            } finally {
                log.debug("Release lock on account {}", maxAccountId);
                lock2.unlock();
            }
        } finally {
            log.debug("Release lock on account {}", minAccountId);
            lock1.unlock();
        }
    }
}
