package ru.einster.moneytransfer.feature.transfer;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
public class TransferRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Transfer save(Transfer transfer) {
        entityManager.persist(transfer);
        return transfer;
    }
}
