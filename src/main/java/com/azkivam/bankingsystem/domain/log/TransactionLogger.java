package com.azkivam.bankingsystem.domain.log;

import com.azkivam.bankingsystem.domain.entity.party.TransactionParty;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class TransactionLogger implements TransactionObserver {

    @Override
    public void update(TransactionParty sourceAccount, TransactionParty destinationAccount, BigDecimal amount) {
        log.info("Transaction: " + amount + " from " + sourceAccount + " to " + destinationAccount);
    }
}