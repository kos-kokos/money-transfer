package ru.einster.moneytransfer.feature.account;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Singleton
public class AccountRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public AccountRepository(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Account> findAccount(Long id) {
        return Optional.ofNullable(entityManager.find(Account.class, id));
    }

    public Account save(Account newAccount) {
        entityManager.persist(newAccount);
        return newAccount;
    }
}
