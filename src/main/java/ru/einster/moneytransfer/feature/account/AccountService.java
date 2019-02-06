package ru.einster.moneytransfer.feature.account;


import io.micronaut.spring.tx.annotation.Transactional;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Account> getAccount(Long accountId) {
        return accountRepository.findAccount(accountId);
    }

    @Transactional
    public Account createAccount(NewAccountDto newAccountDto) {
        Account account = new Account();
        account.setBalance(newAccountDto.getBalance());
        return accountRepository.save(account);
    }
}
